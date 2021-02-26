package com.document.documentTranslator.repository.Payment;

import com.document.documentTranslator.entity.Payment;
import com.document.documentTranslator.repository.Base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<Payment>, PaymentDslRepository {
}
