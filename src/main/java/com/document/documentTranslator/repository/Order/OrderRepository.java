package com.document.documentTranslator.repository.Order;

import com.document.documentTranslator.entity.Order;
import com.document.documentTranslator.repository.Base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order>, OrderDslRepository {
}
