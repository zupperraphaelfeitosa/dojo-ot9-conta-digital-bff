package br.com.zup.academy.contadigital.bff.pagamento_boleto;

import br.com.zup.academy.contadigital.bff.compartilhada.api_externa.ApiOquestradorCliente;
import com.google.gson.Gson;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;


@SpringBootTest
@AutoConfigureMockMvc
class PagamentoBoletoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private ApiOquestradorCliente api;

    @Test
    void deveRealizarUmPagamentoDeBoletoRetorno200() throws Exception {
        PagamentoBoletoResponse pagamentoBoletoResponse = new PagamentoBoletoResponse("em processamento");
        PagamentoBoletoRequest pagamentoBoletoRequest = new PagamentoBoletoRequest(new BigDecimal("100"), "11111");
        Mockito.when(api.solicitaPagamentoBoleto(1L, pagamentoBoletoRequest)).thenReturn(ResponseEntity.ok(pagamentoBoletoResponse));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/clientes/1/pagamento-boleto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(pagamentoBoletoRequest)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk());
    }

}