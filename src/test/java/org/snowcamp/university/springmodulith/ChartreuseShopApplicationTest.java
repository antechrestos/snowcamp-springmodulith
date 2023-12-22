package org.snowcamp.university.springmodulith;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

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

    @Test
    void generateDocumentation() {
        ApplicationModules modules = ApplicationModules.of(ChartreuseShopApplication.class);
        Documenter.CanvasOptions canvasOptions = Documenter.CanvasOptions.defaults()
                .revealInternals();
        Documenter.DiagramOptions diagramOptions = Documenter.DiagramOptions.defaults()
                .withStyle(Documenter.DiagramOptions.DiagramStyle.C4);
        new Documenter(modules)
                .writeDocumentation(diagramOptions, canvasOptions);
    }
}
