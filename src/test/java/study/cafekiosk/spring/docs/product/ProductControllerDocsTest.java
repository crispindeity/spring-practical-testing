package study.cafekiosk.spring.docs.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import study.cafekiosk.spring.api.controller.product.ProductController;
import study.cafekiosk.spring.api.controller.product.dto.ProductGenerateRequest;
import study.cafekiosk.spring.api.service.product.ProductService;
import study.cafekiosk.spring.docs.RestDocsSupport;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = Mockito.mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @DisplayName("신규 상품을 등록하는 API.")
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document(
                        "product-generate",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("type")
                                        .type(JsonFieldType.STRING)
                                        .description("상품의 타입"),
                                PayloadDocumentation.fieldWithPath("sellingStatus")
                                        .type(JsonFieldType.STRING)
                                        .description("상품의 판매상태"),
                                PayloadDocumentation.fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("상품의 이름"),
                                PayloadDocumentation.fieldWithPath("price")
                                        .type(JsonFieldType.NUMBER)
                                        .description("상품의 가격")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("code")
                                        .type(JsonFieldType.NUMBER)
                                        .description("상태 코드"),
                                PayloadDocumentation.fieldWithPath("status")
                                        .type(JsonFieldType.NUMBER)
                                        .description("상태"),
                                PayloadDocumentation.fieldWithPath("message")
                                        .type(JsonFieldType.NUMBER)
                                        .description("메시지"),
                                PayloadDocumentation.fieldWithPath("data")
                                        .type(JsonFieldType.OBJECT)
                                        .description("응답 데이터")
                                )
                ));
    }
}
