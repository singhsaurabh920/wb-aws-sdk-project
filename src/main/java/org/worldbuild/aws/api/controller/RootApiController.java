package org.worldbuild.aws.api.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Data
@Slf4j
@RestController
@RequestMapping("/public/api/vi")
public class RootApiController {

    private final MongoTemplate mongoTemplate;

    @RequestMapping("/getMongoInfo")
    public Set<String> getServerConfig(){
        return mongoTemplate.getCollectionNames();
    }
}
