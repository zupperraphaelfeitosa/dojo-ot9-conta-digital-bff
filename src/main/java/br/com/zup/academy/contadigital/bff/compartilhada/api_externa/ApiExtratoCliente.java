package br.com.zup.academy.contadigital.bff.compartilhada.api_externa;

import br.com.zup.academy.contadigital.bff.extrato.ExtratoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "extrato", url = "${api.extrato.url}")
public interface ApiExtratoCliente {

    @GetMapping("/api/v1/transacoes/{idCliente}")
    List<ExtratoResponse> solicitaExtrato(@PathVariable Long idCliente);
}
