package deliverystore.domain;

import deliverystore.domain.OrderPlaced;
import deliverystore.external.FoodCooking;
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
    
    // @PrePersist
    // public void onPrePersist() {
    //     // Get request from food       
    //     FoodCooking foodcooking =         
    //     OrderApplication.applicationContext.getBean(deliverystore.external.FoodCookingService.class)
    //     .getFoodCooking(Long.valueOf(getFoodId()));
           
    //     if(foodcooking.getStock() < getQty()) throw new RuntimeException("Out of Stock!");

    // }

    /**
     * 
     */
    @PostPersist
    public void onPostPersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        deliverystore.external.Payment payment = new deliverystore.external.Payment();
        payment.setAmount(String.valueOf(getPrice()));
        payment.setOrderId(String.valueOf(getId()));
        payment.setCustomerId(getCustomerId());
        payment.setStatus("주문-결제요청중");
           
        // mappings goes here
        OrderApplication.applicationContext
        .getBean(deliverystore.external.PaymentService.class)
        .pay(payment);

        //Delay
        try {
            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       
        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove(){

        deliverystore.external.Payment payment = new deliverystore.external.Payment();
        payment.setOrderId(String.valueOf(getId()));
        payment.setStatus("주문취소");

        // mappings goes here
        OrderApplication.applicationContext
        .getBean(deliverystore.external.PaymentService.class)
        .pay(payment);

        OrderCanceled orderCanceled = new OrderCanceled(this);        
        orderCanceled.publishAfterCommit();
    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }






}
