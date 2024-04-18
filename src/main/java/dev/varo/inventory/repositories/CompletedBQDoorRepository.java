package dev.varo.inventory.repositories;

import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.InventoryItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompletedBQDoorRepository extends MongoRepository<CompletedBQDoor, ObjectId> {
    Optional<List<CompletedBQDoor>> findCompletedBQDoorsByMonth(String month);

}
