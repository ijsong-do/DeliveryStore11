package deliverystore.domain;

import deliverystore.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="customerInfos", path="customerInfos")
public interface CustomerInfoRepository extends PagingAndSortingRepository<CustomerInfo, Long>{

}
