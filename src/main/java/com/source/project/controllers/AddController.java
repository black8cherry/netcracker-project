package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.service.MessageService;
import com.source.project.service.ObjEntityService;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AddController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ObjEntityService objEntityService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/addFilm")
    public String add_main(
            Model model
    ) {

        model.addAttribute("types", typeService.findAll());
        model.addAttribute("objEntity", objEntityService.findAll());
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String add(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (name != null && type != null) {
            ObjEntity objEntity = new ObjEntity(name, typeService.findByType(type));
            if (file.getSize() != 0) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                objEntity.setFilename(resultFilename);
            } else {
                objEntity.setFilename("no-image.jpg");
            }

            objEntityService.save(objEntity);
        }
        return "redirect:/addFilm";
    }


}
