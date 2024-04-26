package cz.krapmatt.zelenina.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cz.krapmatt.zelenina.entities.Role;
import cz.krapmatt.zelenina.entities.User;
import cz.krapmatt.zelenina.repository.RoleRepository;
import cz.krapmatt.zelenina.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setUsername("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    public boolean verifyPass(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
    
}
