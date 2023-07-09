package dev.practice.checkout.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CheckoutSubmitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPostSubmitCheckout() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/checkout/submit")
                        .param("memberId", "10001")
                        .param("productId", "20001")
                        .param("amount", "2")
                        .param("shippingAddress", "경기도 성남시"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}