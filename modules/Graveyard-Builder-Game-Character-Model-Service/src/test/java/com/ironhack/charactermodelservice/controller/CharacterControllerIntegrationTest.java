package com.ironhack.charactermodelservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.charactermodelservice.dao.Character;
import com.ironhack.charactermodelservice.dto.CharacterDTO;
import com.ironhack.charactermodelservice.dto.NewCharacterDTO;
import com.ironhack.charactermodelservice.repository.CharacterRepository;
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
import java.time.Instant;
import java.util.List;

import static com.ironhack.charactermodelservice.database_utils.DbResetUtil.resetAutoIncrementColumns;
import static com.ironhack.charactermodelservice.enums.Type.*;
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
class CharacterControllerIntegrationTest {
    private final ApplicationContext applicationContext;
    private final WebApplicationContext webApplicationContext;
    private final CharacterRepository characterRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl = "/api/v1/characters";
    private MockMvc mockMvc;
    private Character character1;
    private Character character2;
    private Character character3;
    private Character character4;
    private NewCharacterDTO newCharacter;
    private Character notStoredNewCharacter;
    private CharacterDTO newUpdatedCharacter;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
        character2.setId(2L);
        character2.setIsAlive(false);
        character3 = new Character(
                "joaodss",
                MAGE,
                "MageGuy",
                ""
        );
        character3.setId(3L);
        character4 = new Character(
                "joaodss",
                WARRIOR,
                "SwordGuy",
                ""
        );
        character4.setId(4L);
        characterRepository.saveAll(List.of(character1, character2, character3, character4));

        newCharacter = new NewCharacterDTO(
                "joaodss",
                "warrior",
                "SwordGuy",
                ""
        );
        notStoredNewCharacter = new Character(newCharacter);
        notStoredNewCharacter.setId(5L);

