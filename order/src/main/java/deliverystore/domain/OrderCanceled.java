package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class OrderCanceled extends AbstractEvent {

    private Long id;
    private String foodId;
    private String storeId;
    private String address;
    private String customerId;
    private Integer qty;
    private Integer price;

    public OrderCanceled(Order aggregate){
        super(aggregate);
    }
    public OrderCanceled(){
        super();
    }
}
