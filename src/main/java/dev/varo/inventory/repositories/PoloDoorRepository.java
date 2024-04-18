package dev.varo.inventory.repositories;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.PoloDoor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PoloDoorRepository extends MongoRepository<PoloDoor, ObjectId> {

    Optional<PoloDoor> findPoloDoorByName(String name);
}
