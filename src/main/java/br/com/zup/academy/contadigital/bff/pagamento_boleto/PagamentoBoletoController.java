package br.com.zup.academy.contadigital.bff.pagamento_boleto;

import br.com.zup.academy.contadigital.bff.compartilhada.api_externa.ApiOquestradorCliente;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
public class PagamentoBoletoController {

    @Autowired
    private ApiOquestradorCliente api;

    @PostMapping("/{idCliente/pagamento-boleto}")
    public ResponseEntity<?> realizarOperacao(@PathVariable Long idClinte, @RequestBody @Valid PagamentoBoletoRequest request) {
        try {
            api.solicitaPagamentoBoleto(idClinte, request);
            return ResponseEntity.ok().build();

        } catch (FeignException.BadRequest e) {
            return ResponseEntity.badRequest().body("Dados inválidos");

        } catch (FeignException.UnprocessableEntity e) {
            return ResponseEntity.unprocessableEntity().body("Saldo insuficiente");

        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("Ocorreu uma falha de comunicação com o serviço de pagamento de boleto.", HttpStatus.SERVICE_UNAVAILABLE);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado");
        }
    }
}
