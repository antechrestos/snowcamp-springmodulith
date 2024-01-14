package org.snowcamp.university.springmodulith.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.domain.Command;
import org.snowcamp.university.springmodulith.domain.CommandProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CommandListener {

    public static final String TOPIC = "preparation-workshop-command";

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);

    private final CommandProcessor commandProcessor;

    private final ObjectMapper objectMapper;

    public CommandListener(CommandProcessor commandProcessor, ObjectMapper objectMapper) {
        this.commandProcessor = commandProcessor;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = TOPIC, groupId = "application")
    public void newCommand(String message) {
        LOGGER.debug("command message arrived {}", message);
        try {
            IncomingCommandMessage commandMessage = objectMapper.readValue(message, IncomingCommandMessage.class);
            commandProcessor.process(new Command(commandMessage.id(), commandMessage.name()));
            LOGGER.debug("command message handled {}", message);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to read command message {}", message);
            throw new RuntimeException(e);
        }
    }

}
