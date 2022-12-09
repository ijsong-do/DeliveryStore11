package deliverystore.infra;

import deliverystore.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="deliveryLists", path="deliveryLists")
public interface DeliveryListRepository extends PagingAndSortingRepository<DeliveryList, Long> {

    

    
}
