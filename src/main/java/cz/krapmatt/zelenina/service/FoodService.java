package cz.krapmatt.zelenina.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.krapmatt.zelenina.entities.Food;
import cz.krapmatt.zelenina.entities.User;
import cz.krapmatt.zelenina.entities.Vote;
import cz.krapmatt.zelenina.repository.FoodRepository;
import cz.krapmatt.zelenina.repository.VoteRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
@Service
public class FoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private VoteRepository voteRepository;
    public FoodService(FoodRepository foodRepository, VoteRepository voteRepository) {
        this.foodRepository = foodRepository;
        this.voteRepository = voteRepository;
    }
    
    @PostConstruct
    public void initializeFoods() {
        List<Food> foods = Arrays.asList(
            new Food("Banan"),
            new Food("Jablko"),
            new Food("Pomeranč"),
            new Food("Myš")
        );
        for (Food food : foods) {
            if (!foodRepository.existsByName(food.getName())) {
                food.setTimeOfAddition(LocalDateTime.now());
                foodRepository.save(food);
            } 
        }
    }

    public List<Food> selectRandomFoods() {
        return foodRepository.findRandomTwo();
    }

    @Transactional
    public void saveVote(User user, String chosenFood, String loseFood) {
    Food existingFood = foodRepository.findByName(chosenFood);

    if (existingFood == null) {
        //přidat pole pro přidání svého jídla
        Food newFood = new Food(chosenFood);
        foodRepository.save(newFood);
        existingFood = newFood;
    }

    Vote vote = new Vote(user, existingFood, foodRepository.findByName(loseFood));
    vote.setTimeOfVote(LocalDateTime.now());
    voteRepository.save(vote);
}
}
