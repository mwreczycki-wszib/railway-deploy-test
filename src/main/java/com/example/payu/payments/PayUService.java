package com.example.payu.payments;

import com.example.payu.ApplicationConfig;
import com.example.payu.Checkout;
import com.example.payu.utils.HttpReqRespUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Service
public class PayUService {

    private static final Logger logger = LoggerFactory.getLogger(PayUService.class);

    private static final String AUTHORIZE_URL_BASE
            = "/pl/standard/user/oauth/authorize?grant_type=client_credentials&client_id=%s&client_secret=%s";

    private static final String ORDER_URL_BASE = "/api/v2_1/orders";

    private final ApplicationConfig applicationConfig;
    private final RestTemplate restTemplate;

    public PayUService(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        this.restTemplate = new RestTemplate();
    }

    public Optional<String> authorize() {
        try {
            return Optional.ofNullable(restTemplate.postForObject(buildAuthorizeUrl(), null, AuthorizeResponse.class))
                    .map(AuthorizeResponse::accessToken);
        } catch (RestClientException restClientException) {
            logger.error("Unable to fetch authorization token from PayU", restClientException);
            return Optional.empty();
        }
    }

    public String postOrder(Checkout checkout) {
        String token = authorize().orElseThrow(); //FIXME

        URI uri = URI.create(applicationConfig.getPayU().getUrl() + ORDER_URL_BASE);
        OrderRequest request = OrderRequest.build(
                HttpReqRespUtils.getClientIpAddressIfServletRequestExist(),
                applicationConfig,
                checkout
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ token);

        HttpEntity<OrderRequest> entity = new HttpEntity<>(request, headers);

        return Optional.ofNullable(
                restTemplate.postForObject(uri, entity, OrderResponse.class)
        ).map(OrderResponse::redirectUri).orElseThrow(); //FIXME
    }

    private URI buildAuthorizeUrl() {
        return URI.create(
                String.format(
                        applicationConfig.getPayU().getUrl() + AUTHORIZE_URL_BASE,
                        applicationConfig.getPayU().getClientId(),
                        applicationConfig.getPayU().getClientSecret()
                )
        );
    }

}
