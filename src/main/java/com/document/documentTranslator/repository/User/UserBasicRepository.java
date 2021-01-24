package com.document.documentTranslator.repository.User;


import com.document.documentTranslator.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBasicRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);
    User findUserById(Long id);
    User findUserByUsernameAndPassword(String username, String password);
    List<User> findAllByEnable(Boolean enable);
    List<User> findAllByLevelAndEnable(Long level, Boolean enable);
    List<User> findAllByLevelInAndEnable(List<Long> level, Boolean enable);


}
