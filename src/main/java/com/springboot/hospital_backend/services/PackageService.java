package com.springboot.hospital_backend.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.models.MedicalService;
import com.springboot.hospital_backend.models.Package;
import com.springboot.hospital_backend.repositories.AppointmentRepository;
import com.springboot.hospital_backend.repositories.MedicalServiceRepository;
import com.springboot.hospital_backend.repositories.PackageRepository;

@Service

public class PackageService {
    @Autowired
    private PackageRepository packageRepo;

    @Autowired
    private MedicalServiceRepository medicalServiceRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    public List<Package> getPackages() {
        return this.packageRepo.findAllByOrderById();
    }

    public List<Package> getPackagesBySpecialityId(int specialityId) {
        return this.packageRepo.findAllByIncludedServicesSpecialityId(specialityId);
    }

    public Package savePackage(Set<MedicalService> medicalService) {
        if(!this.checkPackages(medicalService)) {
            Set<MedicalService> services = medicalService.stream()
                    .map(s -> this.medicalServiceRepo.findById(s.getId()).get()).collect(Collectors.toSet());
            Package packages = new Package();

            packages.setIncludedServices(services);
            packages.setPrice(this.getDiscount(services));

            return this.packageRepo.save(packages);
        }

        return null;
    }

    public Package addServicesToPackage(Package packages, Set<MedicalService> services) {
        if(!this.checkPackageToAddServices(packages, services) &&
            this.appointmentRepo.findAppointmentsByMedicalPackageId(packages.getId()).size() == 0) {
                
            for(MedicalService s : services) {
                packages.getIncludedServices().add(s);
            }

            if(!this.checkPackages(packages.getIncludedServices())) {
                packages.setPrice(getDiscount(packages.getIncludedServices()));
                return packageRepo.save(packages);
            }
            else
                return null;
        }

        return null;
    }

    public Package deleteServiceFromPackage(Package packages, MedicalService service) {
        packages.getIncludedServices().remove(service);

        if(!this.checkPackages(packages.getIncludedServices()) && 
            this.appointmentRepo.findAppointmentsByMedicalPackageId(packages.getId()).size() == 0) {

            packages.setPrice(this.getDiscount(packages.getIncludedServices()));

            if(packages.getIncludedServices().size() < 2) {
                deletePackage(packages.getId());
                return packages;
            }
            else
                return this.packageRepo.save(packages);
        }

        return null;
    }   

    public void recalculatePrice(MedicalService service) {
        List<Package> packages = this.packageRepo.findAllByOrderById();

        for(Package p : packages) {
            if(p.getIncludedServices().contains(service)) {
                p.setPrice(this.getDiscount(p.getIncludedServices()));
                this.packageRepo.save(p);
            }
        }
    }

    public void updateServicesFromPackege(int serviceId) {
        MedicalService service = medicalServiceRepo.findById(serviceId).get();
        List<Package> allPackages = this.packageRepo.findAllByOrderById();

        for(Package p : allPackages) {
            if(p.getIncludedServices().contains(service)) {
                p.getIncludedServices().remove(service);

                if(!this.checkPackagesWhenUpdating(p.getId(), p.getIncludedServices())) {
                    p.setPrice(this.getDiscount(p.getIncludedServices()));
    
                    if(p.getIncludedServices().size() < 2) {
                        deletePackage(p.getId());
                        updateServicesFromPackege(serviceId);
                    }
                    else 
                        this.packageRepo.save(p);
                }
                else {
                    deletePackage(p.getId());
                    updateServicesFromPackege(serviceId);
                }
            }
        }
    }

    public Package deletePackage(int id) {
        Package packages = this.packageRepo.findById(id).get();

        if(this.appointmentRepo.findAppointmentsByMedicalPackageId(id).size() == 0) {
            this.packageRepo.deleteById(id);
            return packages;
        }

        return null;
    } 

    private boolean checkPackages(Set<MedicalService> medicalServices) {
        List<Package> packages = this.packageRepo.findAllByOrderById();
        Set<Integer> inputServiceIds = medicalServices.stream()
            .map(s-> s.getId()).collect(Collectors.toSet());

        for(Package p : packages) {
            Set<Integer> packageServiceIds = p.getIncludedServices().stream()
                .map(s -> s.getId()).collect(Collectors.toSet());

            if(packageServiceIds.equals(inputServiceIds))
                return true;
        }

        return false;
    }

    private boolean checkPackagesWhenUpdating(int packageId, Set<MedicalService> medicalServices) {
        List<Package> packages = this.packageRepo.findAllExcludingCurrentOne(packageId);
        Set<Integer> inputServiceIds = medicalServices.stream()
            .map(s-> s.getId()).collect(Collectors.toSet());

        for(Package p : packages) {
            Set<Integer> packageServiceIds = p.getIncludedServices().stream()
                .map(s -> s.getId()).collect(Collectors.toSet());

            if(packageServiceIds.equals(inputServiceIds))
                return true;
        }

        return false;
    }

    private boolean checkPackageToAddServices(Package packages, Set<MedicalService> services) {
        Set<Integer> packageServiceIds = packages.getIncludedServices().stream()
            .map(s -> s.getId()).collect(Collectors.toSet());

        Set<Integer> inputServiceIds = services.stream()
            .map(s-> s.getId()).collect(Collectors.toSet());

        for(Integer s : inputServiceIds) {
            if(packageServiceIds.contains(s))
                return true;
        }

        return false;
    }

    private Long getDiscount(Set<MedicalService> services) {
        Long percent = (long) (services.stream().mapToLong(s -> s.getPrice()).sum() * .15);
        Long price = services.stream().mapToLong(s -> s.getPrice()).sum() - percent;

        return price;
    }
}
