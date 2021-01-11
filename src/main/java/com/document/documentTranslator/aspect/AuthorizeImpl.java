package com.document.documentTranslator.aspect;

import com.document.documentTranslator.dto.BaseDto;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.service.User.UserService;
import com.document.documentTranslator.util.Validator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Aspect
@Order(20)
public class AuthorizeImpl {

    @Autowired
    private UserService userService;

    @Pointcut("@annotation(com.document.documentTranslator.aspect.Authorize))")
    protected void authorization() {

        // For defining the Point Cut with annotation
    }

    @Around("authorization()")
    public Object authenticationService(ProceedingJoinPoint joinPoint) throws Throwable {

        Authorize annotation = getAspectAnnotation(joinPoint);
        Authorize.AAAType authenticationType = annotation.type();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] methodAnnotations = method.getParameterAnnotations();

        User user = userService.getCurrentUser();

        switch (authenticationType) {
            case ADMIN:
                if (!userService.isAdmin(user))
                    throw new DomainException(ErrorMessage.ACCESS_DENIED);
                break;
            case SUPER_ADMIN:
                if (!userService.isSuperAdmin(user))
                    throw new DomainException(ErrorMessage.ACCESS_DENIED);
                break;
            default:
                break;
        }

        if (annotation.injectUserName()) {
            BaseDto dto = getInputDto(methodAnnotations, joinPoint.getArgs());
            if (Validator.notNull(dto))
                dto.setUsername(user.getUsername());
        }
        return joinPoint.proceed();
    }

    public BaseDto getInputDto(Annotation[][] methodAnnotations, Object[] parameters) {

        if (!Validator.isNull(methodAnnotations)){

            for (Annotation[] annotations : methodAnnotations){
                int i = 0;
                for (Annotation annotation : annotations){
                    if (Validator.notNull(annotation) && annotation.annotationType().equals(RequestBody.class)){
                        return (BaseDto) parameters[i];
                    }
                    i++;
                }
            }
        }
        return null;
    }

    private Authorize getAspectAnnotation(ProceedingJoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        return method.getAnnotation(Authorize.class);
    }

}
