package dev.varo.inventory;

import dev.varo.inventory.objects.CompletedPoloDoor;
import dev.varo.inventory.repositories.CompletedPoloDoorRepository;
import dev.varo.inventory.repositories.InventoryRepository;
import dev.varo.inventory.services.CompletedPoloDoorService;
import dev.varo.inventory.services.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompletedPoloDoorServiceTest {
    @Mock
    private CompletedPoloDoorRepository completedPoloDoorRepository;
    @InjectMocks
    private CompletedPoloDoorService completedPoloDoorService;

    @Test
    public void addCompletedPoloDoors() {
        CompletedPoloDoor completedPoloDoor = new CompletedPoloDoor();
        completedPoloDoor.setName("1690x1900 1/1");
        completedPoloDoor.setQuantity(3);
        completedPoloDoor.setMonth("January");


        CompletedPoloDoor completedPoloDoor2 = new CompletedPoloDoor();
        completedPoloDoor2.setName("1690x2050 1/1");
        completedPoloDoor2.setQuantity(5);
        completedPoloDoor2.setMonth("October");

        List<CompletedPoloDoor> completedPoloDoors = new ArrayList<>();
        completedPoloDoors.add(completedPoloDoor);
        completedPoloDoors.add(completedPoloDoor2);

        completedPoloDoorService.addCompletedPoloDoors(completedPoloDoors);

        verify(completedPoloDoorRepository, times(1)).saveAll(completedPoloDoors);
    }
}