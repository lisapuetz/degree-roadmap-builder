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

    @GetMapping("add/{programId}")
    public String displayAddCertificationForm(Model model, @PathVariable int programId) {

        Optional<Program> programOptional = programRepository.findById(programId);
        if (programOptional.isEmpty()) {
            return "list";
        }
        Program program = programOptional.get();

        model.addAttribute("university", program.getUniversity());
        model.addAttribute("program", program);
        model.addAttribute(new Certification());
        return "certification/add";
    }

    @PostMapping("add/{programId}")
    public String processAddCertificationForm(@ModelAttribute @Valid Certification newCertification,
                                              Errors errors, Model model, @PathVariable int programId) {
        if (errors.hasErrors()) {
            return "list";
        }

        Optional<Program> programOptional = programRepository.findById(programId);
        if (programOptional.isEmpty()) {
            return "list";
        }
        Program program = programOptional.get();
        newCertification.setProgram(program);

        University university = program.getUniversity();
        newCertification.setUniversity(university);

        certificationRepository.save(newCertification);
        return "redirect:";
    }
}