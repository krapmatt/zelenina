package cz.krapmatt.zelenina.entities;



import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp timeOfVote;

    @ManyToOne
    @JoinColumn(name = "chosen_food_id") 
    private Food chosenFood;
    
    @ManyToOne
    @JoinColumn(name = "lost_food_id")
    private Food lostFood;

    public Vote(User user, Food chosenFood, Food lostFood) {
        this.chosenFood = chosenFood;
        this.lostFood = lostFood;
        this.user = user;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Food return the chosenFood
     */
    public Food getChosenFood() {
        return chosenFood;
    }

    /**
     * @param chosenFood the chosenFood to set
     */
    public void setChosenFood(Food chosenFood) {
        this.chosenFood = chosenFood;
    }

    /**
     * @return Food return the lostFood
     */
    public Food getLostFood() {
        return lostFood;
    }

    /**
     * @param lostFood the lostFood to set
     */
    public void setLostFood(Food lostFood) {
        this.lostFood = lostFood;
    }


}