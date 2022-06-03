package org.elhg.test.springboot.app;

import org.elhg.test.springboot.app.exceptions.DineroInsuficienteException;
import org.elhg.test.springboot.app.models.Banco;
import org.elhg.test.springboot.app.models.Cuenta;
import org.elhg.test.springboot.app.repositories.BancoRepository;
import org.elhg.test.springboot.app.repositories.CuentaRepository;
import org.elhg.test.springboot.app.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.elhg.test.springboot.app.Datos.*;

@SpringBootTest // Comentar con OPCION 2 : Con mockito
class ServiceTest {

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

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		assertEquals("1000",saldoOrigen.toPlainString());

		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("2000",saldoDestino.toPlainString());

		service.transferir(1L, 2L, new BigDecimal("100"), 1L);

		saldoOrigen = service.revisarSaldo(1L);
		assertEquals("900",saldoOrigen.toPlainString());

		saldoDestino = service.revisarSaldo(2L);
		assertEquals("2100",saldoDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(1, total);

		verify(cuentaRepository, times(3)).findById(1L); //revisarSaldo(1),transferir(1),revisarSaldo(1)
		verify(cuentaRepository, times(3)).findById(2L);
		verify(cuentaRepository,times(2)).save(any(Cuenta.class)); //transferir(2)
		verify(bancoRepository, times(2)).findById(1L); //transferir(1),revisarTotalTransferencias(1),
		verify(bancoRepository).save(any(Banco.class)); //transferir(1)

		verify(cuentaRepository, times(6)).findById(anyLong()); //3 1L, 3 2L
		verify(cuentaRepository, never()).findAll(); //Nunca se llama
	}

	@Test
	void contextLoadsException() {
		//Given (Dado algún contexto)
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		assertEquals("1000",saldoOrigen.toPlainString());

		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("2000",saldoDestino.toPlainString());

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
		verify(cuentaRepository, never()).save(any(Cuenta.class)); // No se ejecuta por la exception
		verify(bancoRepository, times(1)).findById(1L); //transferir(1),exception

		verify(bancoRepository, never()).save(any(Banco.class)); //Lanza la exception

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

	@Test
	void testFindAll() {
		//Given
		List<Cuenta> datos = Arrays.asList(crearCuenta001().orElseThrow(), crearCuenta002().orElseThrow());
		when(cuentaRepository.findAll()).thenReturn(datos);

		//When
		List<Cuenta> cuentas = service.findAll();

		assertFalse(cuentas.isEmpty());
		assertEquals(2, cuentas.size());
		assertTrue(cuentas.contains(crearCuenta002().orElseThrow()));

		verify(cuentaRepository).findAll();
	}

	@Test
	void testSave() {
		//Given
		Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));

		when(cuentaRepository.save(any())).then(invocation ->{
			Cuenta cta = invocation.getArgument(0);
			cta.setId(3L);
			return cta;
		});

		//When
		Cuenta cuenta = service.save(cuentaPepe);

		assertEquals("Pepe", cuenta.getPersona());
		assertEquals(3L, cuenta.getId());
		assertEquals("3000", cuenta.getSaldo().toPlainString());

		verify(cuentaRepository).save(any());
	}


}
