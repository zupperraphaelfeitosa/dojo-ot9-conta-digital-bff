package br.com.zup.academy.contadigital.bff.pagamento_boleto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PagamentoBoletoRequest {

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotBlank
    private String numeroBoleto;

    public PagamentoBoletoRequest(BigDecimal valor, String numeroBoleto) {
        this.valor = valor;
        this.numeroBoleto = numeroBoleto;
    }
}
