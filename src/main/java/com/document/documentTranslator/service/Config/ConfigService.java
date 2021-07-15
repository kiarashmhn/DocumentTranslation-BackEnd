package com.document.documentTranslator.service.Config;

import com.document.documentTranslator.dto.ConfigDto;
import com.document.documentTranslator.entity.Config;
import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.repository.Config.ConfigRepository;
import com.document.documentTranslator.util.DomainUtil;
import com.document.documentTranslator.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    public Config createOrUpdate(ConfigDto configDto) throws DomainException {
        if (Validator.isNull(configDto) || !configDto.validate())
            throw new DomainException(ErrorMessage.INVALID_PARAMETER);
        Config config = new Config();
        config.setAccName(configDto.getAccName());
        config.setAccLastName(configDto.getAccLastName());
        config.setAccNumber(configDto.getAccNumber());
        config.setAddress(configDto.getAddress());
        config.setRibId(configDto.getRibId());
        config.setRibName(configDto.getRibName());
        config.setQuestions(DomainUtil.mapToString((HashMap<String, Object>) configDto.getQuestions()));

        List<Config> configs = configRepository.findAll();
        if (Validator.listNotNull(configs))
            configRepository.deleteAll();
        configRepository.save(config);
        return config;
    }

    public Config get() {
        return configRepository.findFirstByOrderByCreationTimeDesc();
    }
}
