package org.snowcamp.university.springmodulith.persistence;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandMongoStore extends MongoRepository<CommandData, String> {
}
