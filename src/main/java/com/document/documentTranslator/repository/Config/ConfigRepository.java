package com.document.documentTranslator.repository.Config;

import com.document.documentTranslator.entity.Config;
import com.document.documentTranslator.repository.Base.BaseRepository;

import java.util.List;

public interface ConfigRepository extends BaseRepository<Config> {

    List<Config> findAll();

    Config findFirstByOrderByCreationTimeDesc();
}
