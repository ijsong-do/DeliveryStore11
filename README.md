# 배달상점 (DeliveryStore)

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.
이는 클라우드 네이티브 애플리케이션의 개발에 요구되는 체크포인트들을 통과하기 위한 예시 답안을 포함합니다.

# 서비스 시나리오

기능적 요구사항
1. 고객이 메뉴를 선택하여 주문한다.
1. 고객이 선택한 메뉴에 대해 결제한다.
1. 고객이 결제하면 포인트를 적립한다.(추가)
1. 주문이 되면 주문 내역이 입점상점주인에게 주문정보가 전달된다.
1. 상점주는 주문을 수락하거나 거절할 수 있다.
1. 상점주는 요리시작때와 완료 시점에 시스템에 상태를 입력한다.
1. 고객은 아직 요리가 시작되지 않은 주문은 취소할 수 있다.
1. 고객이 결제 취소하면 포인트 적립도 취소 된다.(추가)
1. 요리가 완료되면 고객의 지역 인근의 라이더들에 의해 배송건 조회가 가능하다.
1. 라이더가 해당 요리를 Pick한 후, 앱을 통해 통보한다.
1. 고객이 주문상태를 중간중간 조회한다.
1. 주문상태가 바뀔 때 마다 카톡으로 알림을 보낸다
1. 고객이 요리를 배달 받으면 배송확인 버튼을 탭하여, 모든 거래가 완료된다

비기능적 요구사항
1. 장애격리
    1. 상점관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    1. 결제시스템이 과중되면 사용자를 잠시동안 받지 않고 결제를 잠시후에 하도록 유도한다  Circuit breaker, fallback
1. 성능
    1. 고객이 자주 상점관리에서 확인할 수 있는 배달상태를 주문시스템(프론트엔드)에서 확인할 수 있어야 한다  CQRS
    1. 배달상태가 바뀔때마다 카톡 등으로 알림을 줄 수 있어야 한다  Event driven


