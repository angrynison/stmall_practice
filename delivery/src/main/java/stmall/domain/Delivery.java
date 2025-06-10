package stmall.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import stmall.DeliveryApplication;
import stmall.domain.DeliveryCompleted;
import stmall.domain.DeliveryReturned;

@Entity
@Table(name = "Delivery_table")
@Data
//<<< DDD / Aggregate Root
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;          
    private String customerId;      
    private String itemId;
    private Integer qty;
    private String address;
    private String status;

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }

    //<<< Clean Arch / Port Method
    // save 하고 deliveryCompleted 하고 commit 하는 방법 선택
    public static void startDelivery(OrderPlaced orderPlaced) {
        // 비즈니스 로직 구현 
        // 주문을 받아서 배송을 시작한 경우
        Delivery delivery = new Delivery();

        delivery.setOrderId(orderPlaced.getId());
        delivery.setAddress(orderPlaced.getAddress());
        delivery.setCustomerId(orderPlaced.getCustomerId());  // ✅ 카멜케이스 getter
        delivery.setItemId(orderPlaced.getItemId());
        delivery.setQty(orderPlaced.getQty());
        delivery.setStatus("DELIVERY STARTED");

        repository().save(delivery);

        DeliveryCompleted deliveryCompleted = new DeliveryCompleted(delivery); // deliveryCompleted 이벤트를 만든다
        deliveryCompleted.publishAfterCommit();

        /** Example 2: finding and process
        repository().findById(orderPlaced.get???()).ifPresent(delivery -> {
            delivery // do something
            repository().save(delivery);

            DeliveryCompleted deliveryCompleted = new DeliveryCompleted(delivery);
            deliveryCompleted.publishAfterCommit();
        });
        */
    }

    //<<< Clean Arch / Port Method
    public static void cancelDelivery(OrderCanceled orderCanceled) {
        // implement business logic here:

        /** Example 1: new item
        Delivery delivery = new Delivery();
        repository().save(delivery);

        DeliveryReturned deliveryReturned = new DeliveryReturned(delivery);
        deliveryReturned.publishAfterCommit();
        */

        /** Example 2: finding and process
        repository().findById(orderCanceled.get???()).ifPresent(delivery -> {
            delivery // do something
            repository().save(delivery);

            DeliveryReturned deliveryReturned = new DeliveryReturned(delivery);
            deliveryReturned.publishAfterCommit();
        });
        */
    }
}
//>>> DDD / Aggregate Root
