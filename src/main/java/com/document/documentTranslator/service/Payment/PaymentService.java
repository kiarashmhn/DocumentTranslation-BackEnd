package com.document.documentTranslator.service.Payment;

import com.document.documentTranslator.dto.PaymentDto;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.entity.Payment;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Payment.PaymentRepository;
import com.document.documentTranslator.service.Order.OrderService;
import com.document.documentTranslator.service.User.UserService;
import com.document.documentTranslator.util.DomainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    public List<Payment> getAll(PaymentDto dto) {
        return paymentRepository.getAll(dto, DomainUtil.getBegin(dto), DomainUtil.getLength(dto));
    }

    public Payment create(PaymentDto dto) throws DomainException {
        dto.validate();

        Payment payment = new Payment();

        User user = userService.findByUserName(dto.getUsername());
        payment.setUsername(user.getUsername());

        payment.setMethod(dto.getMethod());

        Order order = orderService.findById(dto.getOrderId());
        payment.setOrderId(order.getId());

        return paymentRepository.save(payment);
    }
}
