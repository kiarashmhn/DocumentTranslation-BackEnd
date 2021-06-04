package com.document.documentTranslator.repository.User;

import com.document.documentTranslator.dto.UserDto;
import com.document.documentTranslator.entity.QUser;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.util.Validator;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

public class UserDslRepositoryImpl implements UserDslRepository {

    @Autowired
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public List<User> getAll(UserDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetch();
    }

    @Override
    public long getAllCount(UserDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetchCount();
    }

    private JPAQuery<User> createGetAllQuery(UserDto dto, int begin, int length) {

        JPAQuery<User> jpaQuery = new JPAQuery<>(entityManager);
        QUser qUser = QUser.user;
        JPAQuery<User> query = jpaQuery.from(qUser).where(qUser.enable.eq(Boolean.TRUE).or(qUser.enable.eq(Boolean.FALSE))).orderBy(qUser.creationTime.desc());

        if (Validator.notNull(dto.getUsername()))
            query.where(qUser.username.eq(dto.getUsername()));

        if (Validator.notNull(dto.getEnabled()))
            query.where(qUser.enable.eq(dto.getEnabled()));

        if (Validator.notNull(dto.getEmail()))
            query.where(qUser.email.eq(dto.getEmail()));

        if (Validator.notNull(dto.getPhone()))
            query.where(qUser.phone.eq(dto.getPhone()));

        if (Validator.notNull(dto.getLevel()))
            query.where(qUser.level.eq(dto.getLevel()));

        if (Validator.notNull(dto.getLastLogin()))
            query.where(qUser.lastLogin.lt(dto.getLastLogin()));

        begin = Validator.isNull(begin) ? 0 : begin;
        length = Validator.isNull(length) ? 100 : length;

        query.limit(length);
        query.offset(begin);
        return query;
    }
}
