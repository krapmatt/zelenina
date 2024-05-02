package cz.krapmatt.zelenina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.krapmatt.zelenina.entities.Vote;

@Repository
public interface VoteRepository extends JpaRepository <Vote, Long> {
    
}
