package cz.krapmatt.zelenina.controller;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.krapmatt.zelenina.entities.Food;
import cz.krapmatt.zelenina.entities.User;
import cz.krapmatt.zelenina.service.FoodService;
import cz.krapmatt.zelenina.service.UserService;


@Controller
public class FoodController {
    
    private FoodService foodService;
    
    private UserService userService;

    public FoodController(FoodService foodService, UserService userService) {
        this.foodService = foodService;
        this.userService = userService;
    }

    @GetMapping("/voting")
    public String pickRandomFood(Model model) {
        List<Food> randomFood = foodService.selectRandomFoods();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userService.findUserByEmail(email);

        model.addAttribute("user", user);
        
        model.addAttribute("randomFood", randomFood);
        model.addAttribute("food1", randomFood.get(0).getName());
        model.addAttribute("food2", randomFood.get(1).getName());

        return "voting";
    }

    @PostMapping("/voting")
    public String saveVote(@RequestParam("chosenFood") String chosenFood, @RequestParam("loseFood") String loseFood) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        User user = userService.findUserByEmail(email);
        
        foodService.saveVote(user, chosenFood, loseFood);

        return "redirect:/voting";
    }
    

}
