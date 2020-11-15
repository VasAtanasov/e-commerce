package org.atanasov.gamestore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("org.atanasov.gamestore")
public class GamestoreCoreConfiguration {
  private static final Logger logger = LoggerFactory.getLogger(GamestoreCoreConfiguration.class);

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @PostConstruct
  public void postConstruct() {
    logger.info("BOOKSHOP CORE MODULE LOADED!");
  }
}
