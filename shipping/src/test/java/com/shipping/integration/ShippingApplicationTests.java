package com.shipping.integration;

import com.shipping.json.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShippingApplicationTests {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testOrder_validValue() throws Exception {
        this.mockMvc.perform(post("/shipping/order/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.getTestData().get("orderInfo_valid").getBytes())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void testOrder_invalidValue_item() throws Exception {
        this.mockMvc.perform(post("/shipping/order/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.getTestData().get("orderInfo_valid").getBytes())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorId", is("SHIPPING_SVR_001")))
                .andExpect(jsonPath("$.errorMessage", is("an invalid value was specified for posted json.")))
                .andDo(print());
    }
}
