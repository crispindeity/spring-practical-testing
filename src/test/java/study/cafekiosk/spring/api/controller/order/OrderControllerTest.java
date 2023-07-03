package study.cafekiosk.spring.api.controller.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import study.cafekiosk.spring.ControllerTestSupport;
import study.cafekiosk.spring.api.controller.order.request.OrderGenerateRequest;

import java.util.List;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest extends ControllerTestSupport {

    @DisplayName("새로운 주문을 생성한다.")
    @Test
    void generateOrder() throws Exception {
        //given
        OrderGenerateRequest request = OrderGenerateRequest.constructorForTesting(List.of("001", "002"));

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("새로운 주문 생성 시 상품번호가 없으면 예외가 발생해야 한다.")
    @Test
    void givenEmptyProductNumbersWhenGenerateOrderThenException() throws Exception {
        //given
        OrderGenerateRequest request = OrderGenerateRequest.constructorForTesting(List.of());

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 번호 리스트는 필수입니다."));
    }
}