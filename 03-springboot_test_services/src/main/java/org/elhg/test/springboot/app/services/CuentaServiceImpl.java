package org.elhg.test.springboot.app.services;

import org.elhg.test.springboot.app.models.Banco;
import org.elhg.test.springboot.app.models.Cuenta;
import org.elhg.test.springboot.app.repositories.BancoRepository;
import org.elhg.test.springboot.app.repositories.CuentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService{

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto,
                            Long bancoId) {

        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto); //Resta al monto, Lanza la exception
        cuentaRepository.update(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino);
        cuentaDestino.credito(monto);//Suma al monto
        cuentaRepository.update(cuentaDestino);

        //Solo si no hay exception
        Banco banco = bancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.update(banco);

    }
}
