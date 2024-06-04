package dev.varo.inventory;

import dev.varo.inventory.objects.PoloDoor;
import dev.varo.inventory.repositories.PoloDoorRepository;
import dev.varo.inventory.services.PoloDoorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PoloDoorServiceTest {
    @Mock
    private PoloDoorRepository poloDoorRepository;

    @InjectMocks
    private PoloDoorService poloDoorService;
    @Test
    public void getPoloDoorByName() {
        String name = "1690x1900 1/1";
        Map<String, Integer> materials = new HashMap<>();
        materials.put("605x1537", 2);
        materials.put("asd", 1);

        PoloDoor door = new PoloDoor(name, materials);

        when(poloDoorRepository.findPoloDoorByName(name)).thenReturn(Optional.of(door));

        Optional<PoloDoor> result = poloDoorService.poloDoorByName(name);

        verify(poloDoorRepository, times(1)).findPoloDoorByName(name);
        assertTrue(result.isPresent());
        assertEquals(door, result.get());
    }

    @Test
    public void getAllPoloDoors() {
        String name = "1690x1900 1/1";
        Map<String, Integer> materials = new HashMap<>();
        materials.put("605x1537", 2);

        String name2 = "1690x2050 1/1";
        Map<String, Integer> materials2 = new HashMap<>();
        materials2.put("605x1685", 1);

        PoloDoor door = new PoloDoor(name, materials);
        PoloDoor door2 = new PoloDoor(name2, materials2);

        when(poloDoorRepository.findAll()).thenReturn(Arrays.asList(
                door,
                door2
        ));

        List<PoloDoor> result = poloDoorService.allPoloDoors();

        verify(poloDoorRepository, times(1)).findAll();
        assertEquals(result.get(0), door);
        assertEquals(result.get(1), door2);
    }
}