package com.document.documentTranslator.service.Delete;

import com.document.documentTranslator.dto.DocumentDto;
import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.dto.PaymentDto;
import com.document.documentTranslator.dto.UserDto;
import com.document.documentTranslator.entity.Document;
import com.document.documentTranslator.entity.Message;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.entity.Payment;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.enums.OrderStatus;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Document.DocumentRepository;
import com.document.documentTranslator.repository.Message.MessageRepository;
import com.document.documentTranslator.repository.Order.OrderRepository;
import com.document.documentTranslator.repository.Payment.PaymentRepository;
import com.document.documentTranslator.repository.User.UserRepository;
import com.document.documentTranslator.service.Order.OrderService;
import com.document.documentTranslator.service.User.UserService;
import com.document.documentTranslator.util.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeleteService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    Logger logger = Logger.getLogger(DeleteService.class);

    public void deleteUser(UserDto dto) throws DomainException {
        if (Validator.isNull(dto) || Validator.isNull(dto.getUsername()))
            throw new DomainException(ErrorMessage.INVALID_INPUT);

        User user = userService.findByUserName(dto.getUsername());
        User current = userService.getCurrentUser();
        if (!userService.isSuperAdmin(user) && !user.getUsername().equals(current.getUsername()))
            throw new DomainException(ErrorMessage.ACCESS_DENIED);

        user.setEnable(false);
        userRepository.save(user);

        OrderDto orderDto = new OrderDto();
        orderDto.setUsername(user.getUsername());
        List<Order> orders = orderRepository.getAll(orderDto, 1, 1000);
        for (Order order : orders)
            deleteOrderById(order.getId());

    }

    public void deleteOrderById(Long id) throws DomainException {

        deleteOrder(id);
        deleteMessagesByOrder(id);
        deleteDocumentsByOrder(id);
        deletePaymentByOrder(id);
        deleteFiles(id);
    }

    public void deleteFiles(Long id) {
        try {
            FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + "/files/" + id + "/"));
        } catch (Exception e) {
            logger.info("", e);
        }
    }

    public void deleteOrder(OrderDto orderDto) throws DomainException {
        if (Validator.isNull(orderDto) || Validator.isNull(orderDto.getId()))
            throw new DomainException(ErrorMessage.INVALID_INPUT);
        deleteOrderById(orderDto.getId());
    }

    private void deleteOrder(Long id) throws DomainException {
        Order order = orderService.findById(id);
        User user = userService.getCurrentUser();
        if (!userService.isSuperAdmin(user) && !order.getUsername().equals(user.getUsername()))
            throw new DomainException(ErrorMessage.ACCESS_DENIED);
        order.setEnable(Boolean.FALSE);
        orderRepository.save(order);
    }

    private void deletePaymentByOrder(Long id) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setOrderId(id);
        List<Payment> payments = paymentRepository.getAll(paymentDto, 0, 1000);
        for (Payment payment : payments) {
            payment.setEnable(false);
            paymentRepository.save(payment);
        }
    }

    private void deleteDocumentsByOrder(Long id) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setOrderId(id);
        List<Document> documents = documentRepository.getAll(documentDto, 0, 1000);
        for (Document document : documents) {
            document.setEnable(false);
            documentRepository.save(document);
        }
    }

    private void deleteMessagesByOrder(Long id) {
        List<Message> messages = messageRepository.findByOrderId(id);
        for (Message message : messages) {
            message.setEnable(false);
            messageRepository.save(message);
        }
    }

    public List<String> getExpiredOrderIds() {
        return getExpiredOrders().stream().map(o -> o.getType() + o.getId()).collect(Collectors.toList());
    }

    public Set<Order> getExpiredOrders() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -2);

        OrderDto dto = new OrderDto();
        dto.setFromDate(c.getTime());
        dto.setStatuses(Arrays.asList(OrderStatus.COMPLETING, OrderStatus.WAITING_FOR_PAYMENT));
        HashSet<Order> all = new HashSet<>(orderRepository.getAll(dto, 0, 1000));

        c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -12);
        dto.setFromDate(c.getTime());
        dto.setStatuses(null);
        all.addAll(orderRepository.getAll(dto, 0, 1000));
        return all;
    }

    public void deleteExpiredOrders() throws DomainException {
        for (Order order : getExpiredOrders())
            deleteOrderById(order.getId());
    }
}
