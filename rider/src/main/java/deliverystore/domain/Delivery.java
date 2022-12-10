package deliverystore.domain;

import deliverystore.domain.Picked;
import deliverystore.domain.Delivered;
import deliverystore.RiderApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Delivery_table")
@Data

public class Delivery  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String orderId;
    
    
    
    
    
    private String status;
    
    
    
    
    
    private String customerId;

    private String address;

    @PostPersist
    public void onPostPersist(){


        Picked picked = new Picked(this);
        picked.publishAfterCommit();



        Delivered delivered = new Delivered(this);
        delivered.publishAfterCommit();

    }

    public static DeliveryRepository repository(){
        DeliveryRepository deliveryRepository = RiderApplication.applicationContext.getBean(DeliveryRepository.class);
        return deliveryRepository;
    }




    public static void orderInfoCopy(OrderPlaced orderPlaced){

        /** Example 1:  new item */
        Delivery delivery = new Delivery();
        delivery.setCustomerId(orderPlaced.getAddress());
        delivery.setOrderId(String.valueOf(orderPlaced.getId()));
        delivery.setAddress(orderPlaced.getAddress());
        delivery.setStatus("주문-결제요청중");

        repository().save(delivery);
        
        // FoodCooking foodCooking = new FoodCooking();
        // foodCooking.setCustomerId(orderPlaced.getCustomerId());
        // foodCooking.setFoodId(orderPlaced.getFoodId());
        // foodCooking.setOrderId(String.valueOf(orderPlaced.getId()));
        // foodCooking.setStatus("미결제");

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);


         });
        */

        
    }
    public static void updateStatus(CookFinished cookFinished){

        /** Example 1:  new item 
        Delivery delivery = new Delivery();
        repository().save(delivery);

        */

        /** Example 2:  finding and process*/
        
        // repository().findByOrderId(cookFinished.getOrderId()).ifPresent(delivery->{
            
        //     delivery.setStatus(""); // do something
        //     repository().save(delivery);

        //  });
        

        
    }


}
