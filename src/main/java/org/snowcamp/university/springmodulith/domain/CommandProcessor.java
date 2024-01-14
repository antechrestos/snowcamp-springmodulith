package org.snowcamp.university.springmodulith.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommandProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandProcessor.class);


    private final CommandRepository commandRepository;

    public CommandProcessor(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Transactional
    public void process(Command command) {
        LOGGER.debug("Processing command {}", command);
        commandRepository.save(command);
        LOGGER.debug("Command processed {}", command);
    }
}
