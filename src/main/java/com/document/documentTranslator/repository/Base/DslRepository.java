package com.document.documentTranslator.repository.Base;


import com.document.documentTranslator.dto.BaseDto;
import com.document.documentTranslator.entity.AbstractEntity;

import java.util.List;


public interface DslRepository<T extends AbstractEntity, H extends BaseDto> {

    public List<T> getAll(H dto, int begin, int length);

    public long getAllCount(H dto, int begin, int length);

}

