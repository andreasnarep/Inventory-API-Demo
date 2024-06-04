package dev.varo.inventory.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "bq-windows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BQWindow {
    @Id
    private ObjectId id;
    private String name;
    private Map<String, Integer> materials;

    public BQWindow(String name, Map<String, Integer> materials) {
        this.name = name;
        this.materials = materials;
    }
}
