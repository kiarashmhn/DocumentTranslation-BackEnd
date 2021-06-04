package com.document.documentTranslator.service.Payment;

import com.document.documentTranslator.dto.PaymentDto;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.entity.Payment;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.enums.OrderStatus;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Order.OrderRepository;
import com.document.documentTranslator.repository.Payment.PaymentRepository;
import com.document.documentTranslator.service.Order.OrderService;
import com.document.documentTranslator.service.User.UserService;
import com.document.documentTranslator.util.DomainUtil;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    public List<Payment> getAll(PaymentDto dto) {
        return paymentRepository.getAll(dto, DomainUtil.getBegin(dto), DomainUtil.getLength(dto));
    }

    public Payment create(PaymentDto dto) throws DomainException {
        dto.validate();

        Payment payment = new Payment();

        User user = userService.findByUserName(dto.getUsername());
        payment.setUsername(user.getUsername());
        payment.setAmount(dto.getAmount());
        payment.setMethod(dto.getMethod());
        payment.setDeliveryType(dto.getDeliveryType());
        payment.setCode(dto.getCode());

        Order order = orderService.findById(dto.getOrderId());
        payment.setOrderId(order.getId());
        order.setPaid(Boolean.TRUE);
        order.setStatus(OrderStatus.PENDING);
        order.setLastModifiedDate(new Date());
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

    public CreatePaymentResponse stripePay(PaymentDto dto) throws StripeException {

        Stripe.apiKey = "sk_test_51IMcfSDJralPixYMg2IOYoq4cl60vm2NblpT0STkp8DUkt0rmOwBFeqtcCuiftco9H4oABC85kIFRNw04wfjOZ0L00Vn3l8l8m";

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("EUR")
                .setAmount(dto.getAmount() * 100)
                .build();
        PaymentIntent intent = PaymentIntent.create(createParams);
        return new CreatePaymentResponse(intent.getClientSecret());
    }
}
