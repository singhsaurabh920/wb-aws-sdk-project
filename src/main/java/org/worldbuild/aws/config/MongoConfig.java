package org.worldbuild.aws.config;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.SpringDataMongoDB;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

@Data
@Slf4j
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "org.company.gps.core.domain.repo")
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final String UTF_8 = "UTF-8";
    private static final String MONGODB_PREFIX = "mongodb://";
    private static final String MONGODB_SRV_PREFIX = "mongodb+srv://";
    private static final Set<String> ALLOWED_OPTIONS_IN_TXT_RECORD = new HashSet<String>(asList("authsource", "replicaset"));
    private final MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Bean
    public MongoClient mongoClient() {
        log.info("Primary MongoClient initialized");
        log.info("Mongo Host {} ", mongoProperties.getHost());
        log.info("Mongo Port {} ", mongoProperties.getPort());
        log.info("Mongo Username {} ", mongoProperties.getUsername());
        log.info("Mongo Password {} ", mongoProperties.getPassword());
        log.info("Mongo Database {} ", mongoProperties.getDatabase());
        log.info("Mongo Authentication Database {} ", mongoProperties.getAuthenticationDatabase());
        ConnectionString connectionString = new ConnectionString(MONGODB_PREFIX + mongoProperties.getHost() + ":" + mongoProperties.getPort());
        log.info("Mongo Uri {}", connectionString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(MongoCredential.createCredential(mongoProperties.getUsername(),
                        mongoProperties.getDatabase(),
                        mongoProperties.getPassword()))
                .build();
        return MongoClients.create(mongoClientSettings, SpringDataMongoDB.driverInformation());
    }


    @Bean
    @Primary
    @Override
    public MongoDatabaseFactory mongoDbFactory() {
        log.info("Primary MongoDatabaseFactory initialized");
        return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
    }

    @Bean
    @Primary
    @Override
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        log.info("Primary MongoTemplate initialized");
        return new MongoTemplate(databaseFactory, converter);
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
