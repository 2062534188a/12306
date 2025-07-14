package com.hhai.pay.constants;

public interface MQConstants {
    String DIRECT_EXCHANGE_NAME = "payment.notification.exchange";
    String DIRECT_PAYMENT_QUEUE_NAME = "order.success.queue";
    String DIRECT_PAYMENT_KEY = "payment.success";
}
