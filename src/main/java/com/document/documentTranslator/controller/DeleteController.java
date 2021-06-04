package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.dto.UserDto;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Delete.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
public class DeleteController {

    @Autowired
    private DeleteService deleteService;

    @PostMapping("/user")
    public ResponseEntity<Response> deleteUser(@RequestBody UserDto dto) throws DomainException {

        deleteService.deleteUser(dto);
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, null,
                true, null));
    }

    @PostMapping("/order")
    public ResponseEntity<Response> deleteOrder(@RequestBody OrderDto dto) throws DomainException {

        deleteService.deleteOrder(dto);
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, null,
                true, null));
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN, injectUserName = false)
    @PostMapping("/order/expired")
    public ResponseEntity<Response> deleteExpiredOrders() throws DomainException {

        deleteService.deleteExpiredOrders();
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, null,
                true, null));
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN, injectUserName = false)
    @PostMapping("/order/expiredIds")
    public ResponseEntity<Response> getExpiredOrderIds() {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, deleteService.getExpiredOrderIds(),
                true, null));
    }
}
