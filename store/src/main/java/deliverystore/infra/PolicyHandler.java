package deliverystore.infra;

import javax.naming.NameParser;

import javax.naming.NameParser;
import javax.transaction.Transactional;

import deliverystore.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import deliverystore.domain.*;

@Service
@Transactional
public class PolicyHandler{
    @Autowired FoodCookingRepository foodCookingRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPlaced'")
    public void wheneverOrderPlaced_OrderInfoCopy(@Payload OrderPlaced orderPlaced){

        OrderPlaced event = orderPlaced;
        System.out.println("\n\n##### listener OrderInfoCopy : " + orderPlaced + "\n\n");


        

        // Sample Logic //
        FoodCooking.orderInfoCopy(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='PaymentApproval'")
    public void wheneverPaymentApproval_UpdateStatus(@Payload PaymentApproval paymentApproval){

        PaymentApproval event = paymentApproval;
        System.out.println("\n\n##### listener UpdateStatus : " + paymentApproval + "\n\n");


        

        // Sample Logic //
        FoodCooking.updateStatus(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='PaymentCanceled'")
    public void wheneverPaymentCanceled_UpdateStatus(@Payload PaymentCanceled paymentCanceled){

        PaymentCanceled event = paymentCanceled;
        System.out.println("\n\n##### listener UpdateStatus : " + paymentCanceled + "\n\n");


        

        // Sample Logic //
        FoodCooking.updateStatus(event);
        

        

    }

}


