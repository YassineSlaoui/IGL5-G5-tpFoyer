package tn.esprit.tpfoyer17.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.tpfoyer17.entities.*;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChambreRepositoryTest {

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private UniversiteRepository universiteRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void findByBlocFoyerUniversiteNomUniversite() {
        List<Chambre> chambres = chambreRepository.findByBlocFoyerUniversiteNomUniversite("University1");
        assertNotNull(chambres);
    }

    @Test
    void findByBlocIdBlocAndTypeChambre() {
        List<Chambre> chambres = chambreRepository.findByBlocIdBlocAndTypeChambre(1L, TypeChambre.SIMPLE);
        assertNotNull(chambres);
    }

    @Test
    void findByBlocIdBlocAndTypeChambreJPQL() {
        List<Chambre> chambres = chambreRepository.findByBlocIdBlocAndTypeChambreJPQL(1L, TypeChambre.SIMPLE);
        assertNotNull(chambres);
    }

    @Test
    void getChambresNonReserveParNomUniversiteEtTypeChambre() {
        List<Chambre> chambres = chambreRepository.getChambresNonReserveParNomUniversiteEtTypeChambre("University1", TypeChambre.SIMPLE);
        assertNotNull(chambres);
    }

    @Test
    void getChambresNonReserve() {
        List<Chambre> chambres = chambreRepository.getChambresNonReserve();
        assertNotNull(chambres);
    }
}