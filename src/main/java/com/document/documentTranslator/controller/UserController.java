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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Response> register(@RequestBody UserDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, userService.createUser(dto),
                true, null));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Response> login(@RequestBody UserDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, userService.login(dto),
                true, null));
    }

    @PostMapping(value = "/recoverPass", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Response> recoverPassword(@RequestBody UserDto dto) throws DomainException {

        userService.recoverPassword(dto);
        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, null,
                true, null));
    }

    @Authorize(type = Authorize.AAAType.SUPER_ADMIN, injectUserName = false)
    @PostMapping("/gets")
    public ResponseEntity<Response> gets(@RequestBody UserDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, userService.getAll(dto),
                true, userService.getAllCount(dto)));
    }

    @Authorize(type = Authorize.AAAType.USER)
    @PostMapping("/get")
    public ResponseEntity<Response> get() throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, userService.getUserMap(),
                true, null));
    }

    @Authorize(type = Authorize.AAAType.USER, injectUserName = false)
    @PostMapping("/update")
    public ResponseEntity<Response> update(@RequestBody UserDto dto) throws DomainException {

        return ResponseEntity.ok().body(new Response(ResponseMessages.SUCCESSFUL, userService.updateUser(dto),
                true, null));
    }
}
