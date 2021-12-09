package br.com.zup.academy.contadigital.bff.recarga_celular;

import br.com.zup.academy.contadigital.bff.compartilhada.api_externa.ApiOquestradorCliente;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
public class RecargaCelularController {

    @Autowired
    private ApiOquestradorCliente api;

    @PostMapping("/{idCliente}/recarga-celular")
    public ResponseEntity<?> realizarOperacao(@PathVariable Long idCliente,
                                              @RequestBody @Valid RecargaCelularRequest request) {
        try {
            api.solicitaRecargaCelular(idCliente, request);
            return ResponseEntity.ok().build();

        } catch (FeignException.BadRequest e) {
            return ResponseEntity.badRequest().body("Dados inválidos");

        } catch (FeignException.UnprocessableEntity e) {
            return ResponseEntity.unprocessableEntity().body("Saldo insuficiente");

        } catch (FeignException.NotFound e) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);

        } catch (FeignException.ServiceUnavailable e) {
            return new ResponseEntity<>("Ocorreu uma falha de comunicação com o serviço de recarga.", HttpStatus.SERVICE_UNAVAILABLE);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado");
        }
    }
}
