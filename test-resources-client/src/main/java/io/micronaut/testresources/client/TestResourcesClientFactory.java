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
package io.micronaut.testresources.client;

import io.micronaut.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Properties;

/**
 * A factory responsible for creating a {@link TestResourcesClient}.
 * Because this client is used in services which are loaded via
 * service loading during application context building, we can't use
 * regular dependency injection, so this factory creates an application
 * context to create the client.
 */
public class TestResourcesClientFactory {
    static TestResourcesClient configuredAt(URL configFile) {
        Properties props = new Properties();
        try (InputStream input = configFile.openStream()) {
            props.load(input);
        } catch (IOException e) {
            throw new TestResourcesException(e);
        }
        ApplicationContext context = ApplicationContext.builder()
            .packages("io.micronaut.testresources.client")
            .properties(Collections.singletonMap("micronaut.testresources.proxy.url", props.getProperty(TestResourcesClient.PROXY_URI)))
            .start();
        return context.getBean(TestResourcesClient.class);
    }
}
