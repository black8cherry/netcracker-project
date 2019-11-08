package com.source.project.controllers;

import com.source.project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Controller
public class MessagesController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/main/{id}")
    public String saveMessage(
            @PathVariable("id") Integer id,
            @RequestParam List<String> label,
            @RequestParam List<String> value
    ) {
        messageService.create(id, label, value);

        return "redirect:/main/{id}";
    }

    @RequestMapping("/deleteMessage/{id}/{messageId}")
    public String deleteMessage(
            @PathVariable("messageId") Integer messageId,
            @PathVariable("id") Integer id
    ) {
        messageService.delete(messageId);
        return"redirect:/main/{id}";
    }

}
