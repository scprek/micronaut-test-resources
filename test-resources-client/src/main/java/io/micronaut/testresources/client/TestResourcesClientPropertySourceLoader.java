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

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.testresources.core.LazyTestResourcesPropertySourceLoader;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class TestResourcesClientPropertySourceLoader extends LazyTestResourcesPropertySourceLoader {

    public TestResourcesClientPropertySourceLoader() {
        super(new Function<ResourceLoader, List<String>>() {
            private final AtomicBoolean loaded = new AtomicBoolean(false);

            @Override
            public List<String> apply(ResourceLoader resourceLoader) {
                if (loaded.compareAndSet(false, true)) {
                    Optional<URL> config = resourceLoader.getResource("/test-resources.properties");
                    if (config.isPresent()) {
                        return TestResourcesClientFactory.configuredAt(config.get()).listProperties();
                    }
                }
                return Collections.emptyList();
            }

        });
    }
}
