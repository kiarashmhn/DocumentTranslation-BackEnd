package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.dto.PaymentDto;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Order.OrderService;
import com.document.documentTranslator.service.Payment.PaymentService;
import com.stripe.exception.StripeException;
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
    private PaymentService paymentService;

    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
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
    @PostMapping("/verifyPayment")
    public ResponseEntity<Response> verifyPayment(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.verifyPayment(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN)
    @PostMapping("/unVerifyPayment")
    public ResponseEntity<Response> unVerifyPayment(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.unVerifyPayment(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN)
    @PostMapping("/unAssign")
    public ResponseEntity<Response> unAssign(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.unAssignOrder(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER, injectUserName = false)
    @PostMapping("/pay")
    public ResponseEntity<Response> pay(@RequestBody PaymentDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, paymentService.create(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER, injectUserName = false)
    @PostMapping("/stripePayment")
    public ResponseEntity<Response> stripePayment(@RequestBody PaymentDto dto) throws DomainException, StripeException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, paymentService.stripePay(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER, injectUserName = false)
    @PostMapping("/getPayments")
    public ResponseEntity<Response> getPayments(@RequestBody PaymentDto dto) {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, paymentService.getAll(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.ADMIN, injectUserName = false)
    @PostMapping("/done")
    public ResponseEntity<Response> done(@RequestBody OrderDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, orderService.orderDone(dto),
                true, null));
    }
}
