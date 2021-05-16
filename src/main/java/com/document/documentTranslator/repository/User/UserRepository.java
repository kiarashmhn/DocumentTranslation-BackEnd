package com.document.documentTranslator.repository.User;

import com.document.documentTranslator.entity.User;
import com.document.documentTranslator.repository.Base.BaseRepository;

import java.util.List;

public interface UserRepository extends BaseRepository<User>, UserDslRepository {
    User findByUsernameAndEnable(String username, Boolean enable);
    User findUserById(Long id);
    User findUserByUsernameAndPassword(String username, String password);
    List<User> findAllByEnable(Boolean enable);
    List<User> findAllByLevelAndEnable(Long level, Boolean enable);
    List<User> findAllByLevelInAndEnable(List<Long> level, Boolean enable);
}
