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
    @Autowired CustomerInfoRepository customerInfoRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='PaymentApproval'")
    public void wheneverPaymentApproval_EarningPoints(@Payload PaymentApproval paymentApproval){

        PaymentApproval event = paymentApproval;
        System.out.println("\n\n##### listener EarningPoints : " + paymentApproval + "\n\n");


        

        // Sample Logic //
        CustomerInfo.earningPoints(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderAccepted'")
    public void wheneverOrderAccepted_KatokNotify(@Payload OrderAccepted orderAccepted){

        OrderAccepted event = orderAccepted;
        System.out.println("\n\n##### listener KatokNotify : " + orderAccepted + "\n\n");


        

        // Sample Logic //

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderRejected'")
    public void wheneverOrderRejected_KatokNotify(@Payload OrderRejected orderRejected){

        OrderRejected event = orderRejected;
        System.out.println("\n\n##### listener KatokNotify : " + orderRejected + "\n\n");


        

        // Sample Logic //

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='CookStarted'")
    public void wheneverCookStarted_KatokNotify(@Payload CookStarted cookStarted){

        CookStarted event = cookStarted;
        System.out.println("\n\n##### listener KatokNotify : " + cookStarted + "\n\n");


        

        // Sample Logic //

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='CookFinished'")
    public void wheneverCookFinished_KatokNotify(@Payload CookFinished cookFinished){

        CookFinished event = cookFinished;
        System.out.println("\n\n##### listener KatokNotify : " + cookFinished + "\n\n");


        

        // Sample Logic //

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='Picked'")
    public void wheneverPicked_KatokNotify(@Payload Picked picked){

        Picked event = picked;
        System.out.println("\n\n##### listener KatokNotify : " + picked + "\n\n");


        

        // Sample Logic //

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='Delivered'")
    public void wheneverDelivered_KatokNotify(@Payload Delivered delivered){

        Delivered event = delivered;
        System.out.println("\n\n##### listener KatokNotify : " + delivered + "\n\n");


        

        // Sample Logic //

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='PaymentCanceled'")
    public void wheneverPaymentCanceled_EarningCanclePonints(@Payload PaymentCanceled paymentCanceled){

        PaymentCanceled event = paymentCanceled;
        System.out.println("\n\n##### listener EarningCanclePonints : " + paymentCanceled + "\n\n");


        

        // Sample Logic //
        CustomerInfo.earningCanclePonints(event);
        

        

    }

}


