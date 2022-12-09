package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class OrderAccepted extends AbstractEvent {

    private Long id;
    private String orderId;
    private String customerId;
    private String status;
    private String foodName;

    public OrderAccepted(FoodCooking aggregate){
        super(aggregate);
    }
    public OrderAccepted(){
        super();
    }
}
