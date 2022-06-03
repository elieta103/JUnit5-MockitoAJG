package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest09_Condicionales {
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloLinuxMac() {
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJdk8() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void soloJdk17() {
    }

    @Test
    @DisabledOnJre(JRE.JAVA_16)
    void soloNoJdk16() {
    }

    @Test
    void testImprimirSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "16.0.1")
    void testJavaVersion() {
    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testSolo64Bits() {
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testNo64Bits() {
    }

    @Test
    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    void testDev() {
    }

    @Test
    void testImprimirVariablesAmbiente() {
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-14.0.2.*")
    void testJavaHome() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
    void testProcesadores() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
    void testEnv() {
    }
}