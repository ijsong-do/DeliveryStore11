package deliverystore.infra;

import deliverystore.domain.*;
import deliverystore.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryListViewHandler {

    @Autowired
    private DeliveryListRepository deliveryListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCookFinished_then_CREATE_1 (@Payload CookFinished cookFinished) {
        try {

            if (!cookFinished.validate()) return;

            // view 객체 생성
            DeliveryList deliveryList = new DeliveryList();
            // view 객체에 이벤트의 Value 를 set 함
            deliveryList.setId(Long.valueOf(cookFinished.getOrderId()));
            // view 레파지 토리에 save
            deliveryListRepository.save(deliveryList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}

