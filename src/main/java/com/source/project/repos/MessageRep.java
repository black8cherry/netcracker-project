package com.source.project.repos;

import com.source.project.domain.Message;
import com.source.project.domain.Objects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRep extends
        JpaRepository<Message, Long>
{
    List<Message> findByObjects(Objects objects);
    void removeById(Integer id);
}
