package deliverystore.domain;

import deliverystore.domain.PaymentApproval;
import deliverystore.domain.PaymentCanceled;
import deliverystore.PaymentApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Payment_table")
@Data

public class Payment  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String orderId;
    
    
    
    
    
    private String amount;
    
    
    
    
    
    private String status;
    
    
    
    
    
    private String customerId;

    @PostPersist
    public void onPostPersist(){

        PaymentApproval paymentApproval = new PaymentApproval(this);        
        paymentApproval.publishAfterCommit();
       // PaymentCanceled paymentCanceled = new PaymentCanceled(this);
       // paymentCanceled.publishAfterCommit();

    }

    public static PaymentRepository repository(){
        PaymentRepository paymentRepository = PaymentApplication.applicationContext.getBean(PaymentRepository.class);
        return paymentRepository;
    }



    public void pay(PayCommand payCommand){

        Payment payment = new Payment();
        payment.setOrderId(payCommand.getOrderId());
        payment.setCustomerId((payCommand.getCustomerId()));
        payment.setAmount(payCommand.getAmount());
        setStatus("주문-결재완료");

        repository().save(payment);
        
        PaymentApproval paymentApproval = new PaymentApproval(this);        
        paymentApproval.publishAfterCommit();

    }

    public static void payCancel(OrderCanceled orderCanceled){
        
        repository().findById(Long.valueOf(orderCanceled.getId())).ifPresent(payment->{
                        
            //repository().save(payment);
            repository().delete(payment);
            
            PaymentCanceled paymentCanceled = new PaymentCanceled(payment);
            paymentCanceled.publishAfterCommit();

         });      
    }


}

