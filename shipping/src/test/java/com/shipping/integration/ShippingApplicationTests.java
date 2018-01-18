package com.shipping.integration;

import com.shipping.ShippingApplication;
import com.shipping.config.MyBatisConfig;
import com.shipping.config.TestDataSource;
import com.shipping.json.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestDataSource.class, MyBatisConfig.class})
@WebAppConfiguration
@ContextConfiguration(classes = ShippingApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:drop.sql", "classpath:schema.sql", "classpath:data.sql"})
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
                .content(TestData.getTestData().get("ORDER_INFO_VALID").getBytes())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void testOrder_invalidValue_item() throws Exception {
        this.mockMvc.perform(post("/shipping/order/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.getTestData().get("ORDER_INFO_INVALID").getBytes())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorId", is("SHIPPING_SVR_002")))
                .andExpect(jsonPath("$.errorMessage", is("could not find specified item in the inventory service.")))
                .andDo(print());
    }
}
