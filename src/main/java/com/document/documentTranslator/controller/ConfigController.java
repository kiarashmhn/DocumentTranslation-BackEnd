package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.ConfigDto;
import com.document.documentTranslator.dto.DocumentDto;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Config.ConfigService;
import com.document.documentTranslator.service.Document.DocumentService;
import com.document.documentTranslator.util.DomainUtil;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private DocumentService documentService;

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN)
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody ConfigDto configDto) throws DomainException {
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, configService.createOrUpdate(configDto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/createFile", headers = ("content-type=multipart/*"), produces = "application/json")
    public ResponseEntity<Response> create(@FormDataParam("file") MultipartFile file, @RequestParam("data") String data) throws DomainException, IOException {

        Map<String, Object> map = DomainUtil.jsonStringtoMap(data);

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, documentService.createConfigDoc(file, DocumentDto.fromMap(map)),
                true, null));
    }

    @PostMapping("/get")
    public ResponseEntity<Response> get() {
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, configService.get().map(),
                true, null));
    }
}
