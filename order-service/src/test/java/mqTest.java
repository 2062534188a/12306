import com.hhai.order.OrderApplication;
import com.hhai.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(classes = OrderApplication.class)
public class mqTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Test
    void testPublisherDelayMessage() throws InterruptedException {
        // 1.创建消息
        // 2.发送消息，利用消息后置处理器添加消息头
        rabbitTemplate.convertAndSend("delay.direct", "delay","hello, delayed message", message -> {
            message.getMessageProperties().setDelay(5000);
            return message;
        });
        // 等待消息处理
        Thread.sleep(6000); // 等待时间略大于延迟时间
    }
}
