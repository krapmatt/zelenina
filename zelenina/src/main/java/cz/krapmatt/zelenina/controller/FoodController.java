package cz.krapmatt.zelenina.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cz.krapmatt.zelenina.entities.Food;
import cz.krapmatt.zelenina.service.FoodService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FoodController {
    @Autowired
    private FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/voting")
    public String pickRandomFood(Model model) {
        List<Food> randomFood = foodService.selectRandomObjects(2);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        model.addAttribute("user", auth.getDetails());
        System.out.println(randomFood);
        model.addAttribute("randomFood", randomFood);
        /*model.addAttribute("food1", randomFood.get(0).getName());
        model.addAttribute("food2", randomFood.get(1).getName());*/
        return "voting";
    }

    @PostMapping("/voting")
    public String saveVote(@RequestParam("chosenFood") String food ) {
        //TODO: process POST request
        System.out.println("ahoj" + food);
        return "redirect:/index";
    }
    

}
