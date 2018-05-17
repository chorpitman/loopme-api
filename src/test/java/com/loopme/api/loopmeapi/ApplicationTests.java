package com.loopme.api.loopmeapi;

import com.loopme.api.model.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Test
    public void should_return_content_values() {
        //given
        //when
        ContentType[] values = ContentType.values();
        //then
        assertEquals(3, values.length);
    }
}
