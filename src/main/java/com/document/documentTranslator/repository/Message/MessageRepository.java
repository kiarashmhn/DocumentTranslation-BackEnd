package com.document.documentTranslator.repository.Message;

import com.document.documentTranslator.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findByOrderId(Long orderId);
}
