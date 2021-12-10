package br.com.zup.academy.contadigital.bff.extrato;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExtratoResponse {

    private String tipoOperacao;
    private String tipoTransacao;
    private BigDecimal valor;
    private LocalDateTime data;

    public ExtratoResponse(String tipoOperacao, String tipoTransacao, BigDecimal valor, LocalDateTime data) {
        this.tipoOperacao = tipoOperacao;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.data = data;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }
}
