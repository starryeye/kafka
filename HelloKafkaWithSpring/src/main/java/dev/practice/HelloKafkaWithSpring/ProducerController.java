package dev.practice.HelloKafkaWithSpring;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaProduceService kafkaProduceService;

    @PostMapping("/publish")
    public String publish(String message) {

        kafkaProduceService.send(message);

        return String.format("Published a message : %s", message);
    }

    @PostMapping("/publishWithCallback")
    public String publishWithCallback(String message) {

        kafkaProduceService.sendWithCallback(message);

        return String.format("Published a message with callback : %s", message);
    }

    @PostMapping("/publishMyMessage")
    public String publishMyMessage(MyMessage message) {

        kafkaProduceService.sendMyMessage(message);

        return String.format("Published a message JSON : %s", message);
    }
}
