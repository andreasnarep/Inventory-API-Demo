package dev.varo.inventory.repositories;

import dev.varo.inventory.objects.CompletedPoloDoor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompletedPoloDoorRepository extends MongoRepository<CompletedPoloDoor, ObjectId> {
    Optional<List<CompletedPoloDoor>> findCompletedPoloDoorsByMonth(String month);

}
