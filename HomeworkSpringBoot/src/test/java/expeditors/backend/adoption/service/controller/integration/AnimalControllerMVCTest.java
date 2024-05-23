package expeditors.backend.adoption.service.controller.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AnimalControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetAAnimals() throws Exception {
        MockHttpServletRequestBuilder builder = get("/animalJPA")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testGetAnimalById() throws Exception {
        MockHttpServletRequestBuilder builder = get("/animalJPA/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testGetAnimalNonExistentById() throws Exception {
        MockHttpServletRequestBuilder builder = get("/animalJPA/{id}", 9999)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder).andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateAnimal() throws Exception {
        MockHttpServletRequestBuilder builder = get("/adopterJPA/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder).andExpect(status().isOk());
        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Adopter adopter = mapper.treeToValue(node, Adopter.class);
        adopter.setAnimal(null);
        String jsonStringAdopter = mapper.writeValueAsString(adopter);

        Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").adopter(adopter).build();
        String jsonString = mapper.writeValueAsString(animal);
        jsonString = jsonString.replace("}", STR.",\"adopter\":\{jsonStringAdopter}}");


        actions = mockMvc.perform(post("/animalJPA")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions = actions.andExpect(status().isCreated());

        result = actions.andReturn();
        String locHeader = result.getResponse().getHeader("Location");
        assertNotNull(locHeader);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateAnimal() throws Exception {
        MockHttpServletRequestBuilder builder = get("/adopterJPA/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions actions = mockMvc.perform(builder).andExpect(status().isOk());
        MvcResult result = actions.andReturn();
        String jsonResultAdopter = result.getResponse().getContentAsString();

        builder = get("/animalJPA/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        actions = mockMvc.perform(builder).andExpect(status().isOk());
        result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Animal animal = mapper.treeToValue(node, Animal.class);
        animal.setPetName("Leo");

        String updatedJson = mapper.writeValueAsString(animal);
        updatedJson = updatedJson.replace("}", STR.",\"adopter\":\{jsonResultAdopter}}");

        actions = mockMvc.perform(put("/animalJPA")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson));

        actions = actions.andExpect(status().isOk());
        builder = get("/animalJPA/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        actions = mockMvc.perform(builder).andExpect(status().isOk());
        result = actions.andReturn();

        jsonResult = result.getResponse().getContentAsString();
        node = mapper.readTree(jsonResult);
        animal = mapper.treeToValue(node, Animal.class);

        assertEquals("Leo", animal.getPetName());
    }

    @Test
    public void testUpdateNonExistentAnimal() throws Exception {
        MockHttpServletRequestBuilder builder = get("/animalJPA/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder).andExpect(status().isOk());
        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Animal adopter = mapper.treeToValue(node, Animal.class);
        adopter.setPetName("Leo");
        adopter.setId(9999);

        String updatedJson = mapper.writeValueAsString(adopter);
        actions = mockMvc.perform(put("/animalJPA")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson));

        actions = actions.andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAnimal() throws Exception {
        MockHttpServletRequestBuilder builder = get("/adopterJPA/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder).andExpect(status().isOk());
        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Adopter adopter = mapper.treeToValue(node, Adopter.class);
        String jsonStringAdopter = mapper.writeValueAsString(adopter);

        Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").adopter(adopter).build();
        String jsonString = mapper.writeValueAsString(animal);
        jsonString = jsonString.replace("}", STR.",\"adopter\":\{jsonStringAdopter}}");

        actions = mockMvc.perform(post("/animalJPA")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions = actions.andExpect(status().isCreated());

        result = actions.andReturn();
        String locHeader = result.getResponse().getHeader("Location");
        assertNotNull(locHeader);

        actions = mockMvc.perform(delete(locHeader)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
    }

    @Test
    public void testDeleteNonExistentAnimal() throws Exception {
        ResultActions actions = mockMvc.perform(delete("/animalJPA/{id}", 9999)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());
    }
}
