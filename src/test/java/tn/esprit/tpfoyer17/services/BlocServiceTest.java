package tn.esprit.tpfoyer17.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.services.BlocService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private BlocService blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc result = blocService.addBloc(bloc);

        assertEquals(bloc, result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void getAllBlocs() {
        List<Bloc> blocs = Arrays.asList(new Bloc(), new Bloc());
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> result = blocService.getAllBlocs();

        assertEquals(blocs, result);
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void getBlocById() {
        Bloc bloc = new Bloc();
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc result = blocService.getBlocById(1L);

        assertEquals(bloc, result);
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBloc() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.deleteBloc(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc result = blocService.updateBloc(bloc);

        assertEquals(bloc, result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void affecterChambresABloc() {
        Bloc bloc = new Bloc();
        List<Long> chambreIds = Arrays.asList(1L, 2L);
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        when(chambreRepository.findAllById(chambreIds)).thenReturn(chambres);

        Bloc result = blocService.affecterChambresABloc(chambreIds, 1L);

        assertEquals(bloc, result);
        for (Chambre chambre : chambres) {
            assertEquals(bloc, chambre.getBloc());
        }
        verify(blocRepository, times(1)).findById(1L);
        verify(chambreRepository, times(1)).findAllById(chambreIds);
        verify(blocRepository, times(chambres.size())).save(bloc);
    }
}