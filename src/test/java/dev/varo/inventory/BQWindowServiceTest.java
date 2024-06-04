package dev.varo.inventory;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.BQWindow;
import dev.varo.inventory.objects.PoloDoor;
import dev.varo.inventory.repositories.BQDoorRepository;
import dev.varo.inventory.repositories.BQWindowRepository;
import dev.varo.inventory.repositories.PoloDoorRepository;
import dev.varo.inventory.services.BQDoorService;
import dev.varo.inventory.services.BQWindowService;
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
public class BQWindowServiceTest {
    @Mock
    private BQWindowRepository bqWindowRepository;
    @InjectMocks
    private BQWindowService bqWindowService;
    @Test
    public void getBQWindowByName() {
        String name = "1390x1000";
        Map<String, Integer> materials = new HashMap<>();
        materials.put("545x775", 2);
        materials.put("asd", 3);

        BQWindow window = new BQWindow(name, materials);

        when(bqWindowRepository.findBQWindowByName(name)).thenReturn(Optional.of(window));

        Optional<BQWindow> result = bqWindowService.bqWindowByName(name);

        verify(bqWindowRepository, times(1)).findBQWindowByName(name);
        assertTrue(result.isPresent());
        assertEquals(window, result.get());
    }

    @Test
    public void getAllBQWindows() {
        String name = "1390x1000";
        Map<String, Integer> materials = new HashMap<>();
        materials.put("545x775", 2);

        String name2 = "1290x1000";
        Map<String, Integer> materials2 = new HashMap<>();
        materials2.put("495x775", 2);

        BQWindow window = new BQWindow(name, materials);
        BQWindow window2 = new BQWindow(name2, materials2);

        when(bqWindowRepository.findAll()).thenReturn(Arrays.asList(
                window,
                window2
        ));

        List<BQWindow> result = bqWindowService.allBQWindows();

        verify(bqWindowRepository, times(1)).findAll();
        assertEquals(result.get(0), window);
        assertEquals(result.get(1), window2);
    }
}
