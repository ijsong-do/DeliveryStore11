package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class Picked extends AbstractEvent {

    private Long id;
    private String orderId;
    private String status;
    private String customerId;

    public Picked(Delivery aggregate){
        super(aggregate);
    }
    public Picked(){
        super();
    }
}
