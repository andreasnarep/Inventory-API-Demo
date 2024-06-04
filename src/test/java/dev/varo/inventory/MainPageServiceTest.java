package dev.varo.inventory;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.CompletedBQWindow;
import dev.varo.inventory.objects.CompletedPoloDoor;
import dev.varo.inventory.repositories.CompletedBQDoorRepository;
import dev.varo.inventory.repositories.CompletedBQWindowRepository;
import dev.varo.inventory.repositories.CompletedPoloDoorRepository;
import dev.varo.inventory.repositories.PoloDoorRepository;
import dev.varo.inventory.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MainPageServiceTest {
    @Mock
    private CompletedPoloDoorRepository completedPoloDoorRepository;
    @InjectMocks
    private CompletedPoloDoorService completedPoloDoorService;
    @Mock
    private CompletedBQDoorRepository completedBQDoorRepository;
    @InjectMocks
    private CompletedBQDoorService completedBQDoorService;
    @Mock
    private CompletedBQWindowRepository completedBQWindowRepository;
    @InjectMocks
    private CompletedBQWindowService completedBQWindowService;
    @InjectMocks
    private MainPageService mainPageService;

    @Test
    public void completedBQDoorsByMonth() throws ParseException {
        String name = "1690x1900 1/1";
        String month = "January";
        int quantity = 2;

        String name2 = "1588x1965 1/1";
        String month2 = "January";
        int quantity2 = 2;

        CompletedBQDoor door = new CompletedBQDoor(name, month, quantity);
        CompletedBQDoor door2 = new CompletedBQDoor(name2, month2, quantity2);

        List<CompletedBQDoor> doors = new ArrayList<>();
        doors.add(door);
        doors.add(door2);

        when(completedBQDoorRepository.findCompletedBQDoorsByMonth(month)).thenReturn(Optional.of(doors));

        Optional<List<CompletedBQDoor>> result = mainPageService.completedBQDoorsByMonth(month);

        verify(completedBQDoorRepository, times(1)).findCompletedBQDoorsByMonth(month);
        assertTrue(result.isPresent());
        assertEquals(result.get(), doors);
    }

    @Test
    public void completedPoloDoorsByMonth() throws ParseException {
        String name = "1690x1900 1/1";
        String month = "February";
        int quantity = 2;

        String name2 = "1690x2050 1/1";
        String month2 = "February";
        int quantity2 = 8;

        CompletedPoloDoor door = new CompletedPoloDoor(name, month, quantity);
        CompletedPoloDoor door2 = new CompletedPoloDoor(name2, month2, quantity2);

        List<CompletedPoloDoor> doors = new ArrayList<>();
        doors.add(door);
        doors.add(door2);

        when(completedPoloDoorRepository.findCompletedPoloDoorsByMonth(month)).thenReturn(Optional.of(doors));

        Optional<List<CompletedPoloDoor>> result = mainPageService.completedPoloDoorsByMonth(month);

        verify(completedPoloDoorRepository, times(1)).findCompletedPoloDoorsByMonth(month);
        assertTrue(result.isPresent());
        assertEquals(result.get(), doors);
    }

    @Test
    public void completedBQWindowsByMonth() throws ParseException {
        String name = "1390x1000";
        String month = "December";
        int quantity = 2;

        String name2 = "1290x1000";
        String month2 = "December";
        int quantity2 = 4;

        CompletedBQWindow window = new CompletedBQWindow(name, month, quantity);
        CompletedBQWindow window2 = new CompletedBQWindow(name2, month2, quantity2);

        List<CompletedBQWindow> windows = new ArrayList<>();
        windows.add(window);
        windows.add(window2);

        when(completedBQWindowRepository.findCompletedBQWindowsByMonth(month)).thenReturn(Optional.of(windows));

        Optional<List<CompletedBQWindow>> result = mainPageService.completedBQWindowsByMonth(month);

        verify(completedBQWindowRepository, times(1)).findCompletedBQWindowsByMonth(month);
        assertTrue(result.isPresent());
        assertEquals(result.get(), windows);
    }
}
