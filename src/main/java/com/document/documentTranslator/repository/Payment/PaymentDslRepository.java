package com.document.documentTranslator.repository.Payment;

import com.document.documentTranslator.dto.PaymentDto;
import com.document.documentTranslator.entity.Payment;
import com.document.documentTranslator.repository.Base.DslRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDslRepository extends DslRepository<Payment, PaymentDto> {
}
