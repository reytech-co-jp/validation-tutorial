package com.validationtutorial.task1;

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
class UserControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 有効なusernameとpasswordとconfirmPassword場合はユーザーを登録できること() throws Exception {
        UserRequest userRequest = new UserRequest("username", "password", "password");
        ResultActions actual = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userRequest)));
        actual.andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "message": "successfully created"
                        }
                        """));
    }

    @Test
    public void ユーザー登録時にusernameとpasswordとconfirmPasswordがnullの場合は400エラーとなること() throws Exception {
        UserRequest userRequest = new UserRequest(null, null, null);
        ResultActions actual = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userRequest)));
        actual.andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "status": "BAD_REQUEST",
                            "message": "validation error",
                            "errors": [
                                {
                                    "field": "username",
                                    "message": "ユーザー名を入力してください"
                                },
                                {
                                    "field": "password",
                                    "message": "パスワードを入力してください"
                                },
                                {
                                    "field": "passwordMatching",
                                    "message": "パスワードと確認用パスワードが一致していません"
                                }
                            ]
                        }
                        """));
    }
}
