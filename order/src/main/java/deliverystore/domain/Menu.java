package deliverystore.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;


@Entity
@Table(name="Menu_table")
@Data
public class Menu {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;


}
