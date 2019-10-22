package com.source.project.controllers;

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

import java.io.IOException;

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
        model.addAttribute("movie", objEntityService.findAllByFilenameNotNull());
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String add(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (name != null && type != null) {
            objEntityService.save(name, type, file, uploadPath);
        }
        return "redirect:/addFilm";
    }


}
