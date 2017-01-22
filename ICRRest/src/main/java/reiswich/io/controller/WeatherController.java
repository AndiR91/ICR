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

package reiswich.io.controller;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reiswich.io.QueueDefinition;
import reiswich.io.data.Weather;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Controller for Spring REST that handles weather requests.
 */
@RestController
public class WeatherController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private CachingConnectionFactory amqpConnection;
    private RabbitTemplate rabbitTemplate;

    public WeatherController() {
        amqpConnection = new CachingConnectionFactory("localhost");
        amqpConnection.setUsername("admin");
        amqpConnection.setPassword("admin");
        rabbitTemplate = new RabbitTemplate(amqpConnection);
    }

    @RequestMapping("/latest")
    public Weather latest() {
        return new Weather(5.2, 5.3);
    }

    @RequestMapping("/test")
    public Weather test() {
        Weather testWeather = new Weather(Math.random(), Math.random());
        rabbitTemplate.convertAndSend(QueueDefinition.DATA_QUEUE, testWeather);
        return testWeather;
    }

}
