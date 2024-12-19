package com.seen;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class SeenConfigServerApplicationTests {
    @Test
    void main() {
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            Mockito.when(SpringApplication.run(Mockito.any(Class.class), Mockito.any())).thenReturn(null);
            SeenConfigServerApplication.main(new String[]{});
        }
    }

}
