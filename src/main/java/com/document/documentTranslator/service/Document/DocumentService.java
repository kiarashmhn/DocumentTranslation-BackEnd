package com.document.documentTranslator.service.Document;

import com.document.documentTranslator.dto.DocumentDto;
import com.document.documentTranslator.entity.Document;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Document.DocumentRepository;
import com.document.documentTranslator.util.DomainUtil;
import com.document.documentTranslator.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document create(MultipartFile file, DocumentDto dto) throws IOException, DomainException {
        dto.validate();
        if (Validator.isNull(file))
            throw new DomainException(ErrorMessage.INVALID_INPUT);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Document document = new Document();

        document.setName(fileName);
        document.setType(file.getContentType());
        document.setData(file.getBytes());
        document.setOrderId(dto.getOrderId());
        document.setUsername(dto.getUsername());

        return documentRepository.save(document);
    }

    public Document findById(Long id) throws DomainException {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (!documentOptional.isPresent())
            throw new DomainException(String.format(ErrorMessage.NOT_FOUND.getFarsiMessage(), "سند"), ErrorMessage.NOT_FOUND);
        return documentOptional.get();
    }

    public Map<String, Object> getById(DocumentDto dto) throws DomainException {
        if (Validator.isNull(dto) || Validator.isNull(dto.getId()))
            throw new DomainException(ErrorMessage.INVALID_PARAMETER);
        return findById(dto.getId()).map();
    }

    public List<Document> getAll(DocumentDto dto) {
        List<Document> documents = documentRepository.getAll(dto, DomainUtil.getBegin(dto), DomainUtil.getLength(dto));

        for (Document document: documents)
            document.setData(null);
        return documents;
    }

    public String deleteById(DocumentDto dto) throws DomainException {
        if (Validator.isNull(dto))
            throw new DomainException(ErrorMessage.INVALID_INPUT);

        if (documentRepository.existsById(dto.getId())) {
            documentRepository.deleteById(dto.getId());
            return "File has been successfully deleted";
        }
        throw new DomainException(String.format(ErrorMessage.NOT_FOUND.getFarsiMessage(), "سند"), ErrorMessage.NOT_FOUND);
    }
}
