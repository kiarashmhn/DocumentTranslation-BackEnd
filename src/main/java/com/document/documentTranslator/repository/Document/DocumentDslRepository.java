package com.document.documentTranslator.repository.Document;

import com.document.documentTranslator.dto.DocumentDto;
import com.document.documentTranslator.entity.Document;
import com.document.documentTranslator.repository.Base.DslRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentDslRepository extends DslRepository<Document, DocumentDto> {
}
