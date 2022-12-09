package deliverystore.domain;

import deliverystore.infra.AbstractEvent;
import lombok.Data;
import java.util.*;


@Data
public class CookStarted extends AbstractEvent {

    private Long id;
    private String orderId;
    private String customerId;
    private String status;
    private String foodName;
}
