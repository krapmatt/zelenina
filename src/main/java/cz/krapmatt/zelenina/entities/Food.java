package cz.krapmatt.zelenina.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(unique = true)
    private String name;

    @CreationTimestamp
    private Timestamp timeOfAddition;

    @OneToMany(mappedBy = "chosenFood", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    public Food() {

    }
    
    public Food(String name) {
        this.name = name;
    }

    /**
     * @return long return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return List<Vote> return the votes
     */
    public List<Vote> getVotes() {
        return votes;
    }

    /**
     * @param votes the votes to set
     */
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
    
    /**
     * @return Timestamp return the timeOfAddition
     */
    public Timestamp getTimeOfAddition() {
        return timeOfAddition;
    }

    /**
     * @param timeOfAddition the timeOfAddition to set
     */
    public void setTimeOfAddition(Timestamp timeOfAddition) {
        this.timeOfAddition = timeOfAddition;
    }

}
