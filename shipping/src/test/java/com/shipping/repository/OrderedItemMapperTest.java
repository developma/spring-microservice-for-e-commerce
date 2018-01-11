package com.shipping.repository;

import com.shipping.config.MyBatisConfig;
import com.shipping.config.TestDataSource;
import com.shipping.domain.OrderedItem;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestDataSource.class, MyBatisConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:drop.sql", "classpath:schema.sql", "classpath:data.sql"})
public class OrderedItemMapperTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderedItemMapper sut;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testInsert_valid() throws Exception {
        assertThat(jdbcTemplate.queryForList("SELECT * FROM ORDEREDITEM").size(), is(1));
        final OrderedItem orderedItem = new OrderedItem(201712150999999L, 1, 5);
        sut.insertOrderedItem(orderedItem);
        assertThat(jdbcTemplate.queryForList("SELECT * FROM ORDEREDITEM").size(), is(2));

        final OrderedItem orderedItem1 = jdbcTemplate.queryForObject("SELECT * FROM ORDEREDITEM WHERE ID = 201712150999999", new BeanPropertyRowMapper<>(OrderedItem.class));
        assertThat(orderedItem1, samePropertyValuesAs(orderedItem));
    }

    @Test
    public void testInsert_invalid() throws Exception {
        expectedException.expect(DuplicateKeyException.class);
        assertThat(jdbcTemplate.queryForList("SELECT * FROM ORDEREDITEM").size(), is(1));
        final OrderedItem orderedItem = new OrderedItem(201712150999999L, 1, 5);
        sut.insertOrderedItem(orderedItem);
        sut.insertOrderedItem(orderedItem);
    }
}
