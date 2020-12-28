package com.document.documentTranslator.repository.Document;

import com.document.documentTranslator.entity.Document;
import com.document.documentTranslator.repository.Base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends BaseRepository<Document>, DocumentDslRepository {
}
