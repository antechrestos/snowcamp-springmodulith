package org.snowcamp.university.springmodulith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snowcamp.university.springmodulith.persistence.CommandData;
import org.snowcamp.university.springmodulith.persistence.CommandMongoStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DURATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkshopPreparationApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommandMongoStore commandMongoStore;

    @BeforeEach
    void cleanData(){
        commandMongoStore.deleteAll();
    }

    @Test
    void should_persist_command(){
        String commandName = "test";

        this.restTemplate.postForObject("http://localhost:"+port+"/send/command/{name}",
                null, Void.class, Map.of("name", commandName));

        awaitUntilAsserted(Duration.ofSeconds(1), 5, () -> {
            List<CommandData> allCommands = commandMongoStore.findAll();
            assertThat(allCommands).hasSize(1);
            CommandData command = allCommands.get(0);
            assertThat(command.name()).isEqualTo(commandName);
        });
    }

    private void awaitUntilAsserted(Duration duration, int maxAttempts, Runnable verification) {
        int cpt = 0;
        while (cpt++ < maxAttempts){
            try {
                verification.run();
            }catch (AssertionError e){
                if(cpt == maxAttempts){
                    throw e;
                } else {
                    try {
                        Thread.sleep(duration.toMillis());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

}