package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private String foodId;
    private String storeId;
    private String customerId;
    private String address;
    private Integer qty;
    private Integer price;
}


