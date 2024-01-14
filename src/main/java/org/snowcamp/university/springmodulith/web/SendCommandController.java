package org.snowcamp.university.springmodulith.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.messaging.CommandListener;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class SendCommandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendCommandController.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public SendCommandController(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    @PostMapping("/send/command/{name}")
    public void sendCommand(@PathVariable("name") String name) {
        CommandDto commandDto = new CommandDto(UUID.randomUUID().toString(), name);
        try {
            LOGGER.debug("sending command {}", commandDto);
            kafkaTemplate.send(CommandListener.TOPIC, objectMapper.writeValueAsString(
                                    commandDto
                            )
                    )
                    .get();
            LOGGER.debug("command {} sent", commandDto);
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            LOGGER.error("Failed to send command {}", commandDto, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
