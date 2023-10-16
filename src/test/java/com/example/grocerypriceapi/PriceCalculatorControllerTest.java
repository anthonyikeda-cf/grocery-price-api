package com.example.grocerypriceapi;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@SpringBootTest
@AutoConfigureWebTestClient
class PriceCalculatorControllerTest {
        private final Logger log = LoggerFactory.getLogger(getClass().getCanonicalName());

        @Autowired
        WebTestClient client;

        @Test
        void testCreateCalculation() {
                var request = new CalculationRequest();
                request.setCalculationType(CalculationType.ALL);
                request.setCustomerId("Customer1234");
                var item1 = new LineItem("12345", 4, null);
                var item2 = new LineItem("5432", 2, null);
                List<LineItem> items = Arrays.asList(item1, item2);
                request.setLineItems(items);

                Jwt jwt = Jwt.withTokenValue("token")
                                .issuer("https://restrapid.auth0.com/")
                                .header("alg", "none")
                                .claim("sub", "user")
                                .claim("scope", "read")
                                .build();

                Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("SCOPE_read");
                JwtAuthenticationToken token = new JwtAuthenticationToken(jwt, authorities);

                WebTestClient.ResponseSpec response = client
                                .mutateWith(mockJwt())
                                .mutateWith(csrf())
                                .post()
                                .uri("/calculation/v1")
                                .body(BodyInserters.fromValue(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .exchange();

                var results = response
                                .expectStatus()
                                .isCreated()
                                .expectBody()
                                .consumeWith(System.out::println)
                                .isEmpty().getResponseHeaders();

                URI location = results.getLocation();

                assertThat(location).isNotNull();

                log.info("Created URI = {}", location.toString());

                client
                                .mutateWith(mockAuthentication(token))
                                .get()
                                .uri(location).exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .consumeWith(System.out::println)
                                .jsonPath("$.*").isNotEmpty();

        }
}
