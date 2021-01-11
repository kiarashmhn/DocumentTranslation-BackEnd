package com.document.documentTranslator.service.User;

import com.document.documentTranslator.dto.UserDto;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.User.UserBasicRepository;
import com.document.documentTranslator.util.Validator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserBasicRepository userBasicRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserBasicRepository userBasicRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userBasicRepository = userBasicRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public User getCurrentUser() throws DomainException {
        return findByUserName(getCurrentUsername());
    }

    public Boolean isAdmin(User user) {
        if (Validator.notNull(user.getLevel()))
            return user.getLevel() >= 1;
        return false;
    }

    public Boolean isSuperAdmin(User user) {
        if (Validator.notNull(user.getLevel()))
            return user.getLevel() >= 2;
        return false;
    }

    public User findByUserName(String username) throws DomainException {
        User user = this.userBasicRepository.findUserByUsername(username);
        if (Validator.isNull(user))
            throw new DomainException(String.format(ErrorMessage.INVALID_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.INVALID_PARAMETER);
        return user;
    }

    public User validateAdminByName(String username) throws DomainException {
        User user = findByUserName(username);
        if (!user.getLevel().equals(1L))
            throw new DomainException(ErrorMessage.NOT_ADMIN);
        return user;
    }

    public User create(UserDto userDto) throws DomainException {

        userDto.validate();
        User oldUser = this.userBasicRepository.findUserByUsername(userDto.getUsername());
        if (Validator.notNull(oldUser))
            throw new DomainException(ErrorMessage.USER_ALREADY_EXISTS);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setLevel(userDto.getLevel());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());

        this.userBasicRepository.save(user);
        return user;
    }

    public UserDto createUser(UserDto userDto) throws DomainException {
        userDto.setLevel(0L);
        User user = create(userDto);
        UserDto dto = new UserDto(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), user.getLevel(), userDto.getPhone());
        dto.setToken(getJWTToken(userDto.getUsername()));
        return dto;
    }

    public UserDto login(UserDto userDto) throws DomainException {
        userDto.validate();
        User user = findByUserName(userDto.getUsername());
        if (Validator.isNull(user))
            throw new DomainException(String.format(ErrorMessage.NOT_FOUND.getFarsiMessage(), "نام کاربری"), ErrorMessage.NOT_FOUND);
        if (!bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword()))
            throw new DomainException(String.format(ErrorMessage.INVALID_PARAMETER.getFarsiMessage(), "نام کاربری یا رمز عبور"), ErrorMessage.INVALID_PARAMETER);

        userDto.setLevel(user.getLevel());
        userDto.setEmail(user.getEmail());
        userDto.setToken(getJWTToken(userDto.getUsername()));
        return userDto;

    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("galaxyWebApp")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10800000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public UserDto createAdmin(UserDto dto) throws DomainException {
        dto.setLevel(1L);
        User user = create(dto);
        return new UserDto(dto.getUsername(), dto.getPassword(), dto.getEmail(), user.getLevel(), dto.getPhone());
    }
}