        newUpdatedCharacter = new CharacterDTO(
                1L,
                null,
                null,
                false,
                Instant.now().toString(),
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @AfterEach
    void tearDown() throws SQLException {
        characterRepository.deleteAll();
        resetAutoIncrementColumns(applicationContext, "characters");
    }


    // -------------------- GET Routes --------------------
    // ---------- Get All Users ----------
    @Test
    @Order(1)
    void testGetAllCharacters_valuesOnDatabase_ListWithValues() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/all")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("ArcherGuy", "MageGirl", "MageGuy", "SwordGuy")))
                .andReturn();
        var resultBody = List.of(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterDTO[].class));
        var expectedBody = List.of(
                new CharacterDTO(character1),
                new CharacterDTO(character2),
                new CharacterDTO(character3),
                new CharacterDTO(character4)
        );
        assertTrue(resultBody.containsAll(expectedBody));
    }

    @Test
    @Order(1)
    void testGetAllCharacters_emptyDatabase_emptyList() throws Exception {
        characterRepository.deleteAll();
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/all")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
        var resultBody = List.of(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterDTO[].class));
        assertTrue(resultBody.isEmpty());
    }


    // ---------- Get Character By Id ----------
    @Test
    @Order(2)
    void testGetCharacterById_characterExists_returned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/id/{id}", character1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(character1.getName())))
                .andExpect(jsonPath("$.type", is(character1.getType().name())))
                .andExpect(jsonPath("$.userUsername", is(character1.getUserUsername())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterDTO.class);
        assertEquals(new CharacterDTO(character1), resultBody);
    }

    @Test
    @Order(2)
    void testGetCharacterById_characterDoesNotExists_notFound() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/id/{id}", -1L)
                )
                .andExpect(status().isNotFound())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("Character not found", responseError);
    }


    // ---------- Get Party By User Username ----------
    @Test
    @Order(3)
    void testGetPartyByUserUsername_partyExists_returned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/party/{userUsername}", "joaodss")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("MageGuy", "SwordGuy")))
                .andReturn();
        var resultBody = List.of(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterDTO[].class));
        var expectedBody = List.of(new CharacterDTO(character3), new CharacterDTO(character4));
        assertTrue(resultBody.containsAll(expectedBody));
    }

    // ---------- Get Graveyard By User Username ----------
    @Test
    @Order(4)
    void testGetGraveyardByUserUsername_graveyardExists_returned() throws Exception {
        var mvcResult = mockMvc.perform(
                        get(baseUrl + "/graveyard/{userUsername}", "joaodss")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("MageGirl")))
                .andReturn();
        var resultBody = List.of(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterDTO[].class));
        var expectedBody = List.of(new CharacterDTO(character2));
        assertTrue(resultBody.containsAll(expectedBody));
    }


    // -------------------- POST Routes --------------------
    // ---------- Create Character ----------
    @Test
    @Order(5)
    void testCreateCharacter_characterCreated_characterReturned() throws Exception {
        var newCharacterBody = objectMapper.writeValueAsString(newCharacter);
        var mvcResult = mockMvc.perform(
                        post(baseUrl + "/create")
                                .content(newCharacterBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(newCharacter.getName())))
                .andExpect(jsonPath("$.type", is(newCharacter.getType().toUpperCase())))
                .andExpect(jsonPath("$.userUsername", is(newCharacter.getUserUsername())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterDTO.class);
        var expectedBody = new CharacterDTO(notStoredNewCharacter);
        assertEquals(expectedBody, resultBody);
    }


    // -------------------- PUT Routes --------------------
    // ---------- Update Character ----------
    // (To Dead: isAlive = false, new deathInstant, currentHealth = 0)
    @Test
    @Order(6)
    void testUpdateCharacter_characterExists_characterUpdatedAndReturned() throws Exception {
        var updatedCharacterBody = objectMapper.writeValueAsString(newUpdatedCharacter);
        var mvcResult = mockMvc.perform(
                        put(baseUrl + "/update")
                                .content(updatedCharacterBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(character1.getName())))
                .andExpect(jsonPath("$.type", is(character1.getType().name())))
                .andExpect(jsonPath("$.userUsername", is(character1.getUserUsername())))
                .andExpect(jsonPath("$.isAlive", is(newUpdatedCharacter.getIsAlive())))
                .andReturn();
        var resultBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterDTO.class);
        var expectedBody = new CharacterDTO(character1);
        expectedBody.setIsAlive(newUpdatedCharacter.getIsAlive());
        expectedBody.setDeathTime(newUpdatedCharacter.getDeathTime());
        expectedBody.setCurrentHealth(newUpdatedCharacter.getCurrentHealth());
        assertEquals(expectedBody, resultBody);
    }

    @Test
    @Order(6)
    void testUpdateCharacter_characterDoesNotExists_characterUpdatedAndReturned() throws Exception {
        var invalidUpdateCharacter = newUpdatedCharacter;
        invalidUpdateCharacter.setId(-1L);

        var updatedCharacterBody = objectMapper.writeValueAsString(invalidUpdateCharacter);
        var mvcResult = mockMvc.perform(
                        put(baseUrl + "/update")
                                .content(updatedCharacterBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("Character not found", responseError);

    }


    // -------------------- DELETE Routes --------------------
    // ---------- Delete Character ----------

    @Test
    @Order(7)
    void testDeleteCharacter_characterExists_characterDeleted() throws Exception {
        mockMvc.perform(
                        delete(baseUrl + "/delete/" + character1.getId())
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(characterRepository.findById(character1.getId()).isEmpty());
    }

    @Test
    @Order(7)
    void testDeleteCharacter_characterDoesNotExists_characterDeleted() throws Exception {
        var mvcResult = mockMvc.perform(
                        delete(baseUrl + "/delete/-1")
                )
                .andExpect(status().isBadRequest())
                .andReturn();
        var responseError = mvcResult.getResponse().getErrorMessage();
        assertEquals("Character not found", responseError);

    }

}