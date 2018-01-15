package com.inventory.repository;

import com.inventory.config.MyBatisConfig;
import com.inventory.config.TestDataSource;
import com.inventory.domain.Category;
import com.inventory.domain.Item;
import com.inventory.domain.ReduceInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestDataSource.class, MyBatisConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:drop.sql", "classpath:schema.sql", "classpath:data.sql"})
public class InventoryMapperTest {

    @Autowired
    public InventoryMapper sut;

    @Test
    public void testSelectItemsByCategoryId_nullValue() throws Exception {
        final List<Item> items = sut.selectItemsByCategoryId(null);
        assertThat(items, is(notNullValue()));
        assertThat(items.size(), is(9));

        // FIXME: Please tell me that how to test using samePropertyValueAs(...) for nested(complex) bean.
//        assertThat(items.get(0), samePropertyValuesAs(
//                new Item(1, "Apple", new Category(1, "FOOD"))
//        ));
        Item item = items.get(0);
        assertThat(item.getId(), is(1));
        assertThat(item.getName(), is("Apple"));
        // INSERT INTO ITEM (NAME, PRICE, UNIT, DESCRIPTION, PICT, CATEGORY_ID)
//        VALUES ('Apple', 110, 5, 'Apple is fruits', FILE_READ('classpath:pict/apple.jpeg'), 1);
        assertThat(item.getPrice(), is(110));
        assertThat(item.getUnit(), is(5));
        assertThat(item.getDescription(), is("Apple is fruits"));
        // TODO: Must assert value of pict.
        assertThat(item.getCategory(), samePropertyValuesAs(new Category(1, "FOOD")));

        item = items.get(8);
        assertThat(item.getId(), is(9));
        assertThat(item.getName(), is("STAR WARS"));
        assertThat(item.getCategory(), samePropertyValuesAs(new Category(3, "DVD")));
    }

    @Test
    public void testSelectItemsByCategoryId_validValue() throws Exception {
        final List<Item> items = sut.selectItemsByCategoryId(1);
        assertThat(items, is(notNullValue()));
        assertThat(items.size(), is(3));
        // FIXME: Please tell me that how to test using samePropertyValueAs(...) for nested(complex) bean.
//        assertThat(items.get(0), samePropertyValuesAs(
//                new Item(3, "Grape", new Category(1, "FOOD"))
//        ));
        final Item item = items.get(2);
        assertThat(item.getId(), is(3));
        assertThat(item.getName(), is("Grape"));
        assertThat(item.getCategory(), samePropertyValuesAs(new Category(1, "FOOD")));
    }

    @Test
    public void testSelectItemsByCategoryId_invalidValue() throws Exception {
        final List<Item> items = sut.selectItemsByCategoryId(Integer.MAX_VALUE);
        assertThat(items, is(notNullValue()));
        assertThat(items.size(), is(0));
    }

    @Test
    public void testSelectItemById_validValue() throws Exception {
        final Item item = sut.selectItemById(3);
        assertThat(item, is(notNullValue()));
        assertThat(item.getId(), is(3));
        assertThat(item.getName(), is("Grape"));
        assertThat(item.getCategory(), samePropertyValuesAs(new Category(1, "FOOD")));
    }

    @Test
    public void testSelectItemById_invalidValue() throws Exception {
        final Item item = sut.selectItemById(Integer.MAX_VALUE);
        assertThat(item, is(nullValue()));
    }

    @Test
    public void testUpdate_validValue() throws Exception {
        Item item = sut.selectItemById(1);
        assertThat(item.getUnit(), is(5));
        boolean result = sut.update(new ReduceInfo(1, 3, 0L));
        item = sut.selectItemById(1);
        assertThat(item.getUnit(), is(3));
        assertThat(result, is(true));
        assertThat(item.getVersionno(), is(1L));

        result = sut.update(new ReduceInfo(1, 3, 0L));
        assertThat(result, is(false));
    }

    @Test
    public void testUpdate_invalidValue() throws Exception {
        final Item item = sut.selectItemById(1);
        final boolean result = sut.update(new ReduceInfo(1, 3, item.getVersionno() + 1));
        assertThat(result, is(false));
    }
}
