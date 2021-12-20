package com.ironhack.opponentselectionservice.service;

import com.ironhack.opponentselectionservice.dto.CharacterDTO;
import com.ironhack.opponentselectionservice.proxy.CharacterModelProxy;
import com.ironhack.opponentselectionservice.proxy.UserModelProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ironhack.opponentselectionservice.util.Randomizer.getRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpponentSelectionServiceImpl implements OpponentSelectionService {
    private final UserModelProxy userModelProxy;
    private final CharacterModelProxy characterModelProxy;
    private final OpponentCreationService opponentCreationService;

    private final int MIN_POSSIBLE_OPPONENTS = 20;
    private final int MAX_DIFFERENCE_OF_PARTY_LEVEL = 3;
    private final int NUMBER_OF_OPPONENTS = 5;


    public List<CharacterDTO> getOpponentCharacters(int partyLevel) {
        log.info("Getting opponents for party level: {}", partyLevel);
        List<CharacterDTO> selectedParty;
        int minLevel = partyLevel - MAX_DIFFERENCE_OF_PARTY_LEVEL;
        int maxLevel = partyLevel + MAX_DIFFERENCE_OF_PARTY_LEVEL;
        var possibleOpponentNames =
                getPossibleOpponentNames(minLevel, maxLevel);
        var realPossibleOpponentNames = possibleOpponentNames.stream()
                .filter(opponent -> isValidOpponent(opponent, minLevel, maxLevel))
                .toList();
        if (isValidOpponentList(realPossibleOpponentNames)) {
            var selectedUser = getRandom(realPossibleOpponentNames);
            selectedParty = getParty(selectedUser);
        } else {
            int randomLevel = getRandom(minLevel, maxLevel);
            selectedParty = opponentCreationService.generateOpponent(randomLevel);
        }
        return selectFighters(selectedParty, NUMBER_OF_OPPONENTS);
    }

    private List<CharacterDTO> selectFighters(List<CharacterDTO> party, int nrOfFighters) {
        log.info("Selecting fighters from party: {}", party);
        List<CharacterDTO> fightersList = party.stream()
                .sorted(Comparator.comparingInt(CharacterDTO::getLevel).reversed())
                .limit(nrOfFighters)
                .collect(Collectors.toList());
        Collections.shuffle(fightersList);
        return fightersList;
    }

    // -------------------- Aux Methods --------------------
    private boolean isValidOpponent(String opponentUsername, int minLevel, int maxLevel) {
        var opponentParty = getParty(opponentUsername);
        if (opponentParty.size() == 10) return true;
        int realPartyLevel = opponentParty.stream().mapToInt(CharacterDTO::getLevel).sum() + (10 - opponentParty.size());
        return realPartyLevel >= minLevel && realPartyLevel <= maxLevel && opponentParty.size() >= NUMBER_OF_OPPONENTS;
    }

    private boolean isValidOpponentList(List<String> opponentList) {
        log.info("Validating possible user opponents: {}", opponentList);
        return opponentList.size() >= MIN_POSSIBLE_OPPONENTS;
    }

    // -------------------- Proxy Calls --------------------
    private List<String> getPossibleOpponentNames(int min, int max) {
        log.info("Getting possible opponents between levels: {} and {}", min, max);
        return userModelProxy.getAllUsersUsernamesByPartyLevelBetween(min, max);
    }

    private List<CharacterDTO> getParty(String userUsername) {
        log.info("Getting party of user: {}", userUsername);
        return characterModelProxy.getCharactersByUserUsernameActive(userUsername);
    }

}
