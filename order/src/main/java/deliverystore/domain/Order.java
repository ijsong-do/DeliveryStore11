package deliverystore.domain;

import deliverystore.domain.OrderPlaced;
import deliverystore.domain.OrderCanceled;
import deliverystore.OrderApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Order_table")
@Data

public class Order  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String foodId;
    
    
    
    
    
    private String storeId;
    
    
    
    
    
    private String address;
    
    
    
    
    
    private String customerId;
    
    
    
    
    
    private Integer qty;
    
    
    
    
    
    private Integer price;
    
    @PostPersist
    public void onPostPersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        deliverystore.external.PayCommand payCommand = new deliverystore.external.PayCommand();
        payCommand.setAmount(String.valueOf(getPrice()));
        payCommand.setOrderId(String.valueOf(getId()));
        payCommand.setCustomerId(getCustomerId());
        payCommand.setStatus("주문-결제요청중");
   
        // mappings goes here
        OrderApplication.applicationContext.getBean(deliverystore.external.PaymentService.class)
            .pay(getId(), payCommand); 

        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();


        OrderCanceled orderCanceled = new OrderCanceled(this);
        orderCanceled.publishAfterCommit();

        // Get request from FoodCooking
        //deliverystore.external.FoodCooking foodCooking =
        //    Application.applicationContext.getBean(deliverystore.external.FoodCookingService.class)
        //    .getFoodCooking(/** mapping value needed */);

    }
    @PreRemove
    public void onPreRemove(){
    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }






}
