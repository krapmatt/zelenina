package cz.krapmatt.zelenina.service;

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
    
    public List<Food> selectRandomObjects(int count) {
        
        List<Food> foods = Arrays.asList(
            new Food("Banan"),
            new Food("Jablko"),
            new Food("Pomeranč"),
            new Food("Myš")
        );

        
        List<Food> selectedFood = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(foods.size());
            selectedFood.add(foods.get(randomIndex));
        }
        //V připadě předělání na více možností na hlasy předělat! TODO!
        if(selectedFood.get(0).getName() == selectedFood.get(1).getName()) {
            return selectRandomObjects(count);
        } else {
            return selectedFood;
        }

    }
    
    @Transactional
    public void saveVote(User user, String food) {
        
        Vote vote = new Vote(user, new Food(food));
        voteRepository.save(vote);
    }
}
