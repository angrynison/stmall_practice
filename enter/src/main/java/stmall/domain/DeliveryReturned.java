package stmall.domain;

import java.util.*;
import lombok.*;
import stmall.domain.*;
import stmall.infra.AbstractEvent;

@Data
@ToString
public class DeliveryReturned extends AbstractEvent {

    private Long id;
    private Long orderid;
    private String custeomerid;
    private String itemId;
    private Integer qty;
    private String address;
}
