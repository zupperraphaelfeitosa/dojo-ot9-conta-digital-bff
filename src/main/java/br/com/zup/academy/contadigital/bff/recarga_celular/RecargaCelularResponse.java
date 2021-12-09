package br.com.zup.academy.contadigital.bff.recarga_celular;

public class RecargaCelularResponse {
    private String resposta;

    public RecargaCelularResponse(String resposta) {
        this.resposta = resposta;
    }

    public String getResposta() {
        return resposta;
    }
}
