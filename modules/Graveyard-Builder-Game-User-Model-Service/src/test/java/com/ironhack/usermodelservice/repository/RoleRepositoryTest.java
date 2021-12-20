package com.ironhack.usermodelservice.repository;

import com.ironhack.usermodelservice.dao.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

import static com.ironhack.usermodelservice.database_utils.DbResetUtil.resetAutoIncrementColumns;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class RoleRepositoryTest {
    private final ApplicationContext applicationContext;
    private final RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        var r1 = new Role("USER");
        var r2 = new Role("ADMIN");
        roleRepository.saveAll(List.of(r1, r2));
    }

    @AfterEach
    void tearDown() throws SQLException {
        roleRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "roles");
    }


    // ------------------------------ CRUD TESTING ------------------------------
    @Test
    @Order(1)
    void testCount_numberOfRolesInDatabase_correctAmount() {
        assertEquals(2, roleRepository.count());
    }

    // -------------------- Create --------------------
    @Test
    @Order(2)
    void testCreateRole_saveNew_storedInRepository() {
        var initialSize = roleRepository.count();
        roleRepository.save(new Role("TESTER"));
        assertEquals(initialSize + 1, roleRepository.count());
    }

    // -------------------- Read --------------------
    @Test
    @Order(3)
    void testReadRole_findAll_returnsListOfObjectsNotEmpty() {
        var allElements = roleRepository.findAll();
        assertFalse(allElements.isEmpty());
    }

    @Test
    @Order(3)
    void testReadRole_findById_validId_returnsObjectsWithCorrectName() {
        var element1 = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals("ADMIN", element1.getName());
    }

    @Test
    @Order(3)
    void testReadRole_findById_invalidId_returnsEmpty() {
        var element1 = roleRepository.findById(9999L);
        assertTrue(element1.isEmpty());
    }

    // -------------------- Update --------------------
    @Test
    @Order(4)
    void testUpdateRole_changeName_newNameEqualsDefinedValue() {
        var element1 = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Element not found"));

        assertNotEquals("NEW_ROLE", element1.getName());
        element1.setName("NEW_ROLE");
        roleRepository.save(element1);

        var updatedElement1 = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals("NEW_ROLE", updatedElement1.getName());
    }

    // -------------------- Delete --------------------
    @Test
    @Order(5)
    void testDeleteRole_delete_validId_deletedFromRepository() {
        var initialSize = roleRepository.count();
        roleRepository.deleteById(2L);
        assertEquals(initialSize - 1, roleRepository.count());
    }

    @Test
    @Order(5)
    void testDeleteRole_delete_invalidId_deletedFromRepository() {
        assertThrows(Exception.class, () -> roleRepository.deleteById(9999L));
    }


    // ------------------------------ Custom Queries Testing ------------------------------
    // -------------------- Find By Name --------------------
    @Test
    @Order(6)
    void testFindByName_nameIsValid_returnCorrectAccount() {
        var element1 = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Element not found"));
        assertEquals("USER", element1.getName());
    }

    @Test
    @Order(6)
    void testFindByName_nameIsInvalid_returnEmpty() {
        var element1 = roleRepository.findByName("non existing name");
        assertTrue(element1.isEmpty());
    }

}
