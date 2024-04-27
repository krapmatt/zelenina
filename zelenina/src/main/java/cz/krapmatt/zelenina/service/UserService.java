package cz.krapmatt.zelenina.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cz.krapmatt.zelenina.entities.Role;
import cz.krapmatt.zelenina.entities.User;
import cz.krapmatt.zelenina.repository.RoleRepository;
import cz.krapmatt.zelenina.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Example: Fetching user roles and converting them to GrantedAuthority
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getUsername()));
        });
        return authorities;
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
