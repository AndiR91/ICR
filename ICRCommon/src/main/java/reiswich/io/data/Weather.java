/*
 * Copyright 2014 the original author or authors.
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

package reiswich.io.data;

import java.io.Serializable;

/**
 * Data model for weather information.
 * Based on ideas: https://gist.github.com/garyrussell/c86d22f6e00a526aec37,
 * http://stackoverflow.com/questions/18526571/how-to-send-consume-object-in-spring-amqp,
 * Gary Russell
 */
public class Weather implements Serializable {
    private final double temperature;
    private final double humidity;

    public Weather(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
