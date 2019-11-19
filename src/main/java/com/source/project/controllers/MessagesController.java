package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.User;
import com.source.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ObjEntityService objEntityService;

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

    @GetMapping("/editMessage/{id}/{messageId}")
    public String editMessagePage(
            @PathVariable("messageId") Integer messageId,
            @PathVariable("id") Integer id,
            Model model
    ) {
        ObjEntity objEntity = objEntityService.findById(messageId);
        Map<String, String> tmpMap = objEntityService.showAttributes(objEntity);
        model.addAttribute("id", id);
        model.addAttribute("attributeValue", tmpMap);
        model.addAttribute("objectAttributes", attributeService.getListForRefactorAttributeValues(tmpMap));
        return "editMessage";
    }

    @PostMapping("/editMessage/{id}/{messageId}")
    public String editMessage(
            @PathVariable("messageId") Integer messageId,
            @PathVariable("id") Integer id,
            @RequestParam(required = false) List<String> label,
            @RequestParam(required = false) List<String> value
    ) {
        if (label!=null)
            messageService.edit(messageId, label, value);
        return"redirect:/main/{id}";
    }
}
