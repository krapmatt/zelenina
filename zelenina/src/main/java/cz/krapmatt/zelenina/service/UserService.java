package cz.krapmatt.zelenina.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;    
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
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
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }
    
    @Transactional
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

    public boolean verifyPass(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
    
    @Transactional
    public User newGuestUser() {
        User guest = new User();
        guest.setUsername("guest");
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);
        guest.setRoles(roles);
        roleRepository.save(role);        
        userRepository.save(guest);
        return guest;
    }
}
