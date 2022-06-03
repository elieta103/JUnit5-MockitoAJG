package org.elhg.test.springboot.app;

import org.elhg.test.springboot.app.exceptions.DineroInsuficienteException;
import org.elhg.test.springboot.app.models.Banco;
import org.elhg.test.springboot.app.models.Cuenta;
import org.elhg.test.springboot.app.repositories.BancoRepository;
import org.elhg.test.springboot.app.repositories.CuentaRepository;
import org.elhg.test.springboot.app.services.CuentaService;
import org.elhg.test.springboot.app.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.elhg.test.springboot.app.Datos.*;

@SpringBootTest  // Comentar con OPCION 2 : Con mockito
class SpringbootTestApplicationTests {

	// OPCION 2 : Con mockito,
	/*
	@Mock
	CuentaRepository cuentaRepository;
	@Mock
	BancoRepository bancoRepository;
	@InjectMocks
	CuentaServiceImpl service;  //Que sea del tipo de la implementacion
	*/

    // OPCION 1 : Con SpringBoot  Descomentar @SpringBootTest
	@MockBean
	CuentaRepository cuentaRepository;
	@MockBean
	BancoRepository bancoRepository;
	@Autowired
	CuentaService service;  //Del tipo de la interfaz, agregar @Service


	@BeforeEach
	void setUp() {
		// OPCION 2 : Con mockito
		/*
		cuentaRepository = mock(CuentaRepository.class);
		bancoRepository = mock(BancoRepository.class);
		service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
		*/
	}

	@Test
	void contextLoads() {
		//Given (Dado algún contexto)
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		//Revisar saldos
		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		assertEquals("1000",saldoOrigen.toPlainString());

		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("2000",saldoDestino.toPlainString());

		//Realizar transferencia
		service.transferir(1L, 2L, new BigDecimal("100"), 1L);

		saldoOrigen = service.revisarSaldo(1L);
		assertEquals("900",saldoOrigen.toPlainString());

		saldoDestino = service.revisarSaldo(2L);
		assertEquals("2100",saldoDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(1, total);

		verify(cuentaRepository, times(3)).findById(1L); //revisarSaldo(1L),transferir(1L),revisarSaldo(1L)
		verify(cuentaRepository, times(3)).findById(2L); //revisarSaldo(2L),transferir(2L),revisarSaldo(2L)
		verify(cuentaRepository,times(2)).update(any(Cuenta.class)); //transferir(2 veces), 1 ctaOrigen, 1 ctaDestino
		verify(bancoRepository, times(2)).findById(1L); //transferir(1),revisarTotalTransferencias(1),
		verify(bancoRepository).update(any(Banco.class)); //transferir(1)

		verify(cuentaRepository, times(6)).findById(anyLong()); //3Veces cuenta(1L), 3Veces cuenta(2L)
		verify(cuentaRepository, never()).findAll(); //Nunca se llama
	}

	@Test
	void contextLoadsException() {
		//Given (Dado algún contexto)
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		//Revisar saldos
		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		assertEquals("1000",saldoOrigen.toPlainString());

		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("2000",saldoDestino.toPlainString());

		//Realizar transferencia
		assertThrows(DineroInsuficienteException.class, ()->{
			//La exception se lanza en el debito
			service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
		});

		saldoOrigen = service.revisarSaldo(1L); //Lanzó exception no cambia los saldos de las cuentas
		assertEquals("1000",saldoOrigen.toPlainString());

		saldoDestino = service.revisarSaldo(2L); //Lanzó exception no cambia los saldos de las cuentas
		assertEquals("2000",saldoDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L); //Es cero, por la exception
		assertEquals(0, total);

		verify(cuentaRepository, times(3)).findById(1L); //revisarSaldo(1),transferir(1),revisarSaldo(1)
		verify(cuentaRepository, times(2)).findById(2L); //revisarSaldo(1),transferir(1)
		verify(cuentaRepository, never()).update(any(Cuenta.class)); // No se ejecuta por la exception
		verify(bancoRepository, times(1)).findById(1L); //transferir(1),exception

		verify(bancoRepository, never()).update(any(Banco.class)); //Lanza la exception

		verify(cuentaRepository, never()).findAll(); //Nunca se llama
		verify(cuentaRepository, times(5)).findById(anyLong()); //3 1L, 2 2L
	}

	@Test
	void contextLoad3() {
		//Given (Dado algún contexto)
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

		Cuenta cuenta1 = service.findById(1L);
		Cuenta cuenta2 = service.findById(1L);

		//Validar que sea el mismo objeto
		assertSame(cuenta1, cuenta2);
		assertTrue(cuenta1 == cuenta2);
		assertEquals("Andres", cuenta1.getPersona());
		assertEquals("Andres", cuenta2.getPersona());

		verify(cuentaRepository,times(2)).findById(1L); //para cuenta1 y cuenta2e
	}
}
