package com.document.documentTranslator.repository.Order;

import com.document.documentTranslator.dto.OrderDto;
import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.repository.Base.DslRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDslRepository extends DslRepository<Order, OrderDto> {
}
