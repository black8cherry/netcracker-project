package com.source.project.controllers;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.ObjectsRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.w3c.dom.Attr;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AddController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ObjectsRep objectsRep;
    @Autowired
    private TypeRep typeRep;

    @GetMapping("/addFilm")
    public String add_main(
            Model model
    ) {
        model.addAttribute("types", typeRep.findAll());
        model.addAttribute("objects", objectsRep.findAll());
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String add(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (name != null && type != null) {
            Objects object = new Objects(name, typeRep.findByType(type));

            if (file.getSize() != 0) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                object.setFilename(resultFilename);
            } else {
                object.setFilename("no-image.jpg");
            }

            objectsRep.save(object);
        }
        return "redirect:/addFilm";
    }


}
