package cz.krapmatt.zelenina.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import cz.krapmatt.zelenina.entities.User;
import cz.krapmatt.zelenina.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    
    private UserRepository userRepository;    
   
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities());
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        
    }


    //Správně načítat věci z databáze

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
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


    public boolean verifyPass(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
    
    
    @PostConstruct
    public void newGuestUser() {
        if (!userRepository.existsByEmail("guest@guest.com")) {
            User guest = new User("guest", "guest", "guest@guest.com");
            guest.setPassword(passwordEncoder.encode(guest.getPassword()));
            userRepository.save(guest);
        }
    }
}
