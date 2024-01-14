package org.snowcamp.university.springmodulith.persistence;

import org.snowcamp.university.springmodulith.domain.Command;
import org.snowcamp.university.springmodulith.domain.CommandRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MongoCommandRepository implements CommandRepository {

    private final CommandMongoStore commandMongoStore;

    public MongoCommandRepository(CommandMongoStore commandMongoStore) {
        this.commandMongoStore = commandMongoStore;
    }

    @Override
    public void save(Command command) {
        this.commandMongoStore.save(new CommandData(command.id(), command.message()));
    }
}
