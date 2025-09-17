package io.github.seenings;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class SeenGatewayApplicationTest {

    @org.junit.jupiter.api.Test
    void testMain() {
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            springApplicationMockedStatic.when(()->SpringApplication.run(Mockito.any(Class.class), Mockito.any())).thenReturn(null);
            SeenGatewayApplication.main(new String[]{});
        }
    }
}
