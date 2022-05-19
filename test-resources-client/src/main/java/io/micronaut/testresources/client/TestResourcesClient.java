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

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.testresources.core.TestResourcesResolver;

import java.util.List;
import java.util.Optional;

@Client("${micronaut.testresources.proxy.url}/proxy")
public interface TestResourcesClient extends TestResourcesResolver {
    String PROXY_URI = "proxy.uri";

    @Get("/list")
    List<String> listProperties();

    @Get("/resolve/{name}")
    Optional<String> resolve(String name);
}
