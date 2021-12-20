package com.ironhack.usermodelservice.service;

import com.ironhack.usermodelservice.dao.Role;
import com.ironhack.usermodelservice.dao.User;
import com.ironhack.usermodelservice.dto.RegisterUserDTO;
import com.ironhack.usermodelservice.dto.UserDTO;
import com.ironhack.usermodelservice.repository.RoleRepository;
import com.ironhack.usermodelservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    @Spy
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;


    private User admin;
    private User user;
    private User joaodss;
    private Role adminRole;
    private Role userRole;
    private RegisterUserDTO newUserAlbert;
    private User albert;
    private UserDTO updatedJoaodss;
    private UserDTO updatedJoaodssGold;

    @BeforeEach
    void setUp() {
        adminRole = new Role(1L, "ADMIN");
        userRole = new Role(2L, "USER");

        admin = new User(
                1L,
                "Admin",
                "admin@email.com",
                "123456",
                Set.of(adminRole),
                "",
                10L,
                12L,
                16
        );
        user = new User(
                2L,
                "User",
                "user@email.com",
                "123456",
                Set.of(userRole),
                "",
                15L,
                28L,
                18
        );
        joaodss = new User(
                3L,
                "Joaodss",
                "joaodss@email.com",
                "123456",
                Set.of(userRole),
                "",
                7L,
                8L,
                15
        );

        newUserAlbert = new RegisterUserDTO(
                "albert",
                "albert@email.com",
                "654321"
        );
        albert = new User(newUserAlbert);
        albert.setId(4L);

        updatedJoaodss = new UserDTO(
                "joaodss",
                "joaodss@email.com",
                Set.of(userRole.getName()),
                "url",
                9L,
                null,
                18
        );

        updatedJoaodssGold = new UserDTO(
                null,
                null,
                null,
                null,
                null,
                26L,
                null
        );
    }


    // -------------------- Get methods --------------------
    // ---------- Get All ----------
    @Test
    @Order(1)
    void testGetAllUsers_usesRepositoryFindAll() {
        // When
        userService.getAllUsers();
        // Then
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    // ---------- Get By Id ----------
    @Test
    @Order(2)
    void testGetUserById_usesUserRepositoryFindById() {
        // When
        userService.getUserById(anyLong());
        // Then
        verify(userRepository).findById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @Order(2)
    void testGetUserById_validId_returnsValidObject() {
        // Given
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(admin));
        // When
        var storedUser = userService.getUserById(1L);
        // Then
        assertEquals(new UserDTO(admin), storedUser);
    }


    @Test
    @Order(2)
    void testGetUserById_invalidId_returnsNull() {
        // Given
        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());
        // When
        var storedUser = userService.getUserById(999L);
        // Then
        assertNull(storedUser);
    }

    // ---------- Get By Username ----------
    @Test
    @Order(3)
    void testGetUserByUsername_usesUserRepositoryFindByUsername() {
        // When
        userService.getUserByUsername(anyString());
        // Then
        verify(userRepository).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @Order(3)
    void testGetUserByUsername_validId_returnsValidObject() {
        // Given
        when(userRepository.findByUsername("User"))
                .thenReturn(Optional.of(user));
        // When
        var storedUser = userService.getUserByUsername("User");
        // Then
        assertEquals(new UserDTO(user), storedUser);
    }


    @Test
    @Order(3)
    void testGetUserByUsername_invalidId_returnsNull() {
        // Given
        when(userRepository.findByUsername("Non existing"))
                .thenReturn(Optional.empty());
        // When
        var storedUser = userService.getUserByUsername("Non existing");
        // Then
        assertNull(storedUser);
    }

    // ---------- Get By Email ---------
    @Test
    @Order(4)
    void testGetUserByEmail_usesUserRepositoryFindByEmail() {
        // When
        userService.getUserByEmail(anyString());
        // Then
        verify(userRepository).findByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @Order(4)
    void testGetUserByEmail_validId_returnsValidObject() {
        // Given
        when(userRepository.findByEmail("joaodss@email.com"))
                .thenReturn(Optional.of(joaodss));
        // When
        var storedUser = userService.getUserByEmail("joaodss@email.com");
        // Then
        assertEquals(new UserDTO(joaodss), storedUser);
    }

    @Test
    @Order(4)
    void testGetUserByEmail_invalidId_returnsNull() {
        // Given
        when(userRepository.findByEmail("non existing email"))
                .thenReturn(Optional.empty());
        // When
        var storedUser = userService.getUserByEmail("non existing email");
        // Then
        assertNull(storedUser);
    }

    // ---------- Get Users Ids By Party Level ----------
    @Test
    @Order(5)
    void testGetAllIdsByPartyLevelBetween_usesUserRepositoryFindAllIdsByPartyLevelBetween() {
        // When
        userService.getAllUsersUsernamesByPartyLevelBetween(anyInt(), anyInt());
        // Then
        verify(userRepository).findAllByPartyLevelBetween(anyInt(), anyInt());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @Order(5)
    void testGetAllIdsByPartyLevelBetween_validBetweenRange_returnsListWithValues() {
        // Given
        when(userRepository.findAllByPartyLevelBetween(15, 17))
                .thenReturn(List.of(admin, joaodss));
        // When
        var storedUsers = userService.getAllUsersUsernamesByPartyLevelBetween(15, 17);
        // Then
        assertEquals(List.of(admin.getUsername(), joaodss.getUsername()), storedUsers);
    }

    @Test
    @Order(5)
    void testGetAllIdsByPartyLevelBetween_invalidBetweenRange_returnsEmpty() {
        // Given
        when(userRepository.findAllByPartyLevelBetween(10, 12))
                .thenReturn(List.of());
        // When
        var storedUsers = userService.getAllUsersUsernamesByPartyLevelBetween(10, 12);
        // Then
        assertEquals(List.of(), storedUsers);
    }


    // -------------------- Register methods --------------------
    // ---------- Register User ----------
    @Test
    @Order(6)
    void testRegisterUser_usesUserRepositorySave() {
        // Given
        when(userRepository.save(any(User.class)))
                .thenReturn(albert);
        doNothing().when(userService).addRoleToUser(anyString(), eq("USER"));
        // When
        var result = userService.registerUser(newUserAlbert);
        // Then
        var argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        assertEquals(new User(newUserAlbert), argumentCaptor.getValue());

        verify(userService).addRoleToUser(eq(newUserAlbert.getUsername()), eq("USER"));
        verifyNoMoreInteractions(userRepository);

        assertEquals(new UserDTO(albert), result);
    }

    // ---------- Add Role to User ----------
    @Test
    @Order(7)
    void testAddRoleToUser_usesUserRepositoryFindByUsername() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(albert));
        when(roleRepository.findByName(anyString()))
                .thenReturn(Optional.of(userRole));
        // When
        userService.addRoleToUser(anyString(), anyString());
        // Then
        verify(userRepository).findByUsername(anyString());
        verify(roleRepository).findByName(anyString());
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    @Order(7)
    void testAddRoleToUser_UserIdNotFound_throwsError() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(Exception.class, () -> userService.addRoleToUser(anyString(), anyString()));
    }

    @Test
    @Order(7)
    void testAddRoleToUser_RoleNotFound_throwsError() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(albert));
        when(roleRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        // When
        userService.addRoleToUser(anyString(), anyString());
        // Then
        verify(userRepository).findByUsername(anyString());
        verify(roleRepository).findByName(anyString());
        verify(roleRepository).save(any(Role.class));
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(roleRepository);
    }


    // -------------------- Update methods --------------------
    // ---------- Update User ----------
    @Test
    @Order(8)
    void testUpdateUser_usesUserRepositoryFindByUsername() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(joaodss));
        when(userRepository.save(any(User.class)))
                .thenReturn(joaodss);
        // When
        var updatedUser = userService.updateUser("joaodss", updatedJoaodss);
        // Then
        verify(userRepository).findByUsername(anyString());
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
        joaodss.setUsername("joaodss");
        joaodss.setProfilePictureUrl("url");
        joaodss.setExperience(9L);
        joaodss.setPartyLevel(18);
        assertEquals(new UserDTO(joaodss), updatedUser);
    }

    @Test
    @Order(8)
    void testUpdateUser_updateGold_usesUserRepositoryFindByUsername() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(joaodss));
        when(userRepository.save(any(User.class)))
                .thenReturn(joaodss);
        // When
        var updatedUser = userService.updateUser("joaodss", updatedJoaodssGold);
        // Then
        verify(userRepository).findByUsername(anyString());
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
        joaodss.setGold(26L);
        assertEquals(new UserDTO(joaodss), updatedUser);
    }

    @Test
    @Order(8)
    void testUpdateUser_UserNotFound_throwsError() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(Exception.class, () -> userService.updateUser(anyString(), any(UserDTO.class)));
    }

    // ---------- Update User's Password ----------
    @Test
    @Order(9)
    void testUpdateUserPassword_usesUserRepositoryFindByUsername() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        // When
        userService.changePassword(anyString(), anyString());
        // Then
        verify(userRepository).findByUsername(anyString());
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @Order(9)
    void testUpdateUserPassword_UserNotFound_throwsError() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(Exception.class, () -> userService.changePassword(anyString(), anyString()));
    }


    // -------------------- Delete methods --------------------
    // ---------- Delete User ----------
    @Test
    @Order(10)
    void testDeleteUser_usesUserRepositoryFindByUsername() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(admin));
        // When
        userService.deleteUser(anyString());
        // Then
        verify(userRepository).findByUsername(anyString());
        verify(userRepository).deleteById(admin.getId());
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    @Order(10)
    void testDeleteUser_UserNotFound_throwsError() {
        // Given
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(Exception.class, () -> userService.deleteUser(anyString()));
    }

}
