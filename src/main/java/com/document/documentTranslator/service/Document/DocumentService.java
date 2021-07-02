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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document create(MultipartFile file, DocumentDto dto) throws IOException, DomainException {
        dto.validate();
        if (Validator.isNull(file))
            throw new DomainException(ErrorMessage.INVALID_INPUT);

        dto.setName(URLDecoder.decode(dto.getName(), "UTF-8"));

        if (dto.getSize() > 50000000L)
            throw new DomainException(ErrorMessage.FILE_SIZE_TOO_BIG);

        String path = getFilePath(String.valueOf(dto.getOrderId()), dto.getType(), dto.getName(), dto.getMessageId());
        InputStream fileStream = new BufferedInputStream(file.getInputStream());
        createFile(fileStream, path, dto.getSize().intValue());
        Document document = new Document();

        document.setName(dto.getName());
        document.setType(dto.getType());
        document.setSize(dto.getSize());
        document.setPath(path);
        document.setOrderId(dto.getOrderId());
        document.setUsername(dto.getUsername());
        document.setMessageId(dto.getMessageId());

        return documentRepository.save(document);
    }

    private void createFile(InputStream fileStream, String path, int size) throws DomainException {
        try {
            if (size > 0) {
                int read;
                byte[] bytes = new byte[size];

                File file = new File(path);

                file.getParentFile().mkdirs();
                try (OutputStream outputStream = new FileOutputStream(file)) {
                    while ((read = fileStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                    outputStream.flush();

                }
            }

        } catch (Exception e) {
            throw new DomainException(ErrorMessage.INTERNAL_ERROR);
        }
    }

    private String getFilePath(String orderId, String type, String fileName, Long messageId) {
        String basePath = System.getProperty("user.dir") + "/files";
        String separator = "/";
        if (Validator.isNull(messageId))
            return basePath + separator + orderId + separator + type + separator + fileName;
        return basePath + separator + orderId + separator + type + separator + messageId + separator + fileName;
    }


    public Document findById(Long id) throws DomainException {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (!documentOptional.isPresent())
            throw new DomainException(String.format(ErrorMessage.NOT_FOUND.getFarsiMessage(), "سند"), ErrorMessage.NOT_FOUND);
        return documentOptional.get();
    }

    public File getFileById(DocumentDto dto) throws DomainException {
        if (Validator.isNull(dto) || Validator.isNull(dto.getId()))
            throw new DomainException(ErrorMessage.INVALID_PARAMETER);
        Document document = findById(dto.getId());
        return new File(document.getPath());
    }

    public List<Document> getAll(DocumentDto dto) {
        return documentRepository.getAll(dto, DomainUtil.getBegin(dto), DomainUtil.getLength(dto));
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
