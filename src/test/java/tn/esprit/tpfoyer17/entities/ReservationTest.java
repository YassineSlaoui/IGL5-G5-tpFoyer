package tn.esprit.tpfoyer17.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void getIdReservation() {
        Reservation reservation = new Reservation();
        assertNull(reservation.getIdReservation());
    }

    @Test
    void getAnneeUniversitaire() {
        Reservation reservation = new Reservation();
        Date date = new Date();
        reservation.setAnneeUniversitaire(date);
        assertEquals(date, reservation.getAnneeUniversitaire());
    }

    @Test
    void isEstValide() {
        Reservation reservation = new Reservation();
        reservation.setEstValide(true);
        assertTrue(reservation.isEstValide());
    }

    @Test
    void getEtudiants() {
        Reservation reservation = new Reservation();
        Set<Etudiant> etudiants = new HashSet<>();
        reservation.setEtudiants(etudiants);
        assertEquals(etudiants, reservation.getEtudiants());
    }

    @Test
    void setIdReservation() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("123");
        assertEquals("123", reservation.getIdReservation());
    }

    @Test
    void setAnneeUniversitaire() {
        Reservation reservation = new Reservation();
        Date date = new Date();
        reservation.setAnneeUniversitaire(date);
        assertEquals(date, reservation.getAnneeUniversitaire());
    }

    @Test
    void setEstValide() {
        Reservation reservation = new Reservation();
        reservation.setEstValide(true);
        assertTrue(reservation.isEstValide());
    }

    @Test
    void setEtudiants() {
        Reservation reservation = new Reservation();
        Set<Etudiant> etudiants = new HashSet<>();
        reservation.setEtudiants(etudiants);
        assertEquals(etudiants, reservation.getEtudiants());
    }

    @Test
    void testToString() {
        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
        String expected = "Reservation(idReservation=null, anneeUniversitaire=" + reservation.getAnneeUniversitaire() + ", estValide=true, etudiants=null)";
        assertEquals(expected, reservation.toString());
    }

    @Test
    void builder() {
        Date date = new Date();
        Set<Etudiant> etudiants = new HashSet<>();
        Reservation reservation = Reservation.builder()
                .anneeUniversitaire(date)
                .estValide(true)
                .etudiants(etudiants)
                .build();
        assertEquals(date, reservation.getAnneeUniversitaire());
        assertTrue(reservation.isEstValide());
        assertEquals(etudiants, reservation.getEtudiants());
    }
}