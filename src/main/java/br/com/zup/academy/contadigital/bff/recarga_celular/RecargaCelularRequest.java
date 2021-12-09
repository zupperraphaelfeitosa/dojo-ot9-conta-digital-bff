package br.com.zup.academy.contadigital.bff.recarga_celular;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class RecargaCelularRequest {
    @NotBlank
    private String numeroCelular;

    @NotNull
    private Operadora operadora;

    @Positive
    @NotNull
    private BigDecimal valor;

    public RecargaCelularRequest(String numeroCelular, Operadora operadora, BigDecimal valor) {
        this.numeroCelular = numeroCelular;
        this.operadora = operadora;
        this.valor = valor;
    }
}
