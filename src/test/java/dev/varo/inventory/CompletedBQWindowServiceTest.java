package dev.varo.inventory;

import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.CompletedBQWindow;
import dev.varo.inventory.repositories.CompletedBQWindowRepository;
import dev.varo.inventory.services.CompletedBQWindowService;
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
public class CompletedBQWindowServiceTest {
    @Mock
    private CompletedBQWindowRepository completedBQWindowRepository;
    @InjectMocks
    private CompletedBQWindowService completedBQWindowService;

    @Test
    public void addCompletedBQWindows() {
        CompletedBQWindow completedBQWindow = new CompletedBQWindow();
        completedBQWindow.setName("1390x1000");
        completedBQWindow.setQuantity(11);
        completedBQWindow.setMonth("May");


        CompletedBQWindow completedBQWindow2 = new CompletedBQWindow();
        completedBQWindow2.setName("1290x1000");
        completedBQWindow2.setQuantity(12);
        completedBQWindow2.setMonth("July");

        List<CompletedBQWindow> completedBQWindows = new ArrayList<>();
        completedBQWindows.add(completedBQWindow);
        completedBQWindows.add(completedBQWindow2);

        completedBQWindowService.addCompletedBQWindows(completedBQWindows);

        verify(completedBQWindowRepository, times(1)).saveAll(completedBQWindows);
    }
}
