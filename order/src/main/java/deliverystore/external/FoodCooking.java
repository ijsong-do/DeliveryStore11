package deliverystore.external;

import lombok.Data;
import java.util.Date;
@Data
public class FoodCooking {

    private Long id;
    private String orderId;
    private String customerId;
    private String status;
    private String foodName;
    private Integer stock;
}


