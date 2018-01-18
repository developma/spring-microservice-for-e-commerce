package com.shipping.service;

import com.shipping.config.MyBatisConfig;
import com.shipping.config.TestDataSource;
import com.shipping.domain.Address;
import com.shipping.domain.OrderInfo;
import com.shipping.domain.OrderedItem;
import com.shipping.exception.ItemNotFoundException;
import com.shipping.exception.ItemUnitLackingException;
import com.shipping.exception.NetworkException;
import com.shipping.generator.RegisterIdGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        RestConfiguration.class,
        ShippingService.class,
        TestDataSource.class,
        MyBatisConfig.class,
        RegisterIdGenerator.class
        })
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:drop.sql", "classpath:schema.sql", "classpath:data.sql"})
public class ShippingServiceTest {

    @Autowired
    ShippingService sut;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void setUp() throws Exception {
        mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
    }

    @After
    public void tearDown() throws Exception {
        mockRestServiceServer.reset();
    }

    @Test
    public void order_valid() throws Exception {
        mockRestServiceServer.expect(requestTo("http://localhost:8200/inventory/check/1/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess("{\"registerId\":1,\"name\":\"Apple\",\"price\":110,\"unit\":5,\"description\":null,\"pict\":null,\"category\":null,\"versionno\":0}",
                                MediaType.APPLICATION_JSON));
        mockRestServiceServer.expect(requestTo("http://localhost:8200/inventory/update/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("success", MediaType.APPLICATION_JSON));

        final Address address = new Address(201701121606954L, "test", "test", "test");
        final OrderedItem orderedItem = new OrderedItem(201701121606954L, 1, 1, 0L);
        final OrderInfo orderInfo = new OrderInfo(
                201701121606954L,
                Arrays.asList(orderedItem),
                "test",
                address);
        sut.order(orderInfo);

        final Address address1 = jdbcTemplate.queryForObject("SELECT * FROM ADDR WHERE REGISTERID = " + orderInfo.getRegisterId(), new BeanPropertyRowMapper<>(Address.class));
        assertThat(address1, is(samePropertyValuesAs(address)));

        final OrderedItem orderedItem1 = jdbcTemplate.queryForObject("SELECT * FROM ORDEREDITEM WHERE REGISTERID = " + orderInfo.getRegisterId(), new BeanPropertyRowMapper<>(OrderedItem.class));
        assertThat(orderedItem1.getRegisterId(), is(orderInfo.getRegisterId()));
        assertThat(orderedItem1.getId(), is(1));
        assertThat(orderedItem1.getUnit(), is(1));

        assertThat(jdbcTemplate.queryForList("SELECT * FROM ORDERHIST").size(), is(2));
        mockRestServiceServer.verify();
    }

    @Test
    public void order_invalid_wrongItemKey() throws Exception {
        mockRestServiceServer.expect(requestTo("http://localhost:8200/inventory/check/999/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess("{\"errorId\":\"SVR_URI_010\",\"errorMessage\":\"could not find specified item in the inventory service.\",\"solution\":null}",
                                MediaType.APPLICATION_JSON));
        expectedException.expect(ItemNotFoundException.class);
        final OrderInfo orderInfo = new OrderInfo(
                1L,
                Arrays.asList(new OrderedItem(1L, 999, 3, 0L)),
                "test",
                null);
        sut.order(orderInfo);
    }

    @Test
    public void order_invalid_network() throws Exception {
        mockRestServiceServer.expect(requestTo("http://localhost:8200/inventory/check/1/"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        expectedException.expect(NetworkException.class);
        final OrderInfo orderInfo = new OrderInfo(
                1L,
                Arrays.asList(new OrderedItem(1L, 1, 3, 0L)),
                "test",
                null);
        sut.order(orderInfo);
    }

    @Test
    public void order_invalid_lacking_item() throws Exception {
        mockRestServiceServer.expect(requestTo("http://localhost:8200/inventory/check/1/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess("{\"registerId\":1,\"name\":\"Apple\",\"price\":110,\"unit\":5,\"description\":null,\"pict\":null,\"category\":null,\"versionno\":0}",
                                MediaType.APPLICATION_JSON));
        expectedException.expect(ItemUnitLackingException.class);
        final OrderInfo orderInfo = new OrderInfo(
                1L,
                Arrays.asList(new OrderedItem(1L, 1, 6, 0L)),
                "test",
                null);
        sut.order(orderInfo);
    }
}

@Configuration
class RestConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
