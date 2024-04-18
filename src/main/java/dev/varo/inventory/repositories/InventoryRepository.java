package dev.varo.inventory.repositories;

import dev.varo.inventory.objects.InventoryItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryItem, ObjectId> {
    Optional<InventoryItem> findInventoryItemByName(String name);

    @Query("{'name': ?0}")
    void updateQuantityByName(String name, int quantity);
}
