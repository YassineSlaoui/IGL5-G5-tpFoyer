package tn.esprit.tpfoyer17.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversiteController.class)
class UniversiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Universite universite;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Test University");
    }

    @Test
    void addingUniversite() throws Exception {
        when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/tpFoyer17/api/universites/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()));

        verify(universiteService, times(1)).addUniversite(any(Universite.class));
    }

    @Test
    void gettingAllUniversite() throws Exception {
        List<Universite> universites = Arrays.asList(universite, new Universite());
        when(universiteService.getAllUniversites()).thenReturn(universites);

        mockMvc.perform(get("/tpFoyer17/api/universites/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(universites.size()));

        verify(universiteService, times(1)).getAllUniversites();
    }

    @Test
    void gettingUniversite() throws Exception {
        when(universiteService.getUniversiteById(universite.getIdUniversite())).thenReturn(universite);

        mockMvc.perform(get("/tpFoyer17/api/universites/get")
                .param("idUniversite", String.valueOf(universite.getIdUniversite())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()));

        verify(universiteService, times(1)).getUniversiteById(universite.getIdUniversite());
    }

    @Test
    void deletingUniversite() throws Exception {
        doNothing().when(universiteService).deleteUniversite(universite.getIdUniversite());

        mockMvc.perform(delete("/tpFoyer17/api/universites/delete/{idUniversite}", universite.getIdUniversite()))
                .andExpect(status().isOk());

        verify(universiteService, times(1)).deleteUniversite(universite.getIdUniversite());
    }

    @Test
    void updatingUniversite() throws Exception {
        when(universiteService.updateUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(put("/tpFoyer17/api/universites/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(universite.getIdUniversite()));

        verify(universiteService, times(1)).updateUniversite(any(Universite.class));
    }

    @Test
    void affecterFoyerAUniversite() throws Exception {
        doNothing().when(universiteService).affecterFoyerAUniversite(anyLong(), String.valueOf(anyLong()));

        mockMvc.perform(post("/tpFoyer17/api/universites/affecter-foyer")
                .param("idUniversite", "1")
                .param("idFoyer", "1"))
                .andExpect(status().isOk());

        verify(universiteService, times(1)).affecterFoyerAUniversite(anyLong(), String.valueOf(anyLong()));
    }

    @Test
    void desaffecterFoyerAUniversite() throws Exception {
        doNothing().when(universiteService).desaffecterFoyerAUniversite(anyLong());

        mockMvc.perform(post("/tpFoyer17/api/universites/desaffecter-foyer")
                .param("idUniversite", "1")
                .param("idFoyer", "1"))
                .andExpect(status().isOk());

        verify(universiteService, times(1)).desaffecterFoyerAUniversite(anyLong());
    }
}