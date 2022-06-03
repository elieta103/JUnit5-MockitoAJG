package org.elhg.test.springboot.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elhg.test.springboot.app.models.TransaccionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CuentaControllerWebTestClientTest {

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;  //Object -> JSON

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testTransferir() throws JsonProcessingException {
        //Given
        TransaccionDTO dto = new TransaccionDTO();
        dto.setCuentaDestinoId(1L);
        dto.setCuentaOrigenId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con exito!");
        response.put("transaccion", dto);


        //When
        client.post().uri("http://localhost:8082/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.mensaje").isNotEmpty()
                //.jsonPath("$.mensaje").value(is("Transferencia realizada con exito!"))  //Forma 1
                //.jsonPath("$.mensaje").value(valor->assertEquals("Transferencia realizada con exito!", valor)) //Forma 2
                .jsonPath("$.mensaje").isEqualTo("Transferencia realizada con exito!")
                .jsonPath("$.transaccion.cuentaOrigenId").isEqualTo(dto.getCuentaOrigenId())
                .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
                .json(objectMapper.writeValueAsString(response));


    }
}