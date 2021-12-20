package com.ironhack.charactermodelservice.database_utils;

import com.ironhack.charactermodelservice.dao.Character;
import com.ironhack.charactermodelservice.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.Optional;

import static com.ironhack.charactermodelservice.database_utils.DbResetUtil.resetAutoIncrementColumns;
import static com.ironhack.charactermodelservice.enums.Type.ARCHER;
import static com.ironhack.charactermodelservice.enums.Type.WARRIOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class DbResetUtilTest {
    private final ApplicationContext applicationContext;
    private final CharacterRepository roleRepository;

    private final Character newCharacter1 = new Character(
            "user1",
            WARRIOR,
            "SwordGuy",
            ""
    );
    private final Character newCharacter2 = new Character(
            "user2",
            ARCHER,
            "ArcherGirl",
            ""
    );

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws SQLException {
        roleRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "characters");
    }


    @Test
    @Order(1)
    void testDbTestUtility_deleteAllRolesCreateNewRole_noAutoIncrementReset_idKeepsIncrementing() {
        // Assert Empty
        assertEquals(0, roleRepository.count());

        // Create first role
        roleRepository.save(newCharacter1);

        // Read saved role
        var storedCharacter = roleRepository.findAllByUserUsernameAndIsAlive("user1", true).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Character not found"));
        assertEquals(1, storedCharacter.getId());
        assertEquals("SwordGuy", storedCharacter.getName());

        // DeleteAll
        roleRepository.deleteAll();
        assertEquals(0, roleRepository.count());

        // Create Second
        roleRepository.save(newCharacter2);


        // Read second saved role
        storedCharacter = roleRepository.findAllByUserUsernameAndIsAlive("user2", true).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Character not found"));
        assertEquals(Optional.empty(), roleRepository.findById(1L));
        assertEquals(2, storedCharacter.getId());
        assertEquals("ArcherGirl", storedCharacter.getName());
    }

    @Test
    @Order(2)
    void testDbTestUtility_deleteAllRolesCreateNewRole_autoIncrementReset_idResetsWhenCalled() throws SQLException {
        // Assert Empty
        assertEquals(0, roleRepository.count());

        // Create first role
        roleRepository.save(newCharacter1);

        // Read saved role
        var storedCharacter = roleRepository.findAllByUserUsernameAndIsAlive("user1", true).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Character not found"));
        assertEquals(1, storedCharacter.getId());
        assertEquals("SwordGuy", storedCharacter.getName());

        // DeleteAll
        roleRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "characters");
        assertEquals(0, roleRepository.count());

        // Create Second
        roleRepository.save(newCharacter2);


        // Read second saved role
        storedCharacter = roleRepository.findAllByUserUsernameAndIsAlive("user2", true).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Character not found"));
        assertEquals(Optional.of(storedCharacter), roleRepository.findById(1L));
        assertEquals(1, storedCharacter.getId());
        assertEquals("ArcherGirl", storedCharacter.getName());
    }

}
