package tn.esprit.tpfoyer17.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
class FoyerServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private FoyerService foyerService;

    @BeforeEach
    void setUp() {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        log.info("AutoCloseable: {}", autoCloseable);
    }

    @Test
    void addFoyer() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerService.addFoyer(foyer);

        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void getAllFoyers() {
        List<Foyer> foyers = Arrays.asList(new Foyer(), new Foyer());
        when(foyerRepository.findAll()).thenReturn(foyers);

        List<Foyer> result = foyerService.getAllFoyers();

        assertEquals(2, result.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void getFoyerById() {
        Foyer foyer = new Foyer();
        when(foyerRepository.findById(anyLong())).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.getFoyerById(1L);

        assertNotNull(result);
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void deleteFoyer() {
        doNothing().when(foyerRepository).deleteById(anyLong());

        foyerService.deleteFoyer(1L);

        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateFoyer() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerService.updateFoyer(foyer);

        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void ajouterFoyerEtAffecterAUniversite() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(universiteRepository.findById(anyLong())).thenReturn(Optional.of(new Universite()));

        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
        verify(universiteRepository, times(1)).findById(1L);
    }
}