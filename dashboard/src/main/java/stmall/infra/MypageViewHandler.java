package stmall.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import stmall.config.kafka.KafkaProcessor;
import stmall.domain.*;

@Service
public class MypageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1(
        @Payload OrderPlaced orderPlaced
    ) {
        try {
            if (!orderPlaced.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setMypageId(orderPlaced.getId());
            mypage.setCustomerId(orderPlaced.getCustomerid());
            mypage.setItemId(orderPlaced.getItemId());
            mypage.setQty(orderPlaced.getQty());
            mypage.setAddress(orderPlaced.getAddress());
            mypage.setOrderStatus(ORDERPLACED);
            // view 레파지 토리에 save
            mypageRepository.save(mypage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCompleted_then_UPDATE_1(
        @Payload DeliveryCompleted deliveryCompleted
    ) {
        try {
            if (!deliveryCompleted.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByItemId(
                deliveryCompleted.getItemId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCanceled_then_UPDATE_2(
        @Payload OrderCanceled orderCanceled
    ) {
        try {
            if (!orderCanceled.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByOrderId(
                String.valueOf(orderCanceled.getId())
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCanceled_then_UPDATE_3(
        @Payload OrderCanceled orderCanceled
    ) {
        try {
            if (!orderCanceled.validate()) return;
            // view 객체 조회

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
