package cz.krapmatt.zelenina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.krapmatt.zelenina.entities.User;



@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);  
    Boolean existsByEmail(String email);  
}
