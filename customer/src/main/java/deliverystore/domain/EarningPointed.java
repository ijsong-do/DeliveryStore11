package deliverystore.domain;

import deliverystore.domain.*;
import deliverystore.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class EarningPointed extends AbstractEvent {

    private Long id;

    public EarningPointed(CustomerInfo aggregate){
        super(aggregate);
    }
    public EarningPointed(){
        super();
    }
}
