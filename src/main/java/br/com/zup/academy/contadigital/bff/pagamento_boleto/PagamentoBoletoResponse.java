package br.com.zup.academy.contadigital.bff.pagamento_boleto;

public class PagamentoBoletoResponse {

    private String resposta;

    public PagamentoBoletoResponse(String resposta) {
        this.resposta = resposta;
    }

    public String getResposta() {
        return resposta;
    }
}
