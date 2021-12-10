package br.com.zup.academy.contadigital.bff.recarga_celular;

import br.com.zup.academy.contadigital.bff.compartilhada.api_externa.ApiOquestradorCliente;
import com.google.gson.Gson;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
class RecargaCelularControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private ApiOquestradorCliente api;

    private final String URI = "/api/v1/clientes/1/recarga-celular";

    @Test
    void deveRealizarUmaRecargaDeCelularRetorno200() throws Exception {
        RecargaCelularResponse recargaCelularResponse = new RecargaCelularResponse("em processamento");
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.CLARO, new BigDecimal("25.00"));
        Mockito.when(api.solicitaRecargaCelular(1L, recargaCelularRequest)).thenReturn(ResponseEntity.ok(recargaCelularResponse));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularComDadoInvalidoRetorno400() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.CLARO, new BigDecimal("25.00"));
        FeignException.BadRequest mockException = Mockito.mock(FeignException.BadRequest.class);
        Mockito.when(api.solicitaRecargaCelular(Mockito.eq(1L), Mockito.any(RecargaCelularRequest.class)))
                .thenThrow(mockException);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularComNumeroDeCelularEmBrancoRetorno400() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("", Operadora.CLARO, new BigDecimal("25.00"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularSemOperadoraRetorno400() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", null, new BigDecimal("25.00"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-100.00", "0.0"})
    void naoDeveRealizarUmaRecargaDeCelularComValorNegativoOuZeroRetorno400(String valor) throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.VIVO, new BigDecimal(valor));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularComValorNuloRetorno400() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.VIVO, null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularComOperadoraNulaRetorno400() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", null, new BigDecimal("25"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularQuandoClienteNaoEncontradoRetorno404() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.OI, new BigDecimal("25"));
        FeignException.NotFound mockException = Mockito.mock(FeignException.NotFound.class);
        Mockito.when(api.solicitaRecargaCelular(Mockito.eq(1L), Mockito.any(RecargaCelularRequest.class)))
                .thenThrow(mockException);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNotFound());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularComSaldoInsuficiente422() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.OI, new BigDecimal("25"));
        FeignException.UnprocessableEntity mockException = Mockito.mock(FeignException.UnprocessableEntity.class);
        Mockito.when(api.solicitaRecargaCelular(Mockito.eq(1L), Mockito.any(RecargaCelularRequest.class)))
                .thenThrow(mockException);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isUnprocessableEntity());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularQuandoServcoIndisponivel503() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.OI, new BigDecimal("25"));
        FeignException.ServiceUnavailable mockException = Mockito.mock(FeignException.ServiceUnavailable.class);
        Mockito.when(api.solicitaRecargaCelular(Mockito.eq(1L), Mockito.any(RecargaCelularRequest.class)))
                .thenThrow(mockException);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isServiceUnavailable());
    }

    @Test
    void naoDeveRealizarUmaRecargaDeCelularQuandoServcoIndisponivel() throws Exception {
        RecargaCelularRequest recargaCelularRequest = new RecargaCelularRequest("93999999999", Operadora.OI, new BigDecimal("25"));
        FeignException.InternalServerError mockException = Mockito.mock(FeignException.InternalServerError.class);
        Mockito.when(api.solicitaRecargaCelular(Mockito.eq(1L), Mockito.any(RecargaCelularRequest.class)))
                .thenThrow(mockException);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(recargaCelularRequest)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isInternalServerError());
    }
}