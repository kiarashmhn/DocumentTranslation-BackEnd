package com.document.documentTranslator.repository.User;

import com.document.documentTranslator.dto.UserDto;
import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.repository.Base.DslRepository;

public interface UserDslRepository extends DslRepository<User, UserDto> {
}
