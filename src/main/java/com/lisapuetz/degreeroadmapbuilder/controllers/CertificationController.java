package com.lisapuetz.degreeroadmapbuilder.controllers;

import com.lisapuetz.degreeroadmapbuilder.models.Certification;
import com.lisapuetz.degreeroadmapbuilder.models.Program;
import com.lisapuetz.degreeroadmapbuilder.models.University;
import com.lisapuetz.degreeroadmapbuilder.models.data.CertificationRepository;
import com.lisapuetz.degreeroadmapbuilder.models.data.ProgramRepository;
import com.lisapuetz.degreeroadmapbuilder.models.data.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("certification")
public class CertificationController {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private ProgramRepository programRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("certifications", certificationRepository.findAll());
        return "certification/index";
    }

    @GetMapping("add/{universityId}/{programId}")
    public String displayAddCertificationForm(Model model, @PathVariable int universityId,
                                              @PathVariable int programId) {
        Optional<University> universityOptional = universityRepository.findById(universityId);
        if (universityOptional.isEmpty()) {
            return "list";
        }
        University university = universityOptional.get();

        Optional<Program> programOptional = programRepository.findById(programId);
        if (programOptional.isEmpty()) {
            return "list";
        }
        Program program = programOptional.get();

        model.addAttribute("university", universityRepository.findById(universityId));
        model.addAttribute("program", programRepository.findById(programId));
        model.addAttribute("courses", university.getCourses());
        model.addAttribute(new Certification());
        return "certification/add";
    }

    @PostMapping("add")
    public String processAddCertificationForm(@ModelAttribute @Valid Certification newCertification,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "certification/add";
        }

        certificationRepository.save(newCertification);
        return "redirect:";
    }

    @GetMapping("view/{certificationId}")
    public String displayViewCertification(Model model, @PathVariable int certificationId) {

        Optional optCert = certificationRepository.findById(certificationId);
        if (optCert.isPresent()) {
            Certification certification = (Certification) optCert.get();
            model.addAttribute("certification", certification);
            return "certification/view";
        } else {
            return "redirect:../";
        }
    }
}