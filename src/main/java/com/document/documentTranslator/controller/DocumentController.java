package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.DocumentDto;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.Document.DocumentService;
import com.document.documentTranslator.util.DomainUtil;
import com.document.documentTranslator.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping(value = "/document", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/create", headers = ("content-type=multipart/*"), produces = "application/json")
    public ResponseEntity<Response> create(@FormDataParam("file") MultipartFile file, @RequestParam("data") String data) throws DomainException, IOException {

        Map<String, Object> map = DomainUtil.jsonStringtoMap(data);

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
    @PostMapping(value = "/getDocument")
    public ResponseEntity<Response> getDocument(@RequestBody DocumentDto dto) throws DomainException {

        if (Validator.notNull(dto) && Validator.notNull(dto.getId()))
            return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, documentService.findById(dto.getId()),
                    true, null));
        return ResponseEntity.badRequest().build();
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/get")
    public void get(@RequestBody DocumentDto dto, HttpServletResponse response) throws DomainException {


        try {
            File file = documentService.getFileById(dto);
            InputStream in = new FileInputStream(file);
            StreamUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new DomainException(ErrorMessage.INTERNAL_ERROR);
        }

    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping(value = "/delete")
    public ResponseEntity<Response> delete(@RequestBody DocumentDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, documentService.deleteById(dto),
                true, null));
    }
}
