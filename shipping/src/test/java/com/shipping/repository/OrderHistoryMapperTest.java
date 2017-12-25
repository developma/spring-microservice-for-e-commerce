package com.shipping.repository;

import com.shipping.config.MyBatisConfig;
import com.shipping.config.TestDataSource;
import com.shipping.domain.OrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestDataSource.class, MyBatisConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:drop.sql", "classpath:schema.sql", "classpath:data.sql"})
public class OrderHistoryMapperTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderHistoryMapper sut;

    @Test
    public void testInsert() throws Exception {

        final List<String> sqls = Arrays.asList(
                "INSERT INTO ADDR (ID, ZIPCODE, LOCATION, RECEIVERNAME) VALUES (99, '123-4567', 'Tokyo-Fuchu', 'SCOTT');",
                "INSERT INTO ORDEREDITEM (ID, ITEM_ID, ITEM_UNIT) VALUES (99, 1, 5);");

        sqls.forEach(s -> jdbcTemplate.execute(s));

        assertThat(jdbcTemplate.queryForList("SELECT * FROM ORDERHIST").size(), is(1));
        final OrderInfo orderInfo = new OrderInfo(99L, null, "TestName", null);
        sut.insertOrderHistory(orderInfo);
        assertThat(jdbcTemplate.queryForList("SELECT * FROM ORDERHIST").size(), is(2));

    }
}
