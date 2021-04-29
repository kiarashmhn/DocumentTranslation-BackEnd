package com.document.documentTranslator.service.Message;

import com.document.documentTranslator.dto.MessageDto;
import com.document.documentTranslator.entity.Message;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.enums.MessageSender;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Message.MessageRepository;
import com.document.documentTranslator.repository.Order.OrderRepository;
import com.document.documentTranslator.service.Order.OrderService;
import com.document.documentTranslator.service.User.UserService;
import com.document.documentTranslator.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order validateAndFindOrder(Long orderId) throws DomainException {

        User user = userService.getCurrentUser();
        Order order = orderService.findById(orderId);
        if (userService.isSuperAdmin(user) || user.getUsername().equals(order.getUsername()))
            return order;
        throw new DomainException(ErrorMessage.ACCESS_DENIED);
    }

    public Message create(MessageDto dto) throws DomainException {

        if (Validator.isNull(dto))
            throw new DomainException(ErrorMessage.INVALID_INPUT);
        dto.validate();

        Order order = validateAndFindOrder(dto.getOrderId());

        Message message = new Message();
        message.setText(dto.getText());
        message.setHasFile(dto.getHasFile());
        message.setOrderId(dto.getOrderId());
        message.setSender(dto.getSender());

        messageRepository.save(message);

        if (dto.getSender().equals(MessageSender.ADMIN))
            order.setHasNewAdminMessage(Boolean.TRUE);
        else order.setHasNewUserMessage(Boolean.TRUE);
        orderRepository.save(order);

        return message;
    }


    public List<Message> getMessages(MessageDto messageDto) throws DomainException {
        if (Validator.isNull(messageDto) || Validator.isNull(messageDto.getOrderId()))
            throw new DomainException(ErrorMessage.INVALID_INPUT);

        Order order = validateAndFindOrder(messageDto.getOrderId());
        if (messageDto.getSender().equals(MessageSender.ADMIN))
            order.setHasNewUserMessage(Boolean.FALSE);
        else order.setHasNewAdminMessage(Boolean.FALSE);
        orderRepository.save(order);

        return messageRepository.findByOrderId(messageDto.getOrderId());
    }
}
