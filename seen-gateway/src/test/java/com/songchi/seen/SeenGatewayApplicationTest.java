package com.songchi.seen;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class SeenGatewayApplicationTest {

    @org.junit.jupiter.api.Test
    void main() {
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            Mockito.when(SpringApplication.run(Mockito.any(Class.class), Mockito.any())).thenReturn(null);
            SeenGatewayApplication.main(new String[]{});
        }
    }
}