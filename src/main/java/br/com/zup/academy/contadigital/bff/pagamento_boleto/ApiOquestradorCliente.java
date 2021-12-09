package br.com.zup.academy.contadigital.bff.pagamento_boleto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "orquestrador", url = "${api.orquestrador.url}")
public interface ApiOquestradorCliente {

    @PostMapping("/pagamento-boleto/{idUsuario}")
    ResponseEntity<PagamentoBoletoResponse> solicitaPagamentoBoleto(
            @PathVariable Long idUsuario,
            @RequestBody @Valid PagamentoBoletoRequest request);
}
