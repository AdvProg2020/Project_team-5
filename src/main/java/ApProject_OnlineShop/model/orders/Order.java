package ApProject_OnlineShop.model.orders;

import java.time.LocalDate;

public abstract class Order {
    private static long ordersCount = 1;
    private long orderId;
    private LocalDate date;
    private long price;
    private OrderStatus orderStatus;

    public enum OrderStatus {
        PROCESSING, READYTOSEND, SENT, RECEIVED
    }

    public Order(long price) {
        this.price = price;
        this.date = LocalDate.now();
        this.orderStatus = OrderStatus.PROCESSING;
        orderId = ordersCount++;
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

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static void setOrdersCount(long ordersCount) {
        Order.ordersCount = ordersCount;
    }

    public String briefString(){
        return "order ID: " + getOrderId() +"   \t date : " + getDate();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order){
            Order order2= (Order) obj;
            return (this.getOrderId() == order2.getOrderId());
        }
        return super.equals(obj);
    }
}

