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

package reiswich.data.receiver;

import org.springframework.stereotype.Component;
import reiswich.io.data.Weather;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * Receiving messages, that are sent through Spring AMQP.
     * Based on idea: http://stackoverflow.com/questions/18526571/how-to-send-consume-object-in-spring-amqp, Gary Russel
     * @param message message that is sent.
     */
    public void receiveMessage(Weather message) {
        System.out.println("Received message: " + message.getTemperature());
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        //Not in a production system, wahrscheinlich Sicherheit
        return latch;
    }

}
