package com.example.payu.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorizeResponse(@JsonProperty("access_token") String accessToken) {
}
