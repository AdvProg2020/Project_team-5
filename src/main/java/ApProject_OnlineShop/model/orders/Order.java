package ApProject_OnlineShop.model.orders;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "`Order`")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Order implements Serializable {
    @Transient
    private static long ordersCount = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId", nullable = false, unique = true)
    private long orderId;

    @Column(name = "ModificationDate", nullable = false)
    private LocalDate date;

    @Column(name = "TotalPrice", nullable = false)
    private long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "OrderStatus", nullable = false)
    private OrderStatus orderStatus;

    public enum OrderStatus {
        PROCESSING, READYTOSEND, SENT, RECEIVED
    }

    public Order(long price) {
        this.price = price;
        this.date = LocalDate.now();
        this.orderStatus = OrderStatus.PROCESSING;
        ordersCount++;
    }

    public Order() {
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

    public static long getOrdersCount() {
        return ordersCount;
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

