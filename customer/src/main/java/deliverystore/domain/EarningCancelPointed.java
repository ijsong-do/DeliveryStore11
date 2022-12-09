package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class EarningCancelPointed extends AbstractEvent {

    private Long id;

    public EarningCancelPointed(CustomerInfo aggregate){
        super(aggregate);
    }
    public EarningCancelPointed(){
        super();
    }
}
