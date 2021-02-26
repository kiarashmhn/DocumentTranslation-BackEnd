package com.document.documentTranslator.repository.Payment;

import com.document.documentTranslator.dto.PaymentDto;
import com.document.documentTranslator.entity.Payment;
import com.document.documentTranslator.entity.QPayment;
import com.document.documentTranslator.util.Validator;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

public class PaymentDslRepositoryImpl implements PaymentDslRepository {

    @Autowired
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public List<Payment> getAll(PaymentDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetch();
    }

    @Override
    public long getAllCount(PaymentDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetchCount();
    }

    private JPAQuery<Payment> createGetAllQuery(PaymentDto dto, int begin, int length) {

        JPAQuery<Payment> jpaQuery = new JPAQuery<>(entityManager);
        QPayment qPayment = QPayment.payment;
        JPAQuery<Payment> query = jpaQuery.from(qPayment).where(qPayment.enable.eq(Boolean.TRUE)).orderBy(qPayment.creationTime.desc());

        if (Validator.notNull(dto.getOrderId()))
            query.where(qPayment.orderId.eq(dto.getOrderId()));

        if (Validator.notNull(dto.getMethod()))
            query.where(qPayment.method.eq(dto.getMethod()));


        begin = Validator.isNull(begin) ? 0 : begin;
        length = Validator.isNull(length) ? 100 : length;

        query.limit(length);
        query.offset(begin);
        return query;
    }
}
