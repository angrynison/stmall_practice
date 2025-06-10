package stmall.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import stmall.domain.*;
import stmall.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Stockincreased extends AbstractEvent {

    private Long id;
    private String name;
    private Integer stock;

    public Stockincreased(Inventory aggregate) {
        super(aggregate);
    }

    public Stockincreased() {
        super();
    }
}
//>>> DDD / Domain Event
