/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.testresources.hivemq;

import io.micronaut.testresources.testcontainers.AbstractTestContainersProvider;
import org.testcontainers.hivemq.HiveMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A test resource provider which will spawn a HiveMQ test container.
 */
public class HiveMQTestResourceProvider extends AbstractTestContainersProvider<HiveMQContainer> {

    public static final String MQTT_CLIENT_SERVER_URI = "mqtt.client.server-uri";
    public static final String DEFAULT_IMAGE = "hivemq/hivemq-ce:2021.3";

    @Override
    public List<String> getResolvableProperties(Map<String, Collection<String>> propertyEntries, Map<String, Object> testResourcesConfig) {
        return Collections.singletonList(MQTT_CLIENT_SERVER_URI);
    }

    @Override
    protected String getSimpleName() {
        return "hivemq";
    }

    @Override
    protected String getDefaultImageName() {
        return DEFAULT_IMAGE;
    }

    @Override
    protected HiveMQContainer createContainer(DockerImageName imageName, Map<String, Object> properties) {
        return new HiveMQContainer(imageName);
    }

    @Override
    protected Optional<String> resolveProperty(String propertyName, HiveMQContainer container) {
        return Optional.of("tcp://" + container.getHost() + ":" + container.getMqttPort());
    }

    @Override
    protected boolean shouldAnswer(String propertyName, Map<String, Object> properties) {
        return MQTT_CLIENT_SERVER_URI.equals(propertyName);
    }
}
