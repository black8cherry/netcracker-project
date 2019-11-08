package com.source.project.controllers;

import com.source.project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Transactional
@Controller
public class MessagesController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/main/{id}")
    public String saveMessage(
            @RequestParam String message,
            @RequestParam String userId,
            @PathVariable("id") Integer id
    ) {
        messageService.save(userId , id, message);
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
