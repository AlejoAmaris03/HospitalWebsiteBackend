package com.springboot.hospital_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.hospital_backend.models.User;

@Repository

public interface UserRepository extends JpaRepository<User, Integer>{
    public User findUserByUsername(String username);

    @Query(
    """
        SELECT u
        FROM User u
        WHERE (u.username != ?1) AND (u.username = ?2)      
    """)
    public User findUserByUsernameExcludingCurrentOne(String currentUsername, String newUsername);
}
