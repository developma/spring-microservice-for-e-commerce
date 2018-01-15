package com.inventory.controller;

import com.inventory.domain.Category;
import com.inventory.domain.Item;
import com.inventory.domain.ReduceInfo;
import com.inventory.exception.ItemNotFoundException;
import com.inventory.service.InventoryService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class InventoryControllerTest {

    @InjectMocks
    InventoryController sut;

    @Mock
    InventoryService inventoryService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        when(inventoryService.reduce(new ReduceInfo(1, 2, 0L))).thenReturn("success");
        when(inventoryService.item(null)).thenReturn(null);
        when(inventoryService.item(1)).thenReturn(new Item(1, "Foo", 10000, 10, "Desc of Foo", null, new Category(1,"Bar"), 0L));
        when(inventoryService.items()).thenReturn(Arrays.asList(new Item(), new Item(), new Item()));
        when(inventoryService.items(2)).thenReturn(Arrays.asList(new Item(), new Item()));
        when(inventoryService.check(1)).thenReturn(new Item(1, "Foo", 10000, 10, null, null, null,  0L));
        when(inventoryService.check(999)).thenThrow(new ItemNotFoundException());
    }

    @Test
    public void testItems() throws Exception {
        assertThat(sut.items().size(), is(3));
    }

    @Test
    public void testItemsOfCategory_validValue() throws Exception {
        assertThat(sut.itemsOfCategory(2).size(), is(2));
    }

    @Test
    public void testItemsOfCategory_invalidValue() throws Exception {
        assertThat(sut.itemsOfCategory(Integer.MAX_VALUE).size(), is(0));
    }

    @Test
    public void testItem_validValue() throws Exception {
        final Item item = sut.item(1);
        assertThat(item.getId(), is(1));
        assertThat(item.getName(), is("Foo"));
        assertThat(item.getCategory(), samePropertyValuesAs(new Category(1, "Bar")));
    }

    @Test
    public void testItem_invalidValue() throws Exception {
        assertThat(sut.item(9), is(nullValue()));
    }

    @Test
    public void testReduce_validValue() throws Exception {
        final Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        params.put("unit", "2");
        params.put("versionno", "0");
        assertThat(sut.reduce(params), is("success"));
    }

    @Test
    public void testCheck_validValue() throws Exception {
        final Item item = sut.check(1);
        assertThat(item.getId(), is(1));
        assertThat(item.getName(), is("Foo"));
        assertThat(item.getCategory(), nullValue());
        assertThat(item.getDescription(), nullValue());
        assertThat(item.getPict(), nullValue());
    }

    @Test
    public void testCheck_invalidValue() throws Exception {
        expectedException.expect(ItemNotFoundException.class);
        sut.check(999);
    }
}
