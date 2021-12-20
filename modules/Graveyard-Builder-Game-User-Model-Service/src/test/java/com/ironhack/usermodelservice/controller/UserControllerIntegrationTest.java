package com.ironhack.usermodelservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.usermodelservice.dao.Role;
import com.ironhack.usermodelservice.dao.User;
import com.ironhack.usermodelservice.dto.NewPasswordDTO;
import com.ironhack.usermodelservice.dto.RegisterUserDTO;
import com.ironhack.usermodelservice.dto.UserDTO;
import com.ironhack.usermodelservice.repository.RoleRepository;
import com.ironhack.usermodelservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.ironhack.usermodelservice.database_utils.DbResetUtil.resetAutoIncrementColumns;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserControllerIntegrationTest {
    private final ApplicationContext applicationContext;
    private final WebApplicationContext webApplicationContext;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl = "/api/v1/users";
    private MockMvc mockMvc;
    private User admin;
    private User user;
    private User joaodss;
    private RegisterUserDTO newUserAlbert;
    private User notStoredAlbert;
    private UserDTO updatedJoaodss;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        roleRepository.saveAll(List.of(adminRole, userRole));

        admin = new User(
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
                "Joaodss",
                "joaodss@email.com",
                "123456",
                Set.of(userRole),
                "",
                7L,
                8L,
                15
        );
        userRepository.saveAll(List.of(admin, user, joaodss));

        newUserAlbert = new RegisterUserDTO(
                "albert",
                "albert@email.com",
                "654321"
        );
        notStoredAlbert = new User(newUserAlbert);
        notStoredAlbert.setRoles(Set.of(userRole));
        notStoredAlbert.setId(4L);

        updatedJoaodss = new UserDTO(
                "joaodss",
                "joaodss@email.com",
                Set.of(userRole.getName()),
                "url",
                9L,
                null,
                18
        );
    }

    @AfterEach
    void tearDown() throws SQLException {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "users", "roles");
    }

    // -------------------- GET Routes --------------------
    // ---------- Get All Users ----------
    @Test
    @Order(1)
    void testGetAllUsers_valuesOnDatabase_ListWithValues() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/all")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder("Admin", "User", "Joaodss")))
                .andReturn();
        var resultBody = List.of(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO[].class));
        var expectedBody = List.of(new UserDTO(admin), new UserDTO(user), new UserDTO(joaodss));
        assertTrue(resultBody.containsAll(expectedBody));
    }

    @Test
    @Order(1)
    void testGetAllUsers_emptyDatabase_emptyList() throws Exception {
        userRepository.deleteAll();
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/all")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
        var resultBody = List.of(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO[].class));
        assertTrue(resultBody.isEmpty());
    }

    // ---------- Get User By Id ----------
    @Test
    @Order(2)
    void testGetUserById_userExists_userReturned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/id/{id}", admin.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(admin.getUsername())))
                .andExpect(jsonPath("$.email", is(admin.getEmail())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        assertEquals(new UserDTO(admin), resultBody);
    }

    @Test
    @Order(2)
    void testGetUserById_userDoesNotExist_userNotFound() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/id/{id}", -1L)
                )
                .andExpect(status().isNotFound())
                .andReturn();
        var responseError = mvcResult.getResponse().getHeader("error");
        assertEquals("User not found", responseError);
    }

    // ---------- Get User By Username ----------
    @Test
    @Order(3)
    void testGetUserByUsername_userExists_userReturned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/username/{username}", user.getUsername())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        assertEquals(new UserDTO(user), resultBody);
    }

    @Test
    @Order(3)
    void testGetUserByUsername_userDoesNotExist_userNotFound() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/username/{username}", "Non Existing username..")
                )
                .andExpect(status().isNotFound())
                .andReturn();
        var responseError = mvcResult.getResponse().getHeader("error");
        assertEquals("User not found", responseError);
    }

    // ---------- Get User By Email ----------
    @Test
    @Order(4)
    void testGetUserByEmail_userExists_userReturned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/email/{email}", joaodss.getEmail())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(joaodss.getUsername())))
                .andExpect(jsonPath("$.email", is(joaodss.getEmail())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        assertEquals(new UserDTO(joaodss), resultBody);
    }

    @Test
    @Order(4)
    void testGetUserByEmail_userDoesNotExist_userNotFound() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/email/{email}", "Non Existing email..")
                )
                .andExpect(status().isNotFound())
                .andReturn();
        var responseError = mvcResult.getResponse().getHeader("error");
        assertEquals("User not found", responseError);
    }

    // ---------- Get All Users Id By Party Level Between ----------
    @Test
    @Order(5)
    void testGetAllUsersIdByPartyLevelBetween_partyLevelExists_usersReturned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/partyLevel")
                                .param("min", "15")
                                .param("max", "17")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();
        var resultBody = List.of(
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), String[].class)
        );
        var expectedBody = List.of(admin.getUsername(), joaodss.getUsername());
        assertTrue(resultBody.containsAll(expectedBody));
    }

    @Test
    @Order(5)
    void testGetAllUsersIdByPartyLevelBetween_partyLevelExistsMinAndMaxNotDefined_usersReturned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/partyLevel")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();
        var resultBody = List.of(
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), String[].class)
        );
        var expectedBody = List.of(
                admin.getUsername(),
                user.getUsername(),
                joaodss.getUsername()
        );
        assertTrue(resultBody.containsAll(expectedBody));
    }

    @Test
    @Order(5)
    void testGetAllUsersIdByPartyLevelBetween_minPartyLevelGreaterThanMax_throwError() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/partyLevel")
                                .param("min", "13")
                                .param("max", "0")
                )
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("Min party level must be less than max party level", responseError);
    }

    @Test
    @Order(5)
    void testGetAllUsersIdByPartyLevelBetween_partyLevelNegative_throwError() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/partyLevel")
                                .param("min", "-5")
                                .param("max", "14")
                )
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("Party level must be positive", responseError);
    }

    // -------------------- POST Routes --------------------
    // ---------- Create User ----------
    @Test
    @Order(6)
    void testCreateUser_userCreated_userReturned() throws Exception {
        var newUserBody = objectMapper.writeValueAsString(newUserAlbert);
        var mvcResult = mockMvc.perform(
                        post(baseUrl + "/register")
                                .content(newUserBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(newUserAlbert.getUsername())))
                .andExpect(jsonPath("$.email", is(newUserAlbert.getEmail())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        var expectedBody = new UserDTO(notStoredAlbert);
        assertEquals(expectedBody, resultBody);
    }

    // -------------------- PUT Routes --------------------
    // ---------- Update User ----------
    @Test
    @Order(7)
    void testUpdateUser_userUpdated_userReturned() throws Exception {
        var updatedUserBody = objectMapper.writeValueAsString(updatedJoaodss);
        var mvcResult = mockMvc.perform(
                        put(baseUrl + "/update/" + joaodss.getUsername())
                                .content(updatedUserBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(updatedJoaodss.getUsername())))
                .andExpect(jsonPath("$.email", is(updatedJoaodss.getEmail())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        var expectedBody = updatedJoaodss;
        var storedObject = new UserDTO(userRepository.findById(joaodss.getId()).orElseThrow());
        expectedBody.setGold(joaodss.getGold());
        assertEquals(expectedBody, resultBody);
        assertEquals(expectedBody, storedObject);
    }

    @Test
    @Order(7)
    void testUpdateUser_userNotFound_throwError() throws Exception {
        var updatedUserBody = objectMapper.writeValueAsString(updatedJoaodss);
        var mvcResult = mockMvc.perform(
                        put(baseUrl + "/update/notFound")
                                .content(updatedUserBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("User not found", responseError);
    }

    // ---------- Change Password ----------
    @Test
    @Order(8)
    void testChangePassword_passwordChanged_passwordReturned() throws Exception {
        var changePasswordBody = objectMapper.writeValueAsString(new NewPasswordDTO("pass1234"));
        mockMvc.perform(
                        put(baseUrl + "/update/" + user.getUsername() + "/password")
                                .content(changePasswordBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        var expectedBody = user;
        expectedBody.setPassword("pass1234");
        var storedObject = userRepository.findById(user.getId()).orElse(null);
        assertEquals(expectedBody, storedObject);
    }

    @Test
    @Order(8)
    void testChangePassword_userNotFound_throwError() throws Exception {
        var changePasswordBody = objectMapper.writeValueAsString(new NewPasswordDTO("pass1234"));
        var mvcResult = mockMvc.perform(
                        put(baseUrl + "/update/notFound/password")
                                .content(changePasswordBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("User not found", responseError);
    }

    // -------------------- DELETE Routes --------------------
    // ---------- Delete User ----------
    @Test
    @Order(9)
    void testDeleteUser_userDeleted_userReturned() throws Exception {
        mockMvc.perform(
                        delete(baseUrl + "/delete/" + joaodss.getUsername())
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(userRepository.findByUsername(joaodss.getUsername()).isEmpty());
    }

    @Test
    @Order(9)
    void testDeleteUser_userNotFound_throwError() throws Exception {
        var mvcResult = mockMvc.perform(
                        delete(baseUrl + "/delete/notFound")
                )
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("User not found", responseError);
    }

}
