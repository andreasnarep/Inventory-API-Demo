package dev.varo.inventory.repositories;

import dev.varo.inventory.objects.BQDoor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BQDoorRepository extends MongoRepository<BQDoor, ObjectId> {

    Optional<BQDoor> findBQDoorByName(String name);
}
