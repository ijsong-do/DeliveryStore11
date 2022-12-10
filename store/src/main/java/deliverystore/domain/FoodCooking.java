package deliverystore.domain;

import deliverystore.domain.CookStarted;
import deliverystore.domain.CookFinished;
import deliverystore.StoreApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="FoodCooking_table")
@Data

public class FoodCooking  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String orderId;
    
    
    
    
    
    private String customerId;
    
    
    
    
    
    private String status;
    
    
    
    
    
    private String foodId;
    
    
    
    
    
    private Integer stock;

    @PostPersist
    public void onPostPersist(){


        CookStarted cookStarted = new CookStarted(this);
        cookStarted.publishAfterCommit();



        CookFinished cookFinished = new CookFinished(this);
        cookFinished.publishAfterCommit();

    }

    public static FoodCookingRepository repository(){
        FoodCookingRepository foodCookingRepository = StoreApplication.applicationContext.getBean(FoodCookingRepository.class);
        return foodCookingRepository;
    }



    public void accept(AcceptCommand acceptCommand){
        if(acceptCommand.getAccept()) {

            OrderAccepted orderAccepted = new OrderAccepted(this);
            orderAccepted.publishAfterCommit();

            setStatus("주문-가게접수됨");
        } else {
            OrderRejected orderRejected = new OrderRejected(this);
            orderRejected.publishAfterCommit();

            setStatus("주문-가게거절함");
        }        
    }
    public void start(){
        
    }
    public void finish(){
    }

    public static void orderInfoCopy(OrderPlaced orderPlaced){

        /** Example 1:  new item   */
        FoodCooking foodCooking = new FoodCooking();
        foodCooking.setCustomerId(orderPlaced.getCustomerId());
        foodCooking.setFoodId(orderPlaced.getFoodId());
        foodCooking.setOrderId(String.valueOf(orderPlaced.getId()));
        foodCooking.setStatus("주문-결제요청중");

        repository().save(foodCooking);
      

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.getId()).ifPresent(foodCooking->{
            
            foodCooking. // do something
            repository().save(foodCooking);


         });
        */

        
    }
    public static void updateStatus(PaymentApproval paymentApproval){

        /** Example 1:  new item 
        FoodCooking foodCooking = new FoodCooking();
        repository().save(foodCooking);

        */

        /** Example 2:  finding and process*/        
        repository().findByOrderId(paymentApproval.getOrderId()).ifPresent(foodCooking->{
            
            foodCooking.setStatus("주문-가게접수"); // do something
            repository().save(foodCooking);

         });
        

        
    }
    public static void updateStatus(PaymentCanceled paymentCanceled){

        /** Example 1:  new item 
        FoodCooking foodCooking = new FoodCooking();
        repository().save(foodCooking);

        */

        /** Example 2:  finding and process
        
        repository().findById(paymentCanceled.get???()).ifPresent(foodCooking->{
            
            foodCooking // do something
            repository().save(foodCooking);


         });
        */

        
    }


}
