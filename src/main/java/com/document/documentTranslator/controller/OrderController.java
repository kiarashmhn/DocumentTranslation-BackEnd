package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.createOrUpdate(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping("/getById")
    public ResponseEntity<Response> getById(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.getById(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER, injectUserName = false)
    @PostMapping("/gets")
    public ResponseEntity<Response> gets(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.getOrders(dto),
                true, orderService.getAllCount(dto)));
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN)
    @PostMapping("/assign")
    public ResponseEntity<Response> assign(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.assignOrderToAdmin(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN)
    @PostMapping("/unAssign")
    public ResponseEntity<Response> unAssign(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.unAssignOrder(dto),
                true, null));
    }
}
