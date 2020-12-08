package com.document.documentTranslator.service.Order;

import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.enums.OrderStatus;
import com.document.documentTranslator.enums.OrderType;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Order.OrderRepository;
import com.document.documentTranslator.util.DomainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderDto orderDto) throws DomainException {
        orderDto.validate();
        Order order = new Order();
        order.setUsername(orderDto.getUsername());
        order.setType(OrderType.lookupByName(orderDto.getType()));
        order.setStatus(OrderStatus.PENDING);
        order.setDetails(DomainUtil.objectToString(orderDto.getDetails()));

        orderRepository.save(order);
        return order;
    }
}
