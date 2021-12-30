package com.octopusthu.dev.configserver;

import java.util.Base64;
import java.util.Objects;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.profiles.active=native",
    "spring.cloud.config.server.native.searchLocations=classpath:/test-repo",
    "spring.security.user.name=test",
    "spring.security.user.password=password"
})
public class SpringCloudConfigServerApplicationTests {

    @Autowired
    WebTestClient webClient;

    @Test
    void getByApplicationAndDefaultProfileThenJsonResponse() {
        webClient
            .get().uri("/foo/default")
            .header(HttpHeaders.AUTHORIZATION, httpBasicAuthenticationHeaderValue())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("propertySources[0].name").value(new StringContains("foo.properties"))
            .jsonPath("propertySources[0].source.foo").isEqualTo("bar");
    }

    @Test
    void getByApplicationAndSpecificProfileThenJsonResponse() {
        webClient
            .get().uri("/foo/dev")
            .header(HttpHeaders.AUTHORIZATION, httpBasicAuthenticationHeaderValue())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("propertySources[0].name").value(new StringContains("foo-dev.properties"))
            .jsonPath("propertySources[0].source.foo").isEqualTo("foo");
    }

    @Test
    void getByPropertyFileNameOfDefaultProfileThenTextResponse() {
        webClient
            .get().uri("/foo-default.properties")
            .header(HttpHeaders.AUTHORIZATION, httpBasicAuthenticationHeaderValue())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
            .expectBody().consumeWith(entityExchangeResult -> {
                String strBody = new String(Objects.requireNonNull(entityExchangeResult.getResponseBody()));
                entityExchangeResult.assertWithDiagnostics(
                    () -> Assert.isTrue(strBody.contains("foo: bar"), "strBody assertion failed"));
            });
    }

    @Test
    void getByPropertyFileNameOfSpecificProfileThenTextResponse() {
        webClient
            .get().uri("/foo-dev.properties")
            .header(HttpHeaders.AUTHORIZATION, httpBasicAuthenticationHeaderValue())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
            .expectBody().consumeWith(entityExchangeResult -> {
                String strBody = new String(Objects.requireNonNull(entityExchangeResult.getResponseBody()));
                entityExchangeResult.assertWithDiagnostics(
                    () -> Assert.isTrue(strBody.contains("foo: foo"), "strBody assertion failed"));
            });
    }

    @Test
    void getWithoutAuthenticationThenUnauthorized() {
        webClient
            .get().uri("/foo/default")
            .exchange()
            .expectStatus().isUnauthorized();
    }

    private static String httpBasicAuthenticationHeaderValue() {
        return "Basic " + Base64.getEncoder().encodeToString(("test:password").getBytes());
    }

}
