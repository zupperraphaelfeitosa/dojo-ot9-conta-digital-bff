package br.com.zup.academy.contadigital.bff.compartilhada.api_externa;

import br.com.zup.academy.contadigital.bff.pagamento_boleto.PagamentoBoletoRequest;
import br.com.zup.academy.contadigital.bff.pagamento_boleto.PagamentoBoletoResponse;
import br.com.zup.academy.contadigital.bff.recarga_celular.RecargaCelularRequest;
import br.com.zup.academy.contadigital.bff.recarga_celular.RecargaCelularResponse;
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

    @PostMapping("/recarga-celular/{idUsuario}")
    ResponseEntity<RecargaCelularResponse> solicitaRecargaCelular(
            @PathVariable Long idUsuario,
            @RequestBody @Valid RecargaCelularRequest request);
}
