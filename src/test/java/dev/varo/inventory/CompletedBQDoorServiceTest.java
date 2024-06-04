package dev.varo.inventory;

import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.CompletedPoloDoor;
import dev.varo.inventory.repositories.CompletedBQDoorRepository;
import dev.varo.inventory.services.CompletedBQDoorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompletedBQDoorServiceTest {
    @Mock
    private CompletedBQDoorRepository completedBQDoorRepository;
    @InjectMocks
    private CompletedBQDoorService completedBQDoorService;

    @Test
    public void addCompletedBQDoors() {
        CompletedBQDoor completedBQDoor = new CompletedBQDoor();
        completedBQDoor.setName("1690x1900 1/1");
        completedBQDoor.setQuantity(3);
        completedBQDoor.setMonth("January");


        CompletedBQDoor completedBQDoor2 = new CompletedBQDoor();
        completedBQDoor2.setName("1588x1965 1/1");
        completedBQDoor2.setQuantity(15);
        completedBQDoor2.setMonth("October");

        List<CompletedBQDoor> completedBQDoors = new ArrayList<>();
        completedBQDoors.add(completedBQDoor);
        completedBQDoors.add(completedBQDoor2);

        completedBQDoorService.addCompletedBQDoors(completedBQDoors);

        verify(completedBQDoorRepository, times(1)).saveAll(completedBQDoors);
    }
}
