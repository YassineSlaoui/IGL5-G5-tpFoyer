package tn.esprit.tpfoyer17.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.services.IReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setIdReservation("1");
    }

    @Test
    void gettingAllReservation() throws Exception {
        List<Reservation> reservations = Arrays.asList(reservation, new Reservation());
        when(reservationService.getAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/tpFoyer17/api/reservations/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(reservations.size()));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void gettingReservation() throws Exception {
        when(reservationService.getReservationById(reservation.getIdReservation())).thenReturn(reservation);

        mockMvc.perform(get("/tpFoyer17/api/reservations/get")
                .param("idReservation", reservation.getIdReservation()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value(reservation.getIdReservation()));

        verify(reservationService, times(1)).getReservationById(reservation.getIdReservation());
    }

    @Test
    void deletingReservation() throws Exception {
        doNothing().when(reservationService).deleteReservation(reservation.getIdReservation());

        mockMvc.perform(delete("/tpFoyer17/api/reservations/delete/{idReservation}", reservation.getIdReservation()))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).deleteReservation(reservation.getIdReservation());
    }

    @Test
    void updatingReservation() throws Exception {
        when(reservationService.updateReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(put("/tpFoyer17/api/reservations/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value(reservation.getIdReservation()));

        verify(reservationService, times(1)).updateReservation(any(Reservation.class));
    }

    @Test
    void ajouterReservation() throws Exception {
        when(reservationService.ajouterReservation(anyLong(), anyLong())).thenReturn(reservation);

        mockMvc.perform(post("/tpFoyer17/api/reservations/ajouter")
                .param("idBloc", "1")
                .param("cinEtudiant", "12345678")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value(reservation.getIdReservation()));

        verify(reservationService, times(1)).ajouterReservation(anyLong(), anyLong());
    }

    @Test
    void annulerReservation() throws Exception {
        when(reservationService.annulerReservation(anyLong())).thenReturn(reservation);

        mockMvc.perform(post("/tpFoyer17/api/reservations/annuler")
                .param("cinEtudiant", "12345678")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value(reservation.getIdReservation()));

        verify(reservationService, times(1)).annulerReservation(anyLong());
    }

    @Test
    void getReservationParAnneeUniversitaireEtNomUniversite() throws Exception {
        List<Reservation> reservations = Arrays.asList(reservation, new Reservation());
        when(reservationService.getReservationParAnneeUniversitaireEtNomUniversite(any(Date.class), anyString())).thenReturn(reservations);

        mockMvc.perform(get("/tpFoyer17/api/reservations/get-par-annee-universitaire-nom-universite")
                .param("anneeUniversitaire", "2023")
                .param("nomUniversite", "Test University"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(reservations.size()));

        verify(reservationService, times(1)).getReservationParAnneeUniversitaireEtNomUniversite(any(Date.class), anyString());
    }
}