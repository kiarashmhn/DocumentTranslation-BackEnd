package com.document.documentTranslator.service.User;

import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.dto.UserDto;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Order.OrderRepository;
import com.document.documentTranslator.repository.User.UserRepository;
import com.document.documentTranslator.service.Order.OrderService;
import com.document.documentTranslator.util.DomainUtil;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private OrderRepository orderRepository;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.orderRepository = orderRepository;
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
        User user = this.userRepository.findUserByUsername(username);
        if (Validator.isNull(user))
            throw new DomainException(String.format(ErrorMessage.INVALID_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.INVALID_PARAMETER);
        return user;
    }

    public User getUser(UserDto dto) throws DomainException {
        if (Validator.isNull(dto) || Validator.isNull(dto.getUsername()))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.EMPTY_PARAMETER);

        User user = this.userRepository.findUserByUsername(dto.getUsername());
        if (Validator.isNull(user))
            throw new DomainException(String.format(ErrorMessage.INVALID_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.INVALID_PARAMETER);
        return user;
    }

    public List<User> getAdmins() {
        return userRepository.findAllByLevelInAndEnable(Arrays.asList(1L, 2L), Boolean.TRUE);
    }

    public User updateUser(UserDto dto) throws DomainException {

        User user = getUser(dto);
        User current = getCurrentUser();

        if (!user.equals(current) && !isAdmin(current))
            throw new DomainException(ErrorMessage.ACCESS_DENIED);

        if (Validator.notNull(dto.getPassword()))
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        if (Validator.notNull(dto.getEmail())) {
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getEmail());
        }
        if (Validator.notNull(dto.getPhone()))
            user.setPhone(dto.getPhone());

        this.userRepository.save(user);
        return user;
    }

    public User validateAdminByName(String username) throws DomainException {
        User user = findByUserName(username);
        if (!user.getLevel().equals(1L) && !user.getLevel().equals(2L))
            throw new DomainException(ErrorMessage.NOT_ADMIN);
        return user;
    }

    public User create(UserDto userDto) throws DomainException {

        userDto.validate();
        userDto.registerValidate();
        User oldUser = this.userRepository.findUserByUsername(userDto.getUsername());
        if (Validator.notNull(oldUser))
            throw new DomainException(ErrorMessage.USER_ALREADY_EXISTS);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setLevel(userDto.getLevel());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());

        this.userRepository.save(user);
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

    public List<Map<String, Object>> getAll(UserDto dto) throws DomainException {
        if (Validator.isNull(dto))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.EMPTY_PARAMETER);

        return DomainUtil.toMapList(userRepository.getAll(dto, DomainUtil.getBegin(dto), DomainUtil.getLength(dto)), DomainUtil.getBegin(dto));
    }

    public Long getAllCount(UserDto dto) {
        return userRepository.getAllCount(dto, DomainUtil.getBegin(dto), DomainUtil.getLength(dto));
    }

    public Map<String, Object> getUserMap() throws DomainException {
        User user = getCurrentUser();
        Map<String, Object> map = user.map();
        map.put("hasNewMessage", hasNewMessage(user));
        return map;
    }

    public boolean hasNewMessage(User user) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUsername(user.getUsername());
        List<Order> orders = orderRepository.getAll(orderDto, 1, 1000);
        for (Order order : orders)
            if (order.getHasNewAdminMessage())
                return true;
        return false;
    }
}
