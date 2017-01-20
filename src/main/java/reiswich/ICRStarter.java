/*
 * Copyright (c) 2013 GoPivotal, Inc. All Rights Reserved
 *
 * Modifications copyright (c) 2016 Andreas Reiswich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reiswich;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reiswich.io.QueueDefinition;
import reiswich.receiver.Receiver;

@SpringBootApplication
public class ICRStarter {


    private static Logger logger =  LoggerFactory.getLogger(ICRStarter.class);

    @Bean
    Queue queue() {
        return new Queue(QueueDefinition.CONTROL_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueDefinition.DATA_QUEUE);
    }

    @Bean
    public ConnectionFactory defaultConnectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory("localhost");
        cf.setUsername("admin");
        cf.setPassword("admin");
        return cf;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueDefinition.CONTROL_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) throws InterruptedException {
        logger.info("This software is licensed under ASLv2 (see LICENSE.txt) and " +
                "uses third party libraries that are distributed under their\n" +
                "own license terms (see LICENSE-3RD-PARTY.txt).");
        SpringApplication.run(ICRStarter.class, args);
    }

}
