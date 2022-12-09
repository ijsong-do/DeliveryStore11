package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class OrderRejected extends AbstractEvent {

    private Long id;
    private String orderId;
    private String customerId;
    private String status;
    private String foodName;

    public OrderRejected(FoodCooking aggregate){
        super(aggregate);
    }
    public OrderRejected(){
        super();
    }
}
