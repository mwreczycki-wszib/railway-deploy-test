package com.example.payu.payments;



import com.example.payu.ApplicationConfig;
import com.example.payu.Checkout;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRequest {

    private String notifyUrl;
    private String continueUrl;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private String extOrderId;
    private long totalAmount;
    private Buyer buyer;
    private List<Product> products;

    private OrderRequest() {
    }

    public static OrderRequest build(String customerIp, ApplicationConfig config, Checkout checkout) {
        OrderRequest newRequest = new OrderRequest();
        newRequest.notifyUrl = config.getBaseUrl() + "/notify";
        newRequest.continueUrl = config.getBaseUrl() + "/thank-you";
        newRequest.customerIp = customerIp;
        newRequest.merchantPosId = config.getPayU().getPosId();
        newRequest.description = "Test payment";
        newRequest.currencyCode = "PLN";
        newRequest.extOrderId = UUID.randomUUID().toString();
        newRequest.totalAmount = checkout.getAmount();
        newRequest.buyer = Buyer.build(checkout);
        newRequest.products = Collections.singletonList(Product.build(checkout));
        return newRequest;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public String getMerchantPosId() {
        return merchantPosId;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public String getContinueUrl() {
        return continueUrl;
    }

    public static class Buyer {

        private String email;
        private String firstName;
        private String lastName;
        private String language;

        private Buyer() {
        }

        private static Buyer build(Checkout checkout) {
            Buyer newBuyer = new Buyer();
            newBuyer.email = "setsudan.hana@gmail.com";
            newBuyer.firstName = "Test";
            newBuyer.lastName = "PayU";
            newBuyer.language = LocaleContextHolder.getLocale().getLanguage();
            return newBuyer;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getLanguage() {
            return language;
        }
    }

    public static class Product {
        private String name;
        private long unitPrice;
        private short quantity;

        private Product() {
        }

        private static Product build(Checkout checkout) {
            Product newProduct = new Product();
            newProduct.name = "Test Product";
            newProduct.unitPrice = checkout.getAmount();
            newProduct.quantity = 1;
            return newProduct;
        }

        public String getName() {
            return name;
        }

        public long getUnitPrice() {
            return unitPrice;
        }

        public short getQuantity() {
            return quantity;
        }
    }

}
