package com.citizenRegistryExample.citizenRegistryDemo.CitizenControllerTest;

import com.citizenRegistryExample.citizenRegistryDemo.CitizenService.CitizenService;
import com.citizenRegistryExample.citizenRegistryDemo.controller.CitizenController;
import com.citizenRegistryExample.citizenRegistryDemo.model.Citizen;
import com.citizenRegistryExample.citizenRegistryDemo.repository.CitizenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Import(CitizenController.class)
@WebMvcTest(CitizenController.class)
public class CitizenControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitizenService citizenService;

    @MockBean
    private CitizenRepository citizenRepository;

//Create a citizen for endpoints that need one like updatedCitizen and deleteCitizen
    @BeforeEach
        public void setUp() {
        Citizen testCitizen = new Citizen("12345678", "Koulis", "Kouriakos", "15-03-1990", "Male", "111111111", "123 Main Street");
        Mockito.when(citizenRepository.findByCitizenId("12345678"))
                .thenReturn(Optional.of(testCitizen));
        Mockito.when(citizenService.updateCitizen(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);
        }

    @Test
    public void testAddCitizen() throws Exception {
        Citizen citizen = new Citizen("12345678", "John", "Doe", "15-03-1990", "Male", "123456789", "123 Main Street");

        Mockito.when(citizenRepository.save(Mockito.any(Citizen.class))).thenReturn(citizen);

        mockMvc.perform(post("/api/addCitizen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizenId\":\"AT123456\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"dateOfBirth\":\"15-03-1990\",\"gender\":\"Male\",\"taxNumber\":\"TX1234569\",\"address\":\"123 Main Street\"}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateCitizen() throws Exception {
        mockMvc.perform(put("/api/updateCitizen")
                        .param("citizenId", "12345678")
                        .param("taxNumber", "999999999")
                        .param("address", "456 Updated Street")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Perform PUT request
    @Test
    public void testUpdateCitizenNotFound() throws Exception {
        // Mock service behavior for a failed update
        Mockito.when(citizenService.updateCitizen("AT12345678", "TX999999999", "456 Updated Street"))
                .thenReturn(false);

        mockMvc.perform(put("/api/updateCitizen")
                        .param("citizenId", "AT12345678")
                        .param("taxNumber", "TX999999999")
                        .param("address", "456 Updated Street")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Citizen with ID AT12345678 not found."));
    }

    @Test
    public void testSearchCitizen() throws Exception {
        mockMvc.perform(get("/api/searchCitizen?firstName=John"))
                .andExpect(status().isOk());
    }
    //Todo
//    @Test
//    public void testDeleteCitizen() throws Exception {
//        Mockito.doNothing().when(citizenRepository).delete(Mockito.any(Citizen.class));
//        mockMvc.perform(delete("/api/deleteCitizen/12345678"))
//                .andExpect(status().isOk());
//    }
}