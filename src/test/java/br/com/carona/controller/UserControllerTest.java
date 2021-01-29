package br.com.carona.controller;

import com.carona.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private String urlBase = "http://localhost:8080/user";

    private MockMvc mockMvc;

    @MockBean
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void shouldReturn200WhenCreating() throws Exception {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Rafael");
        requestParams.put("email", "Rafael@teste.com");

        mockMvc.perform(post(urlBase)
                .contentType("application/json")
                .content(requestParams.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400WhenCreatingWithoutParameters() throws Exception {
        bindingResult.addError(new ObjectError("name", "Name is required"));

        mockMvc.perform(post(urlBase, bindingResult)
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}