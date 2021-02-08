package org.zeorck.sample;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.zeorck.config.RootConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class RestaurantTest {

    @Autowired
    private Restaurant restaurant;

    @Test
    public void 테스트한다() {

        assertNotNull(restaurant);

//        log.info(restaurant);
//        log.info("--------------");
//        log.info(restaurant.getChef());
        System.out.println(restaurant);
        System.out.println("--------------");
        System.out.println(restaurant.getChef());

    }
}