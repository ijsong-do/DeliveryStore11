package deliverystore.external;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Data
public class PayCommand {

    private String orderId;
    private String amount;
    private String status;
    private String customerId;
}
