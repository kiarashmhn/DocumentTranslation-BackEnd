package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.DocumentDto;
import com.document.documentTranslator.entity.Document;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Document.DocumentService;
import com.document.documentTranslator.util.DomainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/create", headers = ("content-type=multipart/*"), produces = "application/json")
    public ResponseEntity<Response> create(@FormDataParam("file") MultipartFile file, @RequestParam("data") String data) throws DomainException, IOException {

        Map<String , Object> map = DomainUtil.jsonStringtoMap(data);

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, documentService.create(file, DocumentDto.fromMap(map)),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/gets")
    public ResponseEntity<Response> gets(@RequestBody DocumentDto dto) {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, documentService.getAll(dto),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/get")
    public ResponseEntity<Object> get(@RequestBody DocumentDto dto) throws DomainException {

        Document document = documentService.findById(dto.getId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                .body(document.getData());
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/delete")
    public ResponseEntity<Response> delete(@RequestBody DocumentDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, documentService.deleteById(dto),
                true, null));
    }
}
