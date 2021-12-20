package com.ironhack.opponentselectionservice.service;

import com.github.javafaker.Faker;
import com.ironhack.opponentselectionservice.dto.*;
import com.ironhack.opponentselectionservice.proxy.CharacterModelProxy;
import com.ironhack.opponentselectionservice.proxy.UserModelProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ironhack.opponentselectionservice.util.Randomizer.getRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpponentCreationServiceImpl implements OpponentCreationService {
    private final Faker faker;
    private final UserModelProxy userModelProxy;
    private final CharacterModelProxy characterModelProxy;

    private final int MAX_REPETITIONS = 20;
    private final int MAX_PARTY_SIZE = 10;
    private final String[] CHARACTER_TYPES = {"Warrior", "Mage", "Archer"};
    private final String[] LEVEL_UP_POINT_TYPES = {"health", "energy", "attack"};
    private final int NUMBER_OF_POINTS_PER_LEVEL_UP = 3;

    // -------------------- Create Opponent --------------------
    public List<CharacterDTO> generateOpponent(int opponentLevel) {
        log.info("Generating opponent of level {}", opponentLevel);
        var generatedUser = createUserOpponent();
        var generatedParty = createOpponentParty(generatedUser.getUsername());
        return levelUpOpponentParty(opponentLevel, generatedUser.getUsername(), generatedParty);
    }

    // -------------------- Create User Opponent --------------------
    public UserDTO createUserOpponent() {
        log.info("Creating opponent");
        // Generate username, email and password
        var generatedUsername = "";
        for (int i = 0; i < MAX_REPETITIONS; i++) {
            generatedUsername = generateUsername();
            if (isValidUserUsername(generatedUsername)) break;
        }
        var generatedEmail = generatedUsername;
        var generatedPassword =
                faker.internet().password(20, 30, true, true);
        // Register user
        return registerUser(new RegisterUserDTO(generatedUsername, generatedEmail, generatedPassword));
    }


    // -------------------- Create Party Opponent --------------------
    public List<CharacterDTO> createOpponentParty(String userUsername) {
        log.info("Creating opponent party");
        List<CharacterDTO> party = new ArrayList<>();
        for (int i = 0; i < MAX_PARTY_SIZE; i++) {
            var newCharacter = new NewCharacterDTO(
                    userUsername,
                    getRandom(CHARACTER_TYPES),
                    generateCharacterName(),
                    null
            );
            var createdCharacter = registerCharacter(newCharacter);
            var level1Character = levelUpCharacter(createdCharacter);
            party.add(level1Character);
        }
        int partyLevel = party.stream().mapToInt(CharacterDTO::getLevel).sum();
        updateUserPartyLevel(userUsername, partyLevel);
        return party;
    }

    public List<CharacterDTO> levelUpOpponentParty(int level, String userUsername, List<CharacterDTO> party) {
        log.info("Leveling up opponent party");

        var currentPartyLevel = getUserByUsername(userUsername).getPartyLevel();
        log.info("Party level: {}", currentPartyLevel);
        for (int i = currentPartyLevel; i < level; i++) {
            var randomPartyMember = party.remove(faker.random().nextInt(party.size()));
            var leveledCharacter = levelUpCharacter(randomPartyMember);
            party.add(leveledCharacter);
        }
        int partyLevel = party.stream().mapToInt(CharacterDTO::getLevel).sum();
        updateUserPartyLevel(userUsername, partyLevel);
        return party;
    }


    // -------------------- Aux Methods  --------------------
    private String generateUsername() {
        log.info("Generating username");
        var categoriesList = List.of(
                faker.name().username(),
                faker.funnyName().name(),
                faker.animal().name(),
                faker.ancient().god(),
                faker.ancient().hero(),
                faker.ancient().titan(),
                faker.ancient().primordial(),
                faker.lordOfTheRings().character()
        );
        return getRandom(categoriesList);
    }

    private boolean isValidUserUsername(String username) {
        log.info("Validating username {}", username);
        var response = userModelProxy.getUserByUsername(username);
        return response.getStatusCodeValue() == 404 &&
                Objects.equals(response.getHeaders().getFirst("error"), "User not found");
    }

    private String generateCharacterName() {
        log.info("Generating character name");
        var categoriesList = List.of(
                faker.ancient().god(),
                faker.ancient().hero(),
                faker.ancient().titan(),
                faker.ancient().primordial(),
                faker.elderScrolls().firstName(),
                faker.elderScrolls().lastName(),
                faker.lordOfTheRings().character(),
                faker.witcher().character(),
                faker.animal().name(),
                faker.dune().planet(),
                faker.leagueOfLegends().champion()
        );
        return getRandom(categoriesList);
    }

    private ArrayList<String> getRandomPoints(int numberOfPoints) {
        var pointsToAdd = new ArrayList<String>();
        for (int i = 0; i < numberOfPoints; i++) {
            var randomPoint = getRandom(LEVEL_UP_POINT_TYPES);
            pointsToAdd.add(randomPoint);
        }
        return pointsToAdd;
    }


    // -------------------- Proxy Methods  --------------------
    private UserDTO registerUser(RegisterUserDTO registerUserDTO) {
        log.info("Registering user {}", registerUserDTO.getUsername());
        log.info("With email {}", registerUserDTO.getEmail());
        log.info("And password {}", registerUserDTO.getPassword());
        return userModelProxy.registerUser(registerUserDTO);
    }

    private CharacterDTO registerCharacter(NewCharacterDTO newCharacter) {
        log.info("Registering character {}", newCharacter.getName());
        log.info("With type {}", newCharacter.getType());
        log.info("For user {}", newCharacter.getUserUsername());
        return characterModelProxy.createCharacter(newCharacter);
    }

    private CharacterDTO levelUpCharacter(CharacterDTO characterToUpdate) {
        log.info("Updating character {}", characterToUpdate.getName());
        log.info("With type {}", characterToUpdate.getType());
        log.info("For user {}", characterToUpdate.getUserUsername());
        var levelUpDTO = new LevelUpDTO(characterToUpdate.getId(), 0, 0, 0);
        var pointsToAdd = getRandomPoints(NUMBER_OF_POINTS_PER_LEVEL_UP);
        for (String point : pointsToAdd) {
            switch (point) {
                case "health" -> levelUpDTO.setHealthPoints(levelUpDTO.getHealthPoints() + 1);
                case "energy" -> levelUpDTO.setEnergyPoints(levelUpDTO.getEnergyPoints() + 1);
                case "attack" -> levelUpDTO.setAttackPoints(levelUpDTO.getAttackPoints() + 1);
            }
        }
        return characterModelProxy.levelUpCharacter(levelUpDTO);
    }

    private UserDTO getUserByUsername(String username) {
        log.info("Getting user by username {}", username);
        return userModelProxy.getUserByUsername(username).getBody();
    }

    private void updateUserPartyLevel(String username, int level) {
        log.info("Updating user {} party level to {}", username, level);
        var userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPartyLevel(level);
        userModelProxy.updateUser(username, userDTO);
    }

}
