package com.document.documentTranslator.repository.Document;

import com.document.documentTranslator.dto.DocumentDto;
import com.document.documentTranslator.entity.Document;
import com.document.documentTranslator.entity.QDocument;
import com.document.documentTranslator.util.Validator;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

public class DocumentDslRepositoryImpl implements DocumentDslRepository {
    @Autowired
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public List<Document> getAll(DocumentDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetch();
    }

    @Override
    public long getAllCount(DocumentDto dto, int begin, int length) {
        return createGetAllQuery(dto, begin, length).fetchCount();
    }

    private JPAQuery<Document> createGetAllQuery(DocumentDto dto, int begin, int length) {

        JPAQuery<Document> jpaQuery = new JPAQuery<>(entityManager);
        QDocument qDocument = QDocument.document;
        JPAQuery<Document> query = jpaQuery.from(qDocument).where(qDocument.enable.eq(Boolean.TRUE)).orderBy(qDocument.creationTime.desc());

        if (Validator.notNull(dto.getUsername()))
            query.where(qDocument.username.eq(dto.getUsername()));

        if (Validator.notNull(dto.getOrderId()))
            query.where(qDocument.orderId.eq(dto.getOrderId()));

        if (Validator.notNull(dto.getName()))
            query.where(qDocument.name.eq(dto.getName()));


        begin = Validator.isNull(begin) ? 0 : begin;
        length = Validator.isNull(length) ? 100 : length;

        query.limit(length);
        query.offset(begin);
        return query;
    }
}
