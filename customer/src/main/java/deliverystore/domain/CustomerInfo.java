package deliverystore.domain;

import deliverystore.domain.EarningPointed;
import deliverystore.domain.EarningCancelPointed;
import deliverystore.CustomerApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="CustomerInfo_table")
@Data

public class CustomerInfo  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String name;
    
    
    
    
    
    private String mobile;
    
    
    
    
    
    private String address;
    
    
    
    
    
    private Integer point;

    @PostPersist
    public void onPostPersist(){


        EarningPointed earningPointed = new EarningPointed(this);
        earningPointed.publishAfterCommit();



        EarningCancelPointed earningCancelPointed = new EarningCancelPointed(this);
        earningCancelPointed.publishAfterCommit();

    }

    public static CustomerInfoRepository repository(){
        CustomerInfoRepository customerInfoRepository = CustomerApplication.applicationContext.getBean(CustomerInfoRepository.class);
        return customerInfoRepository;
    }




    public static void earningPoints(PaymentApproval paymentApproval){

        /** Example 1:  new item 
        CustomerInfo customerInfo = new CustomerInfo();
        repository().save(customerInfo);

        EarningPointed earningPointed = new EarningPointed(customerInfo);
        earningPointed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentApproval.get???()).ifPresent(customerInfo->{
            
            customerInfo // do something
            repository().save(customerInfo);

            EarningPointed earningPointed = new EarningPointed(customerInfo);
            earningPointed.publishAfterCommit();

         });
        */

        
    }
    public static void earningCanclePonints(PaymentCanceled paymentCanceled){

        /** Example 1:  new item 
        CustomerInfo customerInfo = new CustomerInfo();
        repository().save(customerInfo);

        EarningCancelPointed earningCancelPointed = new EarningCancelPointed(customerInfo);
        earningCancelPointed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentCanceled.get???()).ifPresent(customerInfo->{
            
            customerInfo // do something
            repository().save(customerInfo);

            EarningCancelPointed earningCancelPointed = new EarningCancelPointed(customerInfo);
            earningCancelPointed.publishAfterCommit();

         });
        */

        
    }


}
