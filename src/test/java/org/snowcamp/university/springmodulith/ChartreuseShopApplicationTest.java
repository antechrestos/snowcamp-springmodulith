package org.snowcamp.university.springmodulith;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.core.ApplicationModules;

public class ChartreuseShopApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChartreuseShopApplicationTest.class);

    @Test
    void output_modularity(){
        LOGGER.info(ApplicationModules.of(ChartreuseShopApplication.class).toString());
    }

    @Test
    void check_modularity(){
        ApplicationModules.of(ChartreuseShopApplication.class).verify();
    }
}
