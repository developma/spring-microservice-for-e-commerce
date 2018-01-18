package com.shipping.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RegisterIdGenerator.class)
public class RegisterIdGeneratorTest {

    @Autowired
    Generator sut;

    @Test
    public void testGenerate() throws Exception {
        final Long id = sut.generate();
        assertThat(id, notNullValue());
        System.out.println(id);
        assertThat(id.toString().length(), is(15));
    }
}
