package com.danny.javablogaggregatorspringboot.service.initdb;

import javax.annotation.PostConstruct;

import com.danny.javablogaggregatorspringboot.annotation.DevProfile;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.stereotype.Service;

@DevProfile
@Service
public class HsqldbManagerService {

    @PostConstruct
    public void getDbManager() {
        DatabaseManagerSwing.main(
                new String[]{"--url", "jdbc:hsqldb:mem:test", "--user", "sa", "--password", "", "--noexit"});
    }

}
