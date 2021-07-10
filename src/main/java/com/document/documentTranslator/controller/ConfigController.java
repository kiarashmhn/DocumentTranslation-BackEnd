package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.ConfigDto;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN)
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody ConfigDto configDto) throws DomainException {
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, configService.createOrUpdate(configDto),
                true, null));
    }

    @PostMapping("/get")
    public ResponseEntity<Response> get() {
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, configService.get().map(),
                true, null));
    }
}
