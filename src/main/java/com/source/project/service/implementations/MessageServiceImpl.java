package com.source.project.service.implementations;

import com.source.project.domain.Message;
import com.source.project.domain.Objects;
import com.source.project.repos.MessageRep;
import com.source.project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRep messageRep;

    @Override
    public List<Message> findByObjects(Objects objects) {
        return messageRep.findByObjects(objects);
    }

    @Override
    public void removeById(Integer id) {
        messageRep.removeById(id);
    }

    @Override
    public void save(Message message) {
        messageRep.save(message);
    }
}
