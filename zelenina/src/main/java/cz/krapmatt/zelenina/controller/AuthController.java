package cz.krapmatt.zelenina.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.krapmatt.zelenina.entities.Role;
import cz.krapmatt.zelenina.entities.User;
import cz.krapmatt.zelenina.service.UserService;


@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }
    
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Validated @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(user.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/register";
        }
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/register?success";
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        System.out.println("showLoginForm");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        System.out.println("Login2");
        System.out.println("Email: " + email); // Check email value
        System.out.println("Password: " + password);
        User user = userService.findUserByEmail(email);
        if (user != null && userService.verifyPass(user, password)) {
            model.addAttribute("user", user);
            return "redirect:/voting";
        } else {
            
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
    
    @PostMapping("/login/guest")
    public String loginAsAnonymous(Model model) {
        System.out.println("Logging in as guest");
        User user = userService.newGuestUser();
        model.addAttribute("user", user);
        
        return "redirect:/voting";
    }
    
    
    
}