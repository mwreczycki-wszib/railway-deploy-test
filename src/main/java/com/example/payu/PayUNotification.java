package com.example.payu;

public class PayUNotification {

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static class Order {
        private String orderId;
        private Status status;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public enum Status {
            NEW,
            PENDING,
            WAITING_FOR_CONFIRMATION,
            CANCELED,
            COMPLETED
        }
    }

}
