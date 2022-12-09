package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class PaymentCanceled extends AbstractEvent {

    private Long id;
    private String orderId;
    private String amount;
    private String status;
    private String customerId;

    public PaymentCanceled(Payment aggregate){
        super(aggregate);
    }
    public PaymentCanceled(){
        super();
    }
}
