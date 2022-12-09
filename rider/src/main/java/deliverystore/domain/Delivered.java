package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class Delivered extends AbstractEvent {

    private Long id;
    private String orderId;
    private String status;
    private String customerId;

    public Delivered(Delivery aggregate){
        super(aggregate);
    }
    public Delivered(){
        super();
    }
}
