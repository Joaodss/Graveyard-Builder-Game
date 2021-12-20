package com.ironhack.usermodelservice.database_utils;

import com.ironhack.usermodelservice.dao.Role;
import com.ironhack.usermodelservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.Optional;

import static com.ironhack.usermodelservice.database_utils.DbResetUtil.resetAutoIncrementColumns;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class DbResetUtilTest {
    private final ApplicationContext applicationContext;
    private final RoleRepository roleRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws SQLException {
        roleRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "roles");
    }


    @Test
    @Order(1)
    void testDbTestUtility_deleteAllRolesCreateNewRole_noAutoIncrementReset_idKeepsIncrementing() {
        // Assert Empty
        assertEquals(0, roleRepository.count());

        // Create first role
        roleRepository.save(new Role("ROLE"));

        // Read saved role
        var storedRole = roleRepository.findByName("ROLE")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        assertEquals(1, storedRole.getId());
        assertEquals("ROLE", storedRole.getName());

        // DeleteAll
        roleRepository.deleteAll();
        assertEquals(0, roleRepository.count());

        // Create Second
        roleRepository.save(new Role("NEW_ROLE"));


        // Read second saved role
        storedRole = roleRepository.findByName("NEW_ROLE")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        assertEquals(Optional.empty(), roleRepository.findById(1L));
        assertEquals(2, storedRole.getId());
        assertEquals("NEW_ROLE", storedRole.getName());
    }

    @Test
    @Order(2)
    void testDbTestUtility_deleteAllRolesCreateNewRole_autoIncrementReset_idResetsWhenCalled() throws SQLException {
        // Assert Empty
        assertEquals(0, roleRepository.count());

        // Create first role
        roleRepository.save(new Role("ROLE"));

        // Read saved role
        var storedRole = roleRepository.findByName("ROLE")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        assertEquals(1, storedRole.getId());
        assertEquals("ROLE", storedRole.getName());

        // DeleteAll
        roleRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "roles");
        assertEquals(0, roleRepository.count());

        // Create Second
        roleRepository.save(new Role("NEW_ROLE"));


        // Read second saved role
        storedRole = roleRepository.findByName("NEW_ROLE")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        assertEquals(Optional.of(storedRole), roleRepository.findById(1L));
        assertEquals(1, storedRole.getId());
        assertEquals("NEW_ROLE", storedRole.getName());
    }

}
