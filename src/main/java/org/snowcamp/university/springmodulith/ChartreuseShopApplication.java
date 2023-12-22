package org.snowcamp.university.springmodulith;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@SpringBootApplication
public class ChartreuseShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChartreuseShopApplication.class, args);
    }

    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Chartreuse shop")
                                .version("latest-SNAPSHOT")
                );
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory databaseFactory) {
        return new MongoTransactionManager(databaseFactory);
    }
}
