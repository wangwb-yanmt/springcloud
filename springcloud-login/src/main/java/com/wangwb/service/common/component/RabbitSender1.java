//package com.wangwb.service.common.component;
//
//import java.util.Date;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
///**
// * description:消息发送者
// * @author admin
// *
// */
//@Component
//public class RabbitSender1 {
//
//	@Autowired
//	private AmqpTemplate myRabbitTemplate;
//	
//	/**
//	 * description:消息发出，通过队列名直接发送（默认交换机default exchange）
//	 */
//	public void send(String massage) {
//        this.myRabbitTemplate.convertAndSend("队列1",massage);
//    }
//	
//	/**
//	 * description:消息发出，directExchange交换机
//	 */
//	public void directSend(String massage) {
//        this.myRabbitTemplate.convertAndSend("directExchange","direct.a", massage);
//    }
//	
//	/**
//	 * description:消息发出，fanoutExchange交换机
//	 */
//	public void fanoutSend(String massage) {
//        this.myRabbitTemplate.convertAndSend("fanoutExchange","", massage);
//    }
//	
//	
//}
