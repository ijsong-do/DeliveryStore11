package deliverystore.domain;

import deliverystore.infra.AbstractEvent;
import lombok.Data;
import java.util.*;


@Data
public class CookFinished extends AbstractEvent {

    private Long id;
    private String orderId;
    private String customerId;
    private String status;
    private String foodName;
}
