package tn.esprit.tpfoyer17.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.services.IEtudiantService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtudiantController.class)
class EtudiantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantService;

    @Autowired
    private ObjectMapper objectMapper;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("John Doe");
    }

    @Test
    void addEtudiant() throws Exception {
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/tpFoyer17/api/etudiants/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()));

        verify(etudiantService, times(1)).addEtudiant(any(Etudiant.class));
    }

    @Test
    void gettingAllEtudiant() throws Exception {
        List<Etudiant> etudiants = Arrays.asList(etudiant, new Etudiant());
        when(etudiantService.getAllEtudiants()).thenReturn(etudiants);

        mockMvc.perform(get("/tpFoyer17/api/etudiants/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(etudiants.size()));

        verify(etudiantService, times(1)).getAllEtudiants();
    }

    @Test
    void gettingEtudiant() throws Exception {
        when(etudiantService.getEtudiantById(etudiant.getIdEtudiant())).thenReturn(etudiant);

        mockMvc.perform(get("/tpFoyer17/api/etudiants/get")
                .param("idEtudiant", String.valueOf(etudiant.getIdEtudiant())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()));

        verify(etudiantService, times(1)).getEtudiantById(etudiant.getIdEtudiant());
    }

    @Test
    void deletingEtudiant() throws Exception {
        doNothing().when(etudiantService).deleteEtudiant(etudiant.getIdEtudiant());

        mockMvc.perform(delete("/tpFoyer17/api/etudiants/delete/{idEtudiant}", etudiant.getIdEtudiant()))
                .andExpect(status().isOk());

        verify(etudiantService, times(1)).deleteEtudiant(etudiant.getIdEtudiant());
    }

    @Test
    void updatingEtudiant() throws Exception {
        when(etudiantService.updateEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/tpFoyer17/api/etudiants/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(etudiant.getIdEtudiant()));

        verify(etudiantService, times(1)).updateEtudiant(any(Etudiant.class));
    }
}