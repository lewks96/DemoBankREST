package com.lewiskellett.bank.DemoBankREST;

import com.lewiskellett.bank.DemoBankREST.Repositories.AccountRepository;
import com.lewiskellett.bank.DemoBankREST.Types.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class LoadData {
    private static final Logger logger = LoggerFactory.getLogger(LoadData.class);

    //https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
    private static final DecimalFormat balanceFormat = new DecimalFormat("0.00");

    @Bean
    CommandLineRunner initDatabase(AccountRepository repository) {
        Random random = new Random();

        List<String> firstNames = new ArrayList<>();
        firstNames.add("John");
        firstNames.add("James");
        firstNames.add("Paul");
        firstNames.add("Fred");
        firstNames.add("Steve");
        firstNames.add("Adam");
        firstNames.add("Jessica");
        firstNames.add("Samantha");
        firstNames.add("Christine");
        firstNames.add("Chloe");
        firstNames.add("Charlotte");
        firstNames.add("Alex");

        List<String> lastNames = new ArrayList<>();
        lastNames.add("Smith");
        lastNames.add("Jones");
        lastNames.add("Taylor");
        lastNames.add("Brown");
        lastNames.add("Williams");
        lastNames.add("Wilson");

        return args -> {
            for (int i = 0; i < 100; ++i) {
                double balance = Double.parseDouble(balanceFormat.format(0 + (random.nextDouble() * 5000)));

                Account newAccount = new Account(
                        firstNames.get(random.nextInt(firstNames.size())),
                        lastNames.get(random.nextInt(lastNames.size())),
                        balance);

                if (repository.accountExists(newAccount)) {
                    logger.error("Account ID collision?, " + newAccount);
                    System.exit(-1); // really, really should not happen ever
                }

                logger.info("Loading" + repository.save(newAccount));
            }

        };
    }


}
