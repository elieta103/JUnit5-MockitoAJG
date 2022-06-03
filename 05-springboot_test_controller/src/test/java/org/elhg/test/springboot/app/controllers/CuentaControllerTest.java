package org.elhg.test.springboot.app.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elhg.test.springboot.app.models.Cuenta;
import org.elhg.test.springboot.app.models.TransaccionDTO;
import org.elhg.test.springboot.app.services.CuentaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.elhg.test.springboot.app.Datos.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired  // Implementacion de Mockito para MVC
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDetalle() throws Exception {
        //Given
        when(cuentaService.findById(1L)).thenReturn(crearCuenta001().orElseThrow());

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persona").value("Andres"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldo").value("1000"));

        verify(cuentaService).findById(1L);  //Que se llame el findById del service
    }

    @Test
    void testTransferir() throws Exception, JsonProcessingException {
        //Given
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        transaccionDTO.setCuentaOrigenId(1L);
        transaccionDTO.setCuentaDestinoId(2L);
        transaccionDTO.setMonto(new BigDecimal("100"));
        transaccionDTO.setBancoId(1L);
        System.out.println(objectMapper.writeValueAsString(transaccionDTO));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con exito!");
        response.put("transaccion", transaccionDTO);
        System.out.println(objectMapper.writeValueAsString(response));

        //When
        mockMvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaccionDTO)))
                //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").value("Transferencia realizada con exito!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transaccion.cuentaOrigenId").value(1L))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));

    }

    @Test
    void testListar() throws Exception {
        //Given
        List<Cuenta> cuentas = Arrays.asList(crearCuenta001().orElseThrow(), crearCuenta002().orElseThrow());
        when(cuentaService.findAll()).thenReturn(cuentas);
        System.out.println(objectMapper.writeValueAsString(cuentas));
        //When
        mockMvc.perform(get("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].persona").value("Andres"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].persona").value("Jhon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].saldo").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].saldo").value("2000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cuentas)));

        verify(cuentaService).findAll();
    }


    @Test
    void testGuardar() throws Exception {
        //Given
        Cuenta cuenta = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        when(cuentaService.save(any())).then(invocation -> {
            Cuenta c = invocation.getArgument(0);
            c.setId(3L);
            return c;
        });

        //When
        mockMvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
                //Then
                .content(objectMapper.writeValueAsString(cuenta)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persona", Matchers.is("Pepe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldo", Matchers.is(3000)));

        verify(cuentaService).save(any());

    }
}