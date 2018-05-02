package no.octopod.backend.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTestBase {
    @Autowired
    private ResetService resetService;

    @Before
    public void resetDatabase() {
        resetService.resetDatabase();
    }
}
