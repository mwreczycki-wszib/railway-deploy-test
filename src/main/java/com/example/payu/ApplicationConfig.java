package com.example.payu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationConfig {

    private String baseUrl;

    private PayU payU = new PayU();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public PayU getPayU() {
        return payU;
    }

    public void setPayU(PayU payU) {
        this.payU = payU;
    }

    public static class PayU {

        private String url;
        private String posId;
        private String md5;
        private String clientId;
        private String clientSecret;
        private String notificationSecret;

        public String getPosId() {
            return posId;
        }

        public void setPosId(String posId) {
            this.posId = posId;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNotificationSecret() {
            return notificationSecret;
        }

        public void setNotificationSecret(String notificationSecret) {
            this.notificationSecret = notificationSecret;
        }
    }

}


