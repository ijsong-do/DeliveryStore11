package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class CookStarted extends AbstractEvent {

    private Long id;
    private String orderId;
    private String customerId;
    private String status;
    private String foodName;

    public CookStarted(FoodCooking aggregate){
        super(aggregate);
    }
    public CookStarted(){
        super();
    }
}
