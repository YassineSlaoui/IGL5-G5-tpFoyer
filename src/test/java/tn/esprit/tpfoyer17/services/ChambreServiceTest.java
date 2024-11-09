package tn.esprit.tpfoyer17.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class ChambreServiceTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreService chambreService;

    @BeforeEach
    void setUp() {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        log.info("AutoCloseable: {}", autoCloseable);
    }

    @Test
    void addChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.addChambre(chambre);

        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void getAllChambres() {
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(chambreRepository.findAll()).thenReturn(chambres);

        List<Chambre> result = chambreService.getAllChambres();

        assertEquals(chambres, result);
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void getChambreById() {
        long id = 1L;
        Chambre chambre = new Chambre();
        when(chambreRepository.findById(id)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.getChambreById(id);

        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).findById(id);
    }

    @Test
    void deleteChambre() {
        long id = 1L;

        chambreService.deleteChambre(id);

        verify(chambreRepository, times(1)).deleteById(id);
    }

    @Test
    void updateChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.updateChambre(chambre);

        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void getChambresParNomUniversite() {
        String nomUniversite = "Test University";
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(chambreRepository.findByBlocFoyerUniversiteNomUniversite(nomUniversite)).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParNomUniversite(nomUniversite);

        assertEquals(chambres, result);
        verify(chambreRepository, times(1)).findByBlocFoyerUniversiteNomUniversite(nomUniversite);
    }

    @Test
    void getChambresParBlocEtTypeKeyWord() {
        long idBloc = 1L;
        TypeChambre typeChambre = TypeChambre.SINGLE;
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(chambreRepository.findByBlocIdBlocAndTypeChambre(idBloc, typeChambre)).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParBlocEtTypeKeyWord(idBloc, typeChambre);

        assertEquals(chambres, result);
        verify(chambreRepository, times(1)).findByBlocIdBlocAndTypeChambre(idBloc, typeChambre);
    }

    @Test
    void getChambresParBlocEtTypeJPQL() {
        long idBloc = 1L;
        TypeChambre typeChambre = TypeChambre.SINGLE;
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(chambreRepository.findByBlocIdBlocAndTypeChambreJPQL(idBloc, typeChambre)).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParBlocEtTypeJPQL(idBloc, typeChambre);

        assertEquals(chambres, result);
        verify(chambreRepository, times(1)).findByBlocIdBlocAndTypeChambreJPQL(idBloc, typeChambre);
    }

    @Test
    void getChambresNonReserveParNomUniversiteEtTypeChambre() {
        String nomUniversite = "Test University";
        TypeChambre typeChambre = TypeChambre.SINGLE;
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(chambreRepository.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre)).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre);

        assertEquals(chambres, result);
        verify(chambreRepository, times(1)).getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre);
    }

    @Test
    void getChambreNonReserver() {
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(chambreRepository.getChambresNonReserve()).thenReturn(chambres);

        chambreService.getChambreNonReserver();

        verify(chambreRepository, times(1)).getChambresNonReserve();
    }
}