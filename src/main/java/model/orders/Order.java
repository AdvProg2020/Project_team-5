package model.orders;

import java.time.LocalDate;

public abstract class Order {
    private static long ordersCount = 1;
    private long orderId;
    private LocalDate date;
    private long price;
    private OrderStatus orderStatus;

    public Order() {
        this.date = LocalDate.now();
        this.orderStatus = OrderStatus.PROCESSING;
        orderId = ordersCount++;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getPrice() {
        return price;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}

enum OrderStatus {
    PROCESSING, READYTOSEND, SENT, RECEIVED;
}