package cz.krapmatt.zelenina.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cz.krapmatt.zelenina.entities.User;
import cz.krapmatt.zelenina.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class GuestController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    HttpServletRequest request;

    public GuestController(AuthenticationManager authenticationManager, UserService userService, HttpServletRequest request) {
        this.request = request;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/login/guest")
    public String loginAsAnonymous(Model model) {
        System.out.println("Logging in as guest");
        
        User guest = userService.findUserByEmail("guest@guest.com");
        model.addAttribute("user", guest.getUsername());
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(guest.getEmail(), "guest", userService.getAuthorities());
        System.out.println("Logging in as guest1");
        Authentication authentication = authenticationManager.authenticate(authRequest);
        System.out.println("Logging in as guest2");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println("Logging in as guest3");
        securityContext.setAuthentication(authentication);
        System.out.println("Logging in as guest4");
        
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        System.out.println("Redirecting to /voting");
        return "redirect:/voting";
    }
}
