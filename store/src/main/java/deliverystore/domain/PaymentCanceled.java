package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class PaymentCanceled extends AbstractEvent {

    private Long id;
    private String orderId;
    private String amount;
    private String status;
    private String customerId;
}


