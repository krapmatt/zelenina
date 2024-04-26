package cz.krapmatt.zelenina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.krapmatt.zelenina.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByUsername(String username);
}
