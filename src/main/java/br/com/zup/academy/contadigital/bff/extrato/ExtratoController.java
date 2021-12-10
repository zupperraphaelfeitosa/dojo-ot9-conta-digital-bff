package br.com.zup.academy.contadigital.bff.extrato;

import br.com.zup.academy.contadigital.bff.compartilhada.api_externa.ApiExtratoCliente;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ExtratoController {

    @Autowired
    private ApiExtratoCliente api;

    @GetMapping("/{idCliente}/transacoes")
    public ResponseEntity<?> solicitaExtrato(@PathVariable Long idCliente) {
        try {
            List<ExtratoResponse> extrato = api.solicitaExtrato(idCliente);
            return ResponseEntity.ok().body(extrato);

        } catch (FeignException.ServiceUnavailable exception) {
            return new ResponseEntity<>("Ocorreu uma falha de comunicação com o serviço de extrato", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
