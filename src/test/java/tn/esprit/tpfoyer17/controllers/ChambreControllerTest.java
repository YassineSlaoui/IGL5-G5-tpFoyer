package tn.esprit.tpfoyer17.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.services.IChambreService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChambreController.class)
class ChambreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IChambreService chambreService;

    @Autowired
    private ObjectMapper objectMapper;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setTypeChambre(TypeChambre.SINGLE);
    }

    @Test
    void addingChambre() throws Exception {
        when(chambreService.addChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(post("/tpFoyer17/api/chambres/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(chambre.getIdChambre()));

        verify(chambreService, times(1)).addChambre(any(Chambre.class));
    }

    @Test
    void gettingAllChambre() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre, new Chambre());
        when(chambreService.getAllChambres()).thenReturn(chambres);

        mockMvc.perform(get("/tpFoyer17/api/chambres/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(chambres.size()));

        verify(chambreService, times(1)).getAllChambres();
    }

    @Test
    void gettingChambre() throws Exception {
        when(chambreService.getChambreById(chambre.getIdChambre())).thenReturn(chambre);

        mockMvc.perform(get("/tpFoyer17/api/chambres/get")
                .param("idChambre", String.valueOf(chambre.getIdChambre())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(chambre.getIdChambre()));

        verify(chambreService, times(1)).getChambreById(chambre.getIdChambre());
    }

    @Test
    void deletingChambre() throws Exception {
        doNothing().when(chambreService).deleteChambre(chambre.getIdChambre());

        mockMvc.perform(delete("/tpFoyer17/api/chambres/delete/{idChambre}", chambre.getIdChambre()))
                .andExpect(status().isOk());

        verify(chambreService, times(1)).deleteChambre(chambre.getIdChambre());
    }

    @Test
    void updatingChambre() throws Exception {
        when(chambreService.updateChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(put("/tpFoyer17/api/chambres/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(chambre.getIdChambre()));

        verify(chambreService, times(1)).updateChambre(any(Chambre.class));
    }

    @Test
    void getChambresParNomUniversite() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre, new Chambre());
        when(chambreService.getChambresParNomUniversite(anyString())).thenReturn(chambres);

        mockMvc.perform(get("/tpFoyer17/api/chambres/get-par-nom-universite")
                .param("nomUniversite", "Test University"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(chambres.size()));

        verify(chambreService, times(1)).getChambresParNomUniversite(anyString());
    }

    @Test
    void getChambresParBlocEtTypeKeyWord() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre, new Chambre());
        when(chambreService.getChambresParBlocEtTypeKeyWord(anyLong(), any(TypeChambre.class))).thenReturn(chambres);

        mockMvc.perform(get("/tpFoyer17/api/chambres/get-par-bloc-type-keyword")
                .param("idBloc", "1")
                .param("typeC", TypeChambre.SINGLE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(chambres.size()));

        verify(chambreService, times(1)).getChambresParBlocEtTypeKeyWord(anyLong(), any(TypeChambre.class));
    }

    @Test
    void getChambresParBlocEtTypeJPQL() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre, new Chambre());
        when(chambreService.getChambresParBlocEtTypeJPQL(anyLong(), any(TypeChambre.class))).thenReturn(chambres);

        mockMvc.perform(get("/tpFoyer17/api/chambres/get-par-bloc-type-jpql")
                .param("idBloc", "1")
                .param("typeC", TypeChambre.SINGLE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(chambres.size()));

        verify(chambreService, times(1)).getChambresParBlocEtTypeJPQL(anyLong(), any(TypeChambre.class));
    }

    @Test
    void getChambresNonReserveParNomUniversiteEtTypeChambre() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre, new Chambre());
        when(chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(anyString(), any(TypeChambre.class))).thenReturn(chambres);

        mockMvc.perform(get("/tpFoyer17/api/chambres/get-non-reserve-par-nom-universite-typechambre")
                .param("nomUniversite", "Test University")
                .param("type", TypeChambre.SINGLE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(chambres.size()));

        verify(chambreService, times(1)).getChambresNonReserveParNomUniversiteEtTypeChambre(anyString(), any(TypeChambre.class));
    }
}