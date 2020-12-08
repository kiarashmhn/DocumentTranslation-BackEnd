package com.document.documentTranslator.repository.Base;

import com.document.documentTranslator.entity.AbstractEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity> extends PagingAndSortingRepository<T , Long> {
}

