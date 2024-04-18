package dev.varo.inventory.repositories;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.BQWindow;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BQWindowRepository extends MongoRepository<BQWindow, ObjectId> {

    Optional<BQWindow> findBQWindowByName(String name);
}
