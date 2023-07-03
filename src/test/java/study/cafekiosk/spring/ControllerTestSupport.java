package study.cafekiosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import study.cafekiosk.spring.api.controller.order.OrderController;
import study.cafekiosk.spring.api.controller.product.ProductController;
import study.cafekiosk.spring.api.service.order.OrderService;
import study.cafekiosk.spring.api.service.product.ProductService;

@WebMvcTest(controllers = {
        ProductController.class,
        OrderController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected ProductService productService;
}
