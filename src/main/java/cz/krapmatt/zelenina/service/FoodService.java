package cz.krapmatt.zelenina.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    
    
    private FoodRepository foodRepository;
    
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
                foodRepository.save(food);
            } 
        }
    }

    public List<Food> selectRandomFoods() {
        return foodRepository.findRandomTwo();
    }

    

    @Transactional
    public void saveVote(User user, String winnerFood, String loserFood) {
        Food existingFood = foodRepository.findByName(winnerFood);

        if (existingFood == null) {
            //přidat pole pro přidání svého jídla
            Food newFood = new Food(winnerFood);
            foodRepository.save(newFood);
            existingFood = newFood;
        }

        Vote vote = new Vote(user, existingFood, foodRepository.findByName(loserFood));
        vote.setTimeOfVote(LocalDateTime.now());
        voteRepository.save(vote);
    }
}
