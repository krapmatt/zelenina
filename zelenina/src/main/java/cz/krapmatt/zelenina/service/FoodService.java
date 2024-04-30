package cz.krapmatt.zelenina.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.krapmatt.zelenina.entities.Food;
import cz.krapmatt.zelenina.repository.FoodRepository;
@Service
public class FoodService {
    
    @Autowired
    private FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }
    
    public List<Food> selectRandomObjects(int count) {
        
        List<Food> foods = Arrays.asList(
            new Food("Banan"),
            new Food("Jablko"),
            new Food("Pomeranč"),
            new Food("Myš")
        );

        foodRepository.saveAll(foods);
        List<Food> selectedFood = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(foods.size());
            selectedFood.add(foods.get(randomIndex));
        }
        return selectedFood;
    }
 
}
