package com.playground.feature;

import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(
        properties = {
                "spring.cloud.consul.enabled=false",
                "spring.cloud.bus.enabled=false"
        }
)
public @interface PlaygroundIntegrationTest {
}
