package deliverystore.domain;

import deliverystore.infra.AbstractEvent;
import lombok.Data;
import java.util.*;


@Data
public class PaymentCanceled extends AbstractEvent {

    private Long id;
    private String orderId;
    private String amount;
    private String status;
    private String customerId;
}
