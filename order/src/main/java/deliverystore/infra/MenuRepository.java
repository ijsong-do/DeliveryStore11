package deliverystore.infra;

import deliverystore.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="menus", path="menus")
public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {

    

    
}
