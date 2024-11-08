package tn.esprit.tpfoyer17.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.services.IFoyerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoyerController.class)
class FoyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFoyerService foyerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Test Foyer");
    }

    @Test
    void addingFoyer() throws Exception {
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/tpFoyer17/api/foyers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(foyer.getIdFoyer()));

        verify(foyerService, times(1)).addFoyer(any(Foyer.class));
    }

    @Test
    void gettingAllFoyer() throws Exception {
        List<Foyer> foyers = Arrays.asList(foyer, new Foyer());
        when(foyerService.getAllFoyers()).thenReturn(foyers);

        mockMvc.perform(get("/tpFoyer17/api/foyers/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(foyers.size()));

        verify(foyerService, times(1)).getAllFoyers();
    }

    @Test
    void gettingFoyer() throws Exception {
        when(foyerService.getFoyerById(foyer.getIdFoyer())).thenReturn(foyer);

        mockMvc.perform(get("/tpFoyer17/api/foyers/get")
                .param("idFoyer", String.valueOf(foyer.getIdFoyer())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(foyer.getIdFoyer()));

        verify(foyerService, times(1)).getFoyerById(foyer.getIdFoyer());
    }

    @Test
    void deletingFoyer() throws Exception {
        doNothing().when(foyerService).deleteFoyer(foyer.getIdFoyer());

        mockMvc.perform(delete("/tpFoyer17/api/foyers/delete/{idFoyer}", foyer.getIdFoyer()))
                .andExpect(status().isOk());

        verify(foyerService, times(1)).deleteFoyer(foyer.getIdFoyer());
    }

    @Test
    void updatingFoyer() throws Exception {
        when(foyerService.updateFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(put("/tpFoyer17/api/foyers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(foyer.getIdFoyer()));

        verify(foyerService, times(1)).updateFoyer(any(Foyer.class));
    }

    @Test
    void ajouterFoyerEtAffecterAUniversite() throws Exception {
        when(foyerService.ajouterFoyerEtAffecterAUniversite(any(Foyer.class), anyLong())).thenReturn(foyer);

        mockMvc.perform(post("/tpFoyer17/api/foyers/ajouter-et-affecter")
                .param("idUniversite", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(foyer.getIdFoyer()));

        verify(foyerService, times(1)).ajouterFoyerEtAffecterAUniversite(any(Foyer.class), anyLong());
    }
}