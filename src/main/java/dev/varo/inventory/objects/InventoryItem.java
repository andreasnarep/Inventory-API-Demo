package dev.varo.inventory.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem {
    @Id
    private ObjectId id;
    private int quantity;
    private String name;
    private String slat;
    private int lowerLimit;
}
