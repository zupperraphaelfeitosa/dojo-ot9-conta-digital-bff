package br.com.zup.academy.contadigital.bff.extrato;

import br.com.zup.academy.contadigital.bff.compartilhada.api_externa.ApiExtratoCliente;
import com.google.gson.Gson;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class ExtratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private ApiExtratoCliente api;

    private final String URI = "/api/v1/clientes/1/transacoes";


    @Test
    void deveRetornarUmaListaDeExtratoComRetorno200() throws Exception {
        ExtratoResponse recargaCelular = new ExtratoResponse("RECARGA_CELULAR", "DEBITO", new BigDecimal("25"), LocalDateTime.now());
        ExtratoResponse pagamentoBoleto = new ExtratoResponse("PAGAMENTO_BOLETO", "DEBITO", new BigDecimal("1000"), LocalDateTime.now());

        List<ExtratoResponse> extrato = List.of(recargaCelular, pagamentoBoleto);
        Mockito.when(api.solicitaExtrato(1L)).thenReturn(extrato);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk());
    }

    @Test
    void deveRetornarUmaListaDeExtratoVaziaComRetorno200() throws Exception {

        List<ExtratoResponse> extrato = new ArrayList<>();
        Mockito.when(api.solicitaExtrato(1L)).thenReturn(extrato);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk());
    }

    @Test
    void naoDeveRetornarExtratoQuandoServcoIndisponivel503() throws Exception {
        FeignException.ServiceUnavailable mockException = Mockito.mock(FeignException.ServiceUnavailable.class);
        Mockito.when(api.solicitaExtrato(1L)).thenThrow(mockException);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isServiceUnavailable());
    }
}