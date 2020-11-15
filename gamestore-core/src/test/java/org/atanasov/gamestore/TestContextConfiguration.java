package org.atanasov.gamestore;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.atanasov.gamestore.core"})
@Configuration
public class TestContextConfiguration {}
