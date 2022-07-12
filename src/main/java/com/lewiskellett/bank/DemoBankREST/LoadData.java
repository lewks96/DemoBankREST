package com.lewiskellett.bank.DemoBankREST;

import com.lewiskellett.bank.DemoBankREST.Types.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {
    private static final Logger logger = LoggerFactory.getLogger(LoadData.class);

    @Bean
    CommandLineRunner initDatabase(AccountRepository repository) {

        return args -> {
            logger.info("Loading" + repository.save(new Account("Adam", "Smith", 133.55)));
            logger.info("Loading" + repository.save(new Account("Liam", "Jones", 133.55)));
            logger.info("Loading" + repository.save(new Account("Elijah", "Taylor", 133.55)));
            logger.info("Loading" + repository.save(new Account("Olivia", "Brown", 133.55)));
            logger.info("Loading" + repository.save(new Account("Emma", "Williams", 133.55)));
            logger.info("Loading" + repository.save(new Account("Charlotte", "Wilson", 133.55)));
        };
    }
}
