package dev.varo.inventory;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.PoloDoor;
import dev.varo.inventory.repositories.BQDoorRepository;
import dev.varo.inventory.repositories.PoloDoorRepository;
import dev.varo.inventory.services.BQDoorService;
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
public class BQDoorServiceTest {
    @Mock
    private BQDoorRepository bqDoorRepository;
    @InjectMocks
    private BQDoorService bqDoorService;
    @Test
    public void getBQDoorByName() {
        String name = "1690x1900 1/1";
        Map<String, Integer> materials = new HashMap<>();
        materials.put("605x1537", 2);
        materials.put("asd", 1);

        BQDoor door = new BQDoor(name, materials);

        when(bqDoorRepository.findBQDoorByName(name)).thenReturn(Optional.of(door));

        Optional<BQDoor> result = bqDoorService.bqDoorByName(name);

        verify(bqDoorRepository, times(1)).findBQDoorByName(name);
        assertTrue(result.isPresent());
        assertEquals(door, result.get());
    }

    @Test
    public void getAllBQDoors() {
        String name = "1690x1900 1/1";
        Map<String, Integer> materials = new HashMap<>();
        materials.put("605x1537", 2);

        String name2 = "1588x1965 1/1";
        Map<String, Integer> materials2 = new HashMap<>();
        materials2.put("605x1685", 1);

        BQDoor door = new BQDoor(name, materials);
        BQDoor door2 = new BQDoor(name2, materials2);

        when(bqDoorRepository.findAll()).thenReturn(Arrays.asList(
                door,
                door2
        ));

        List<BQDoor> result = bqDoorService.allBQDoors();

        verify(bqDoorRepository, times(1)).findAll();
        assertEquals(result.get(0), door);
        assertEquals(result.get(1), door2);
    }
}