## Model
![image](https://user-images.githubusercontent.com/118672378/206361894-5f700faa-6036-4d65-aa3c-408eae41d66f.png)

요구사항을 커버하는지 검증

![image](https://user-images.githubusercontent.com/118672378/206360745-681ed5ff-05bc-4128-a6ee-ad483d8a98cc.png)

    - 1. 고객이 메뉴를 선택하여 주문한다. (ok)
    - 2. 고객이 선택한 메뉴에 대해 결제한다. (ok)
    - 3. 주문이 되면 주문 내역이 입점상점주인에게 주문정보가 전달된다 (ok)
    
![image](https://user-images.githubusercontent.com/118672378/206361657-b1c2e890-df57-4a75-8c6c-e8e5309b8480.png)

    - 4. 주문이 되면 주문 내역이 입점상점주인에게 주문정보가 전달된다. (ok)
    - 5. 주문을수락하거나 거절할 수 있다. (ok)
    - 6. 상점주는 요리시작때와 완료시점에 시스템에 상태를 입력한다. (ok)
    - 7. 고객은 아직 요리가 시작되지 않은 주문은 취소 할 수 있다. (ok)
    - 8. 고객이 결제 취소하면 포인트 적립도 취소된다. (ok)

![image](https://user-images.githubusercontent.com/118672378/206443892-674efaf2-e5ab-45a3-8e3a-d1d79d17cbd5.png)

    - 9. 요리가 완료되면 고객의 지역 인근의 라이더들에 의해 배송건 조회가 가능하다. (ok)
    - 10. 라이더가 해당 요리를 Pick한 후, 앱을 통해 통보한다. (ok)
    - 11. 고객이 주문상태를 중간중간 조회한다. (ok)
    - 12. 주문상태가 바뀔때 마다 카톡으로 알림을 보낸다 (ok)
    - 13. 고객이 요리를 배달받으면 배송확인 버튼을 탭하여, 모든 거래가 완료된다.(ok)
    
# 체크포인트
## 1. Saga(Pub/Sub)
Order 서비스에서 POST 방식으로 주문을 하면 OrderPlaced 이벤트가 발생되면서 아래 같이 전달된다. 
- Payment 서비스에서 pay Command로 요청정보를 전달한다.(req/res 동기방식)
- Store 서비스, rider 서비스에 orderInfoCopy 정책으로 주문정보를 전달한다. (Pub/Sub : 비동기)

생성된 서비스의 기동 한다. 
```
http :8081/orders

http :8082/payments

http :8084/foodCookings

http :8085/deliveries
```

1건의 주문을 등록한다.
```
http POST http://localhost:8081/orders foodId="탕수육" address="서울 용산구 용산동" customerId="song" qty="1" price=10000
```
```
gitpod /workspace/DeliveryStore (main) $ http POST http://localhost:8081/orders foodId="탕수육" address="서울 용산구 용산동" customerId="song" qty="1" price=10000
HTTP/1.1 201 
Connection: keep-alive
Content-Type: application/json
Date: Thu, 08 Dec 2022 16:16:27 GMT
Keep-Alive: timeout=60
Location: http://localhost:8081/orders/1
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_links": {
        "order": {
            "href": "http://localhost:8081/orders/1"
        },
        "self": {
            "href": "http://localhost:8081/orders/1"
        }
    },
    "address": "서울 용산구 용산동",
    "customerId": "song",
    "foodId": "탕수육",
    "price": 10000,
    "qty": 1,
    "storeId": null
}
```
- 주문(Order) 정보 조회한다.
```
gitpod /workspace/DeliveryStore (main) $ http GET localhost:8081/orders
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Thu, 08 Dec 2022 16:17:51 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "orders": [
            {
                "_links": {
                    "order": {
                        "href": "http://localhost:8081/orders/1"
                    },
                    "self": {
                        "href": "http://localhost:8081/orders/1"
                    }
                },
                "address": "서울 용산구 용산동",
                "customerId": "song",
                "foodId": "탕수육",
                "price": 10000,
                "qty": 1,
                "storeId": null
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8081/profile/orders"
        },
        "self": {
            "href": "http://localhost:8081/orders"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}
```
- payments 정보 조회한다.
```
gitpod /workspace/DeliveryStore (main) $ http GET localhost:8082/payments
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Thu, 08 Dec 2022 16:20:31 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "payments": [
            {
                "_links": {
                    "payment": {
                        "href": "http://localhost:8082/payments/1"
                    },
                    "self": {
                        "href": "http://localhost:8082/payments/1"
                    }
                },
                "amount": "10000",
                "customerId": "song",
                "orderId": "1",
                "status": "주문-결제요청중"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8082/profile/payments"
        },
        "self": {
            "href": "http://localhost:8082/payments"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}
```
- foodCookings 정보 조회한다.
```
gitpod /workspace/DeliveryStore (main) $ http GET localhost:8084/foodCookings
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Thu, 08 Dec 2022 16:21:07 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "foodCookings": [
            {
                "_links": {
                    "accept": {
                        "href": "http://localhost:8084/foodCookings/1/accept"
                    },
                    "finish": {
                        "href": "http://localhost:8084/foodCookings/1/finish"
                    },
                    "foodCooking": {
                        "href": "http://localhost:8084/foodCookings/1"
                    },
                    "self": {
                        "href": "http://localhost:8084/foodCookings/1"
                    },
                    "start": {
                        "href": "http://localhost:8084/foodCookings/1/start"
                    }
                },
                "customerId": "song",
                "foodId": "탕수육",
                "foodName": null,
                "orderId": "1",
                "qty": null,
                "status": "주문-결제요청중"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8084/profile/foodCookings"
        },
        "self": {
            "href": "http://localhost:8084/foodCookings"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}
```
- deliveries 정보 조회한다.
```
gitpod /workspace/DeliveryStore (main) $ http GET localhost:8085/deliveries
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Thu, 08 Dec 2022 16:21:14 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "deliveries": [
            {
                "_links": {
                    "delivery": {
                        "href": "http://localhost:8085/deliveries/1"
                    },
                    "self": {
                        "href": "http://localhost:8085/deliveries/1"
                    }
                },
                "address": "서울 용산구 용산동",
                "customerId": "song",
                "orderId": "1",
                "status": "주문-결제요청중"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8085/profile/deliveries"
        },
        "self": {
            "href": "http://localhost:8085/deliveries"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}
```

## 2. CQRS
모델링에 Read Model 추가 (MyPage 녹색스티커)
Read Model 설계
![image](https://user-images.githubusercontent.com/118672378/206856797-76c06985-d732-4dd2-8f94-2ae111ab8e0b.png)

View Model 코드 확인

- MyPageViewHandler에서 구현체 코드 확인
```
@Service
public class MyPageViewHandler {

    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1 (@Payload OrderPlaced orderPlaced) {
        try {

            if (!orderPlaced.validate()) return;

            // view 객체 생성
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 Value 를 set 함
            myPage.setId(orderPlaced.getId());
            myPage.setStatus("주문됨");            
            // view 레파지 토리에 save
            myPageRepository.save(myPage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
```
코드 실행하여 확인
- order 서비스 기동, 주문 
```
http POST http://localhost:8081/orders foodId="짜장" address="서울 서초구 신사동" customerId="song" qty=1 price="2500"

http POST http://localhost:8081/orders foodId="짬뽕" address="서울 용산구 한남동" customerId="song" qty=1 price="9000"
```
- 주문조회
```
gitpod /workspace/DeliveryStore11 (main) $ http :8081/orders
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Sat, 10 Dec 2022 13:14:52 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "orders": [
            {
                "_links": {
                    "order": {
                        "href": "http://localhost:8081/orders/2"
                    },
                    "self": {
                        "href": "http://localhost:8081/orders/2"
                    }
                },
                "address": "서울 서초구 신사동",
                "customerId": "song",
                "foodId": "짜장",
                "price": 2500,
                "qty": 1,
                "storeId": null
            },
            {
                "_links": {
                    "order": {
                        "href": "http://localhost:8081/orders/4"
                    },
                    "self": {
                        "href": "http://localhost:8081/orders/4"
                    }
                },
                "address": "서울 용산구 한남동",
                "customerId": "song",
                "foodId": "짬뽕",
                "price": 9000,
                "qty": 1,
                "storeId": null
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8081/profile/orders"
        },
        "self": {
            "href": "http://localhost:8081/orders"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 2,
        "totalPages": 1
    }
}
```
- customer 서비스 기동 후 확인
```
gitpod /workspace/DeliveryStore11 (main) $ http GET http://localhost:8083/myPages
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Sat, 10 Dec 2022 13:16:25 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "myPages": [
            {
                "_links": {
                    "myPage": {
                        "href": "http://localhost:8083/myPages/2"
                    },
                    "self": {
                        "href": "http://localhost:8083/myPages/2"
                    }
                },
                "status": "주문됨"
            },
            {
                "_links": {
                    "myPage": {
                        "href": "http://localhost:8083/myPages/4"
                    },
                    "self": {
                        "href": "http://localhost:8083/myPages/4"
                    }
                },
                "status": "주문됨"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8083/profile/myPages"
        },
        "self": {
            "href": "http://localhost:8083/myPages"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 2,
        "totalPages": 1
    }
}
```
## 3. Compensation / Correlation
어떠한 이벤트로 인하여 발생한 변경사항들에 대하여 고객이 원하거나 어떠한 기술적 이유로 인하여 해당 트랜잭션을 취소해야 하는 경우 이를 원복하거나 보상해주는 처리를 Compensation 이라고 한다. 그리고 해당 취소건에 대하여 여러개의 마이크로 서비스 내의 데이터간 상관 관계를 키값으로 연결하여 취소해야 하는데, 이러한 관계값에 대한 처리를 Correlation 이라고 한다.

주문을 1건을 하고 Order 서비스와 Payment 서비스를 조회한다.
- Order.java 클래스의 메소드 구현 
```
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
```
- Payment.java 클래스의 메소드 구현 
```
public static void payCancel(OrderCanceled orderCanceled){
        
        repository().findById(Long.valueOf(orderCanceled.getId())).ifPresent(payment->{
            
            repository().delete(payment);
            
            PaymentCanceled paymentCanceled = new PaymentCanceled(payment);
            paymentCanceled.publishAfterCommit();

         });      
    }
```
- 실행결과 
```
gitpod /workspace/DeliveryStore11 (main) $ http POST http://localhost:8081/orders foodId="짬뽕" address="서울 용산구 한남동" customerId="song" qty=1 price="9000"  storeId="1"
HTTP/1.1 201 
Connection: keep-alive
Content-Type: application/json
Date: Sat, 10 Dec 2022 17:37:58 GMT
Keep-Alive: timeout=60
Location: http://localhost:8081/orders/1
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_links": {
        "order": {
            "href": "http://localhost:8081/orders/1"
        },
        "self": {
            "href": "http://localhost:8081/orders/1"
        }
    },
    "address": "서울 용산구 한남동",
    "customerId": "song",
    "foodId": "짬뽕",
    "price": 9000,
    "qty": 1,
    "storeId": "1"
}


gitpod /workspace/DeliveryStore11 (main) $ http GET localhost:8081/orders
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Sat, 10 Dec 2022 17:38:06 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "orders": [
            {
                "_links": {
                    "order": {
                        "href": "http://localhost:8081/orders/1"
                    },
                    "self": {
                        "href": "http://localhost:8081/orders/1"
                    }
                },
                "address": "서울 용산구 한남동",
                "customerId": "song",
                "foodId": "짬뽕",
                "price": 9000,
                "qty": 1,
                "storeId": "1"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8081/profile/orders"
        },
        "self": {
            "href": "http://localhost:8081/orders"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}


gitpod /workspace/DeliveryStore11 (main) $ http GET localhost:8082/payments
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Sat, 10 Dec 2022 17:38:12 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "payments": [
            {
                "_links": {
                    "pay": {
                        "href": "http://localhost:8082/payments/1/pay"
                    },
                    "payment": {
                        "href": "http://localhost:8082/payments/1"
                    },
                    "self": {
                        "href": "http://localhost:8082/payments/1"
                    }
                },
                "amount": "9000",
                "customerId": "song",
                "orderId": "1",
                "status": "주문-결제요청중"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8082/profile/payments"
        },
        "search": {
            "href": "http://localhost:8082/payments/search"
        },
        "self": {
            "href": "http://localhost:8082/payments"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}
```
주문 1건을 취소하여 Order 서비스와 Payment 서비스를 조회한다.
- order 서비스에서는 삭제되었고 Payment서비스에선 주문취소로 조회된다.
```
gitpod /workspace/DeliveryStore11 (main) $ http DELETE "http://localhost:8081/orders/1"
HTTP/1.1 204 
Connection: keep-alive
Date: Sat, 10 Dec 2022 17:38:18 GMT
Keep-Alive: timeout=60
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers



gitpod /workspace/DeliveryStore11 (main) $ http GET localhost:8081/orders
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Sat, 10 Dec 2022 17:38:23 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "orders": []
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8081/profile/orders"
        },
        "self": {
            "href": "http://localhost:8081/orders"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 0,
        "totalPages": 0
    }
}


gitpod /workspace/DeliveryStore11 (main) $ http GET localhost:8082/payments
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Sat, 10 Dec 2022 17:38:28 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "payments": [
            {
                "_links": {
                    "pay": {
                        "href": "http://localhost:8082/payments/2/pay"
                    },
                    "payment": {
                        "href": "http://localhost:8082/payments/2"
                    },
                    "self": {
                        "href": "http://localhost:8082/payments/2"
                    }
                },
                "amount": null,
                "customerId": null,
                "orderId": "1",
                "status": "주문취소"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8082/profile/payments"
        },
        "search": {
            "href": "http://localhost:8082/payments/search"
        },
        "self": {
            "href": "http://localhost:8082/payments"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}
```

## 4. Request / Response
OrderPlaced 이벤트가 발생시 pay command 로 원격 호출(Request/Response) 방식으로 구현하였다.

- Order.java 클래스의 onPostPersist 메소드 구현
```
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

        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

    }
    
```
- Payment.java 클래스의 pay 메소드 구현
```
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
```

- 서비스 테스트 
```
Order 서비스에서 주문요청시 Payment 서비스가 비정상이면 주문신청이 되지 않는다.
gitpod /workspace/DeliveryStore11 (main) $ http POST http://localhost:8081/orders foodId="짬뽕" address="서울 용산구 한남동" customerId="song" qty=1 price="9000"  storeId="1"
HTTP/1.1 500 
Connection: close
Content-Type: application/json
Date: Sat, 10 Dec 2022 18:04:02 GMT
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "error": "Internal Server Error",
    "message": "",
    "path": "/orders",
    "status": 500,
    "timestamp": "2022-12-10T18:04:02.800+00:00"
}
```

## 5. Circuit Breaker

## 6. Gateway / Ingress
...


