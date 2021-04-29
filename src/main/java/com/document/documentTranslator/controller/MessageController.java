package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.MessageDto;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/create")
    public ResponseEntity<Response> create(@RequestBody MessageDto dto) throws DomainException {
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, messageService.create(dto), true, null));
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping("/gets")
    public ResponseEntity<Response> gets(@RequestBody MessageDto dto) throws DomainException {
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, messageService.getMessages(dto), true, null));
    }
}
