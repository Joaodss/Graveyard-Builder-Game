package com.ironhack.charactermodelservice.service;

import com.ironhack.charactermodelservice.dao.Character;
import com.ironhack.charactermodelservice.dto.CharacterDTO;
import com.ironhack.charactermodelservice.dto.NewCharacterDTO;
import com.ironhack.charactermodelservice.repository.CharacterRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.ironhack.charactermodelservice.enums.Type.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {
    @InjectMocks
    private CharacterServiceImpl characterService;
    @Mock
    private CharacterRepository characterRepository;


    private Character character1;
    private Character character2;
    private Character character3;
    private NewCharacterDTO newCharacter;
    private Character character4;
    private CharacterDTO newUpdatedCharacter1;
    private Character updatedCharacter1;
    private CharacterDTO newUpdatedCharacter2;
    private Character updatedCharacter2;

    @BeforeEach
    void setUp() {
        character1 = new Character(
                "user",
                ARCHER,
                "ArcherGuy",
                ""
        );
        character1.setId(1L);
        character2 = new Character(
                "joaodss",
                MAGE,
                "MageGirl",
                ""
        );

        character2.setIsAlive(false);
        character3 = new Character(
                "joaodss",
                MAGE,
                "MageGuy",
                ""
        );
        character4 = new Character(
                "joaodss",
                WARRIOR,
                "SwordGuy",
                ""
        );

        newCharacter = new NewCharacterDTO(
                "joaodss",
                "warrior",
                "SwordGuy",
                ""
        );

        newUpdatedCharacter1 = new CharacterDTO(
                1L,
                null,
                null,
                false,
                Instant.now().toString(),
                null,
                "",
                10,
                4627L,
                900,
                0,
                null,
                200,
                200,
                null,
                22,
                null,
                null,
                null
        );
        updatedCharacter1 = new Character(
                1L,
                character1.getUserUsername(),
                character1.getType(),
                false,
                Instant.now(),
                character1.getName(),
                "",
                10,
                4627L,
                900,
                0,
                character1.getPassiveChance(),
                200,
                200,
                character1.getStrength(),
                22,
                character1.getMaxMana(),
                character1.getCurrentMana(),
                character1.getIntelligence()
        );
        newUpdatedCharacter2 = new CharacterDTO();
        newUpdatedCharacter2.setId(1L);
        newUpdatedCharacter2.setPictureURL("Hello.png");
        updatedCharacter2 = character1;
        updatedCharacter2.setPictureURL("Hello.png");
    }

    @AfterEach
    void tearDown() {
    }

    // -------------------- Get methods --------------------
    // ---------- Get All ----------
    @Test
    @Order(1)
    void testGetAllCharacters_usesRepositoryFindAll() {
        // When
        characterService.getAllCharacters();
        // Then
        verify(characterRepository).findAll();
        verifyNoMoreInteractions(characterRepository);
    }

    // ---------- Get By Id ----------
    @Test
    @Order(2)
    void testGetCharacterById_usesRepositoryFindById() {
        // When
        characterService.getCharacterById(1L);
        // Then
        verify(characterRepository).findById(1L);
        verifyNoMoreInteractions(characterRepository);
    }

    @Test
    @Order(2)
    void testGetCharacterById_validId_returnsCharacter() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.of(character1));
        // When
        var result = characterService.getCharacterById(1L);
        // Then
        assertEquals(new CharacterDTO(character1), result);
    }

    @Test
    @Order(2)
    void testGetCharacterById_invalidId_returnsNull() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // When
        var result = characterService.getCharacterById(-1L);
        // Then
        assertNull(result);
    }

    // ---------- Get Alive By UserUsername ----------
    @Test
    @Order(3)
    void testGetAliveCharactersByUserUsername_usesRepositoryFindAllByUserUsername() {
        // When
        characterService.getAliveCharactersByUserUsername("joaodss");
        // Then
        verify(characterRepository).findAllByUserUsernameAndIsAlive(anyString(), eq(true));
        verifyNoMoreInteractions(characterRepository);
    }

    @Test
    @Order(3)
    void testGetAliveCharactersByUserUsername_validUserUsername_returnsCharacter() {
        // Given
        when(characterRepository.findAllByUserUsernameAndIsAlive(anyString(), eq(true)))
                .thenReturn(List.of(character3, character4));
        // When
        var result = characterService.getAliveCharactersByUserUsername("joaodss");
        // Then
        var expectedResult = List.of(new CharacterDTO(character3), new CharacterDTO(character3));
        assertTrue(result.containsAll(expectedResult));
    }

    @Test
    @Order(3)
    void testGetAliveCharactersByUserUsername_invalidUserUsername_returnsNull() {
        // Given
        when(characterRepository.findAllByUserUsernameAndIsAlive(anyString(), eq(true)))
                .thenReturn(List.of());
        // When
        var result = characterService.getAliveCharactersByUserUsername("");
        // Then
        assertTrue(result.isEmpty());
    }

    // ---------- Get Dead By UserUsername ----------
    @Test
    @Order(4)
    void testGetDeadCharactersByUserUsername_usesRepositoryFindAllByUserUsername() {
        // When
        characterService.getDeadCharactersByUserUsername("joaodss");
        // Then
        verify(characterRepository).findAllByUserUsernameAndIsAlive(anyString(), eq(false));
        verifyNoMoreInteractions(characterRepository);
    }

    @Test
    @Order(4)
    void testGetDeadCharactersByUserUsername_validUserUsername_returnsCharacter() {
        // Given
        when(characterRepository.findAllByUserUsernameAndIsAlive(anyString(), eq(false)))
                .thenReturn(List.of(character2));
        // When
        var result = characterService.getDeadCharactersByUserUsername("joaodss");
        // Then
        var expectedResult = List.of(new CharacterDTO(character2));
        assertEquals(expectedResult, result);
    }

    @Test
    @Order(4)
    void testGetDeadCharactersByUserUsername_invalidUserUsername_returnsNull() {
        // Given
        when(characterRepository.findAllByUserUsernameAndIsAlive(anyString(), eq(false)))
                .thenReturn(List.of());
        // When
        var result = characterService.getDeadCharactersByUserUsername("");
        // Then
        assertTrue(result.isEmpty());
    }


    // -------------------- Create methods --------------------
    @Test
    @Order(5)
    void testCreateCharacter_usesRepositorySave() {
        // Given
        when(characterRepository.save(any(Character.class)))
                .thenReturn(character4);
        // When
        characterService.createCharacter(newCharacter);
        // Then
        verify(characterRepository).save(new Character(newCharacter));
        verifyNoMoreInteractions(characterRepository);
    }

    @Test
    @Order(5)
    void testCreateCharacter_validCharacter_returnsCharacter() {
        // Given
        when(characterRepository.save(any(Character.class)))
                .thenReturn(character4);
        // When
        var result = characterService.createCharacter(newCharacter);
        // Then
        assertEquals(new CharacterDTO(character4), result);
    }


    // -------------------- Update methods --------------------
    @Test
    @Order(6)
    void testUpdateCharacter_usesRepositorySave() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.of(character1));
        when(characterRepository.save(any(Character.class)))
                .thenReturn(updatedCharacter1);
        // When
        characterService.updateCharacter(newUpdatedCharacter1);
        // Then
        verify(characterRepository).findById(anyLong());
        verify(characterRepository).save(any(Character.class));
        verifyNoMoreInteractions(characterRepository);
    }

    @Test
    @Order(6)
    void testUpdateCharacter_validCharacter_returnsCharacter() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.of(character1));
        when(characterRepository.save(any(Character.class)))
                .thenReturn(updatedCharacter1);
        // When
        var result = characterService.updateCharacter(newUpdatedCharacter1);
        // Then
        assertEquals(new CharacterDTO(updatedCharacter1), result);
    }

    @Test
    @Order(6)
    void testUpdateCharacter_updatePictureUrl_validCharacter_returnsCharacter() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.of(character2));
        when(characterRepository.save(any(Character.class)))
                .thenReturn(updatedCharacter2);
        // When
        var result = characterService.updateCharacter(newUpdatedCharacter2);
        // Then
        assertEquals(new CharacterDTO(updatedCharacter2), result);
    }

    @Test
    @Order(6)
    void testUpdateCharacter_invalidCharacter_returnsNull() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(Exception.class, () -> characterService.updateCharacter(newUpdatedCharacter1));
    }


    // -------------------- Delete methods --------------------
    @Test
    @Order(7)
    void testDeleteCharacter_usesRepositoryDeleteById() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.of(character1));
        // When
        characterService.deleteCharacter(anyLong());
        // Then
        verify(characterRepository).findById(anyLong());
        verify(characterRepository).delete(any(Character.class));
        verifyNoMoreInteractions(characterRepository);
    }

    @Test
    @Order(7)
    void testDeleteCharacter_invalidCharacter_returnsNull() {
        // Given
        when(characterRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(Exception.class, () -> characterService.deleteCharacter(anyLong()));
    }

}
