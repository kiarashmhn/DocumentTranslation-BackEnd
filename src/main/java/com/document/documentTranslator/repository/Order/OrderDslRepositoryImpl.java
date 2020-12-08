package com.document.documentTranslator.repository.Order;

import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.entity.QOrder;
import com.document.documentTranslator.enums.OrderStatus;
import com.document.documentTranslator.enums.OrderType;
import com.document.documentTranslator.util.DomainUtil;
import com.document.documentTranslator.util.Validator;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

public class OrderDslRepositoryImpl implements OrderDslRepository {

    @Autowired
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public List<Order> getAll(OrderDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetch();
    }

    @Override
    public long getAllCount(OrderDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetchCount();
    }

    private JPAQuery<Order> createGetAllQuery(OrderDto dto, int begin, int length) {

        JPAQuery<Order> jpaQuery = new JPAQuery<>(entityManager);
        QOrder qOrder = QOrder.order;
        JPAQuery<Order> query = jpaQuery.from(qOrder).where(qOrder.enable.eq(Boolean.TRUE)).orderBy(qOrder.creationTime.desc());

        if (Validator.notNull(dto.getUsername()))
            query.where(qOrder.username.eq(dto.getUsername()));

        if (Validator.notNull(dto.getStatus()))
            query.where(qOrder.status.eq(OrderStatus.lookupByName(dto.getStatus())));

        if (Validator.notNull(dto.getType()))
            query.where(qOrder.type.eq(OrderType.lookupByName(dto.getType())));

        if (Validator.notNull(dto.getDetails()))
            query.where(qOrder.details.contains(DomainUtil.mapToTrimmedString(dto.getDetails())));


        begin = Validator.isNull(begin) ? 0 : begin;
        length = Validator.isNull(length) ? 100 : length;

        query.limit(length);
        query.offset(begin);
        return query;
    }
}
