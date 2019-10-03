package com.source.project.repos;

import com.source.project.domain.Message;
import com.source.project.domain.Objects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRep extends JpaRepository<Message, Long> {
    List<Message> findByObjects(Objects objects);
}
