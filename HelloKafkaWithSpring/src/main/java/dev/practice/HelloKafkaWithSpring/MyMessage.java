package dev.practice.HelloKafkaWithSpring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MyMessage {

    private String name;
    private String message;
}
