package tn.esprit.tpfoyer17.entities;

import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChambreTest {

    @Test
    void getIdChambre() {
        Chambre chambre = new Chambre();
        assertEquals(0, chambre.getIdChambre());
    }

    @Test
    void getNumeroChambre() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        assertEquals(101, chambre.getNumeroChambre());
    }

    @Test
    void getTypeChambre() {
        Chambre chambre = new Chambre();
        chambre.setTypeChambre(TypeChambre.SINGLE);
        assertEquals(TypeChambre.SINGLE, chambre.getTypeChambre());
    }

    @Test
    void getBloc() {
        Chambre chambre = new Chambre();
        Bloc bloc = new Bloc();
        chambre.setBloc(bloc);
        assertEquals(bloc, chambre.getBloc());
    }

    @Test
    void getReservations() {
        Chambre chambre = new Chambre();
        Set<Reservation> reservations = new HashSet<>();
        chambre.setReservations(reservations);
        assertEquals(reservations, chambre.getReservations());
    }

    @Test
    void setNumeroChambre() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        assertEquals(101, chambre.getNumeroChambre());
    }

    @Test
    void setTypeChambre() {
        Chambre chambre = new Chambre();
        chambre.setTypeChambre(TypeChambre.SINGLE);
        assertEquals(TypeChambre.SINGLE, chambre.getTypeChambre());
    }

    @Test
    void setBloc() {
        Chambre chambre = new Chambre();
        Bloc bloc = new Bloc();
        chambre.setBloc(bloc);
        assertEquals(bloc, chambre.getBloc());
    }

    @Test
    void setReservations() {
        Chambre chambre = new Chambre();
        Set<Reservation> reservations = new HashSet<>();
        chambre.setReservations(reservations);
        assertEquals(reservations, chambre.getReservations());
    }

    @Test
    void testToString() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeChambre(TypeChambre.SINGLE);
        String expected = "Chambre(idChambre=0, numeroChambre=101, typeChambre=SINGLE)";
        assertEquals(expected, chambre.toString());
    }

    @Test
    void builder() {
        Chambre chambre = Chambre.builder()
                .numeroChambre(101)
                .typeChambre(TypeChambre.SINGLE)
                .build();
        assertEquals(101, chambre.getNumeroChambre());
        assertEquals(TypeChambre.SINGLE, chambre.getTypeChambre());
    }
}