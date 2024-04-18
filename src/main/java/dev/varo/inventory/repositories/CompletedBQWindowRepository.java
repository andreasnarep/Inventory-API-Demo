package dev.varo.inventory.repositories;

import dev.varo.inventory.objects.CompletedBQWindow;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompletedBQWindowRepository extends MongoRepository<CompletedBQWindow, ObjectId> {
    Optional<List<CompletedBQWindow>> findCompletedBQWindowsByMonth(String month);

}
