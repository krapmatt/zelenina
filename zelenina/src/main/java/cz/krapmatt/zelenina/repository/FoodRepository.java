package cz.krapmatt.zelenina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cz.krapmatt.zelenina.entities.Food;

@Repository
public interface FoodRepository extends JpaRepository <Food, Long> {
    @Query(value = "SELECT * FROM food ORDER BY RAND() LIMIT 2", nativeQuery = true)
    List<Food> findRandomTwo(); 
}