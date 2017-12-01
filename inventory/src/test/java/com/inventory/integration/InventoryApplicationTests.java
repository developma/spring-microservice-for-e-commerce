package com.inventory.integration;

import com.inventory.InventoryApplication;
import com.inventory.config.MyBatisConfig;
import com.inventory.config.TestDataSource;
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
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestDataSource.class, MyBatisConfig.class})
@WebAppConfiguration
@ContextConfiguration(classes = InventoryApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:drop.sql", "classpath:schema.sql", "classpath:data.sql"})
public class InventoryApplicationTests {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * This is a test case for path of /inventory/items/.
     *
     * @throws Exception
     */
    @Test
    public void testItems() throws Exception {
        this.mockMvc.perform(get("/inventory/items/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(9)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Apple")))
                .andExpect(jsonPath("$[0].category.id", is(1)))
                .andExpect(jsonPath("$[0].category.name", is("FOOD")))
                .andExpect(jsonPath("$[7].id", is(8)))
                .andExpect(jsonPath("$[7].name", is("Matrix")))
                .andExpect(jsonPath("$[7].category.id", is(3)))
                .andExpect(jsonPath("$[7].category.name", is("DVD")))
                .andDo(print());
    }

    /**
     * This is a test case for path of /inventory/item/{id}/.
     * @throws Exception
     */
    @Test
    public void testItem_validValue() throws Exception {
        this.mockMvc.perform(get("/inventory/item/5/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.name", is("ES2015 In Action")))
                .andExpect(jsonPath("$.category.id", is(2)))
                .andExpect(jsonPath("$.category.name", is("BOOK")))
                .andDo(print());

    }

    /**
     * This is a test case for path of /inventory/item/9999/.
     * @throws Exception
     */
    @Test
    public void testItem_invalidValue() throws Exception {
        this.mockMvc.perform(get("/inventory/item/9999/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(result -> {
                   assertThat(result.getResponse().getContentLength(), is(0));
                })
                .andDo(print());
    }


    /**
     * This is a test case for path of /inventory/items/category/{id}/.
     *
     * @throws Exception
     */
    @Test
    public void testItemsOfCategory_validValue() throws Exception {
        this.mockMvc.perform(get("/inventory/items/category/2/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].name", is("Java In Action")))
                .andExpect(jsonPath("$[0].category.id", is(2)))
                .andExpect(jsonPath("$[0].category.name", is("BOOK")))
                .andExpect(jsonPath("$[1].id", is(5)))
                .andExpect(jsonPath("$[1].name", is("ES2015 In Action")))
                .andExpect(jsonPath("$[1].category.id", is(2)))
                .andExpect(jsonPath("$[1].category.name", is("BOOK")))
                .andDo(print());
    }

    /**
     * This is a test case for path of /inventory/items/category/9999/.
     *
     * @throws Exception
     */
    @Test
    public void testItemsOfCategory_invalidValue() throws Exception {
        this.mockMvc.perform(get("/inventory/items/category/9999/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());
    }

    /**
     * This is a test case for path of /inventory/item/aaa/.
     * @throws Exception
     */
    @Test
    public void testItem_invalidValue_notNumber() throws Exception {
        this.mockMvc.perform(get("/inventory/item/aaa/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.errorId", is("SVR_URI_001")))
                .andExpect(jsonPath("$.errorMessage", is("an invalid parameter was specified for path of URI.")))
                .andDo(print());
    }

    // TODO: I'll investigate problem of this test case someday.
//    /**
//     * This is a test case for path of /inventory/iteeeee.
//     * @throws Exception
//     */
//    @Test
//    public void testInvalidPath() throws Exception {
//        this.mockMvc.perform(get("/inventory/iteeeee")
//                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isNotFound())
//                .andDo(print())
//                // FIXME: why?????
////                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.errorId", is("SVR_URI_002")))
//                .andExpect(jsonPath("$.errorMessage", is("an invalid path was specified for path of URI.")))
//                .andDo(print());
//    }
}
