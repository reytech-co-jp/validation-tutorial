package com.validationtutorial.task2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 有効なproductNameとcategoryとprice場合は商品を登録できること() throws Exception {
        ProductRequest productRequest = new ProductRequest("iPhone15", "Electronics", 150000);
        ResultActions actual = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(productRequest)));
        actual.andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "message": "successfully created"
                        }
                        """));
    }

    @Test
    public void 商品のproductNameとcategoryとpriceがnullの場合は400エラーとなること() throws Exception {
        ProductRequest productRequest = new ProductRequest(null, null, null);
        ResultActions actual = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(productRequest)));
        actual.andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "status": "BAD_REQUEST",
                            "message": "validation error",
                            "errors": [
                                {
                                    "field": "productName",
                                    "message": "入力してください"
                                },
                                {
                                    "field": "category",
                                    "message": "入力してください"
                                },
                                {
                                    "field": "category",
                                    "message": "無効なカテゴリです"
                                },
                                {
                                    "field": "price",
                                    "message": "入力してください"
                                }
                            ]
                        }
                        """));
    }
}
