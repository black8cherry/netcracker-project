package com.source.project.controllers;

import com.source.project.domain.User;
import com.source.project.service.AttributeService;
import com.source.project.service.MessageService;
import com.source.project.service.TypeService;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;

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

    @RequestMapping("/main/messages/{id}")
    public String createMessage(
            Model model,
            @PathVariable("id") Integer id
    ) {
        model.addAttribute("id",id);
        model.addAttribute("attributesMessageType", attributeService.findByObjectEntityType(typeService.findById(3)));
        model.addAttribute("userAccount", userService.getUser());

        return "messages";
    }
}
