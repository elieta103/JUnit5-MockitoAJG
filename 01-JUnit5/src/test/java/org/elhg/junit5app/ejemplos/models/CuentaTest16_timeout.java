package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest16_timeout {
    @Nested
    @Tag("timeout")
    class EjemploTimeoutTest{
        @Test
        @Timeout(1)
        void testTimeout() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(100);
        }

        @Test
        @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
        void testTimeout2() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(900);
        }

        @Test
        void testTimeoutAssert() {
            assertTimeout(Duration.ofSeconds(5), ()->{
                TimeUnit.MILLISECONDS.sleep(4500);
            });
        }
    }
}