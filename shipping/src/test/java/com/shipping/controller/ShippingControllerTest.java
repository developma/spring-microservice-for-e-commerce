package com.shipping.controller;

import com.shipping.json.TestData;
import com.shipping.service.ShippingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ShippingControllerTest {

    @InjectMocks
    ShippingController sut;

    @Mock
    ShippingService shippingService;

    @Before
    public void setUp() throws Exception {
        when(shippingService.order(TestData.orderInfo)).thenReturn("success");
    }

    @Test
    public void order() throws Exception {
        assertThat(sut.order(TestData.orderInfo), is("success"));
    }

}
