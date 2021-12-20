package com.ironhack.usermodelservice.repository;

import com.ironhack.usermodelservice.dao.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.ironhack.usermodelservice.database_utils.DbResetUtil.resetAutoIncrementColumns;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserRepositoryTest {
    private final ApplicationContext applicationContext;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final User newUser = new User(
            "Joaodss",
            "joaodss@email.com",
            "123456",
            Set.of(),
            "",
            0L,
            0L,
            15
    );
    private User u1;
    private User u2;

    @BeforeEach
    void setUp() {
        u1 = new User("Admin", "admin@email.com", "123456");
        u1.setPartyLevel(16);
        u2 = new User("User", "user@email.com", "123456");
        u2.setPartyLevel(18);
        userRepository.saveAll(List.of(u1, u2));
    }

    @AfterEach
    void tearDown() throws SQLException {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "users", "roles");
    }


    // ------------------------------ CRUD TESTING ------------------------------
    @Test
    @Order(1)
    void testCount_numberOfUsersInDatabase_correctAmount() {
        assertEquals(2, userRepository.count());
    }

    // -------------------- Create --------------------
    @Test
    @Order(2)
    void testCreateUser_saveNewUser_storedInRepository() {
        var initialSize = userRepository.count();
        userRepository.save(newUser);
        assertEquals(initialSize + 1, userRepository.count());
    }

    // -------------------- Read --------------------
    @Test
    @Order(3)
    void testReadUser_findAll_returnsListOfObjectsNotEmpty() {
        var allElements = userRepository.findAll();
        assertFalse(allElements.isEmpty());
    }

    @Test
    @Order(3)
    void testReadUser_findById_validId_returnsObjectsWithCorrectValue() {
        var element1 = userRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals(u2, element1);
    }

    @Test
    @Order(3)
    void testReadUser_findById_invalidId_returnsEmpty() {
        var element1 = userRepository.findById(9999L);
        assertTrue(element1.isEmpty());
    }

    // -------------------- Update --------------------
    @Test
    @Order(4)
    void testUpdateUser_changePassword_newPasswordEqualsDefinedValue() {
        var element1 = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Element not found"));

        assertNotEquals("admin123", element1.getPassword());
        element1.setPassword("admin123");
        userRepository.save(element1);

        var updatedElement1 = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals("admin123", updatedElement1.getPassword());
    }

    // -------------------- Delete --------------------
    @Test
    @Order(5)
    void testDeleteUser_deleteUser_validId_deletedFromRepository() {
        var initialSize = userRepository.count();
        userRepository.deleteById(2L);
        assertEquals(initialSize - 1, userRepository.count());
    }

    @Test
    @Order(5)
    void testDeleteUser_deleteUser_invalidId_deletedFromRepository() {
        assertThrows(Exception.class, () -> userRepository.deleteById(9999L));
    }


    // ------------------------------ Custom Queries Testing ------------------------------
    // -------------------- Find By Username --------------------
    @Test
    @Order(6)
    void testFindByUsername_usernameIsValid_returnCorrectValue() {
        var element1 = userRepository.findByUsername("User")
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals(u2, element1); // User u2
    }

    @Test
    @Order(6)
    void testFindByUsername_usernameIsInvalid_returnEmpty() {
        var element1 = userRepository.findByUsername("non existing username");
        assertTrue(element1.isEmpty());
    }

    // -------------------- Find By Email --------------------
    @Test
    @Order(7)
    void testFindByEmail_emailIsValid_returnCorrectValue() {
        var element1 = userRepository.findByEmail("admin@email.com")
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals(u1, element1); // Admin u1
    }

    @Test
    @Order(7)
    void testFindByEmail_emailIsInvalid_returnEmpty() {
        var element1 = userRepository.findByUsername("non existing username");
        assertTrue(element1.isEmpty());
    }


    // -------------------- Find By Party Level Between --------------------
    @Test
    @Order(7)
    void findAllByPartyLevelBetween_containInformation_returnUsersWithLevelInside() {
        userRepository.save(newUser);
        var elements = userRepository.findAllByPartyLevelBetween(14, 16);
        assertEquals(List.of(u1, newUser), elements); // u1 - Admin(16), NEW_USER - Joaodss(15)
    }

    @Test
    @Order(7)
    void findAllByPartyLevelBetween_doesNotContainInformation_returnEmpty() {
        userRepository.save(newUser);
        var elements = userRepository.findAllByPartyLevelBetween(10, 12);
        assertTrue(elements.isEmpty());
    }

}