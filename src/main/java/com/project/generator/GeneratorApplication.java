package com.project.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class GeneratorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(GeneratorApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n--------------------逆向工程------------------------------\n" +
                "    访问页 : http://localhost:{}/index.html     " +
                "\n--------------------------------------------------------",
                env.getProperty("server.port"));
    }

}
