package com.ironhack.charactermodelservice.repository;

import com.ironhack.charactermodelservice.dao.Character;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

import static com.ironhack.charactermodelservice.database_utils.DbResetUtil.resetAutoIncrementColumns;
import static com.ironhack.charactermodelservice.enums.Type.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CharacterRepositoryTest {
    private final ApplicationContext applicationContext;
    private final CharacterRepository characterRepository;

    private final Character newCharacter = new Character(
            "joaodss",
            WARRIOR,
            "SwordGuy",
            ""
    );
    private Character character1;
    private Character character2;
    private Character character3;

    @BeforeEach
    void setUp() {
        character1 = new Character(
                "user",
                ARCHER,
                "ArcherGuy",
                ""
        );
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
        characterRepository.saveAll(List.of(character1, character2, character3));
    }

    @AfterEach
    void tearDown() throws SQLException {
        characterRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "characters");
    }


    // ------------------------------ CRUD TESTING ------------------------------
    @Test
    @Order(1)
    void testCount_numberOfCharactersInDatabase_correctAmount() {
        assertEquals(3, characterRepository.count());
    }

    // -------------------- Create --------------------
    @Test
    @Order(2)
    void testCreateCharacter_saveNewCharacter_storedInRepository() {
        var initialSize = characterRepository.count();
        characterRepository.save(newCharacter);
        assertEquals(initialSize + 1, characterRepository.count());
    }

    // -------------------- Read --------------------
    @Test
    @Order(3)
    void testReadCharacter_findAll_returnsListOfObjectsNotEmpty() {
        var allElements = characterRepository.findAll();
        assertFalse(allElements.isEmpty());
    }

    @Test
    @Order(3)
    void testReadCharacter_findById_validId_returnsObjectsWithCorrectValue() {
        var element1 = characterRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals(character2, element1);
    }

    @Test
    @Order(3)
    void testReadCharacter_findById_invalidId_returnsEmpty() {
        var element1 = characterRepository.findById(-1L);
        assertTrue(element1.isEmpty());
    }

    // -------------------- Update --------------------
    @Test
    @Order(4)
    void testUpdateCharacter_changeName_newNameEqualsDefinedValue() {
        var element1 = characterRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Element not found"));

        assertNotEquals("New Name", element1.getName());
        element1.setName("New Name");
        characterRepository.save(element1);

        var updatedElement1 = characterRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals("New Name", updatedElement1.getName());
    }

    // -------------------- Delete --------------------
    @Test
    @Order(5)
    void testDeleteCharacter_deleteCharacter_validId_deletedFromRepository() {
        var initialSize = characterRepository.count();
        characterRepository.deleteById(2L);
        assertEquals(initialSize - 1, characterRepository.count());
    }

    @Test
    @Order(5)
    void testDeleteCharacter_deleteCharacter_invalidId_deletedFromRepository() {
        assertThrows(Exception.class, () -> characterRepository.deleteById(-1L));
    }


    // ------------------------------ Custom Queries Testing ------------------------------
    // -------------------- Find By UserUsername And IsAlive --------------------
    @Test
    @Order(6)
    void testFindByUserUsernameAndIsAlive_validUserUsername_alive_returnsListOfObjectsNotEmpty() {
        var storedElements = characterRepository.findAllByUserUsernameAndIsAlive("joaodss", true);
        assertFalse(storedElements.contains(character2));
        assertTrue(storedElements.contains(character3));
    }

    @Test
    @Order(6)
    void testFindByUserUsernameAndIsAlive_validUserUsername_graveyard_returnsListOfObjectsNotEmpty() {
        var storedElements = characterRepository.findAllByUserUsernameAndIsAlive("joaodss", false);
        assertTrue(storedElements.contains(character2));
        assertFalse(storedElements.contains(character3));
    }

    @Test
    @Order(6)
    void testFindByUserUsernameAndIsAlive_invalidUserUsername_returnsEmptyList() {
        var storedElements = characterRepository.findAllByUserUsernameAndIsAlive("invalidUsername", true);
        assertTrue(storedElements.isEmpty());
    }

}
