package deliverystore.infra;
import deliverystore.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class CustomerInfoHateoasProcessor implements RepresentationModelProcessor<EntityModel<CustomerInfo>>  {

    @Override
    public EntityModel<CustomerInfo> process(EntityModel<CustomerInfo> model) {

        
        return model;
    }
    
}
