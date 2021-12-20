package com.ironhack.charactermodelservice.repository;

import com.ironhack.charactermodelservice.dao.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findAllByUserUsernameAndIsAlive(String userUsername, Boolean isAlive);

}
