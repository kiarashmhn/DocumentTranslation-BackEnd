package com.document.documentTranslator.controller;

import com.document.documentTranslator.aspect.Authorize;
import com.document.documentTranslator.dto.UserDto;
import com.document.documentTranslator.enums.ResponseMessages;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.response.Response;
import com.document.documentTranslator.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN, injectUserName = false)
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody UserDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, userService.createAdmin(dto),
                true, null));
    }
}
