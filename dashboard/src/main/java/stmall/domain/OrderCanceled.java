package stmall.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import stmall.infra.AbstractEvent;

@Data
public class OrderCanceled extends AbstractEvent {

    private Long id;
    private String customerid;
    private String itemId;
    private Integer qty;
    private String address;
}
