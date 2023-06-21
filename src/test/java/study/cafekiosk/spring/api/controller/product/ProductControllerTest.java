package study.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import study.cafekiosk.spring.api.controller.product.dto.ProductGenerateRequest;
import study.cafekiosk.spring.api.service.product.ProductService;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @DisplayName("신규 상품을 등록한다.")
    @Test
    void generateProduct() throws Exception {
        //given
        ProductGenerateRequest request = ProductGenerateRequest.of(ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("신규 상품 등록 시 상품 타입이 없는 경우 예외가 발생해야한다.")
    @Test
    void givenWithoutTypeWhenGenerateProductThenException() throws Exception {
        //given
        ProductGenerateRequest request = ProductGenerateRequest.of(null, ProductSellingStatus.SELLING, "아메리카노", 4000);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 타입은 필수 입니다."));
    }

    @DisplayName("신규 상품 등록 시 상품 이름이 없는 경우 예외가 발생해야한다.")
    @Test
    void givenWithoutNameWhenGenerateProductThenException() throws Exception {
        //given
        ProductGenerateRequest request = ProductGenerateRequest.of(ProductType.HANDMADE, ProductSellingStatus.SELLING, " ", 4000);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 이름은 필수 입니다."));
    }
}