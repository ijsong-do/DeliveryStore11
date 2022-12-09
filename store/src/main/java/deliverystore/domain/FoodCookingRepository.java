package deliverystore.domain;

import deliverystore.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="foodCookings", path="foodCookings")
public interface FoodCookingRepository extends PagingAndSortingRepository<FoodCooking, Long>{

}
