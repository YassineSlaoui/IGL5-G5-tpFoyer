package tn.esprit.tpfoyer17.services;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UniversiteServiceTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private UniversiteService universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);

        assertNotNull(result);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void getAllUniversites() {
        List<Universite> universites = Arrays.asList(new Universite(), new Universite());
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.getAllUniversites();

        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void getUniversiteById() {
        Universite universite = new Universite();
        when(universiteRepository.findById(anyLong())).thenReturn(Optional.of(universite));

        Universite result = universiteService.getUniversiteById(1L);

        assertNotNull(result);
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUniversite() {
        doNothing().when(universiteRepository).deleteById(anyLong());

        universiteService.deleteUniversite(1L);

        verify(universiteRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.updateUniversite(universite);

        assertNotNull(result);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void affecterFoyerAUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.findByNomUniversite(anyString())).thenReturn(universite);
        when(foyerRepository.findById(anyLong())).thenReturn(Optional.of(new Foyer()));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.affecterFoyerAUniversite(1L, "Test University");

        assertNotNull(result);
        verify(universiteRepository, times(1)).findByNomUniversite("Test University");
        verify(foyerRepository, times(1)).findById(1L);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void desaffecterFoyerAUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.findById(anyLong())).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.desaffecterFoyerAUniversite(1L);

        assertNotNull(result);
        verify(universiteRepository, times(1)).findById(1L);
        verify(universiteRepository, times(1)).save(universite);
    }
}