package com.lisapuetz.degreeroadmapbuilder.controllers;

import com.lisapuetz.degreeroadmapbuilder.models.Program;
import com.lisapuetz.degreeroadmapbuilder.models.University;
import com.lisapuetz.degreeroadmapbuilder.models.data.ProgramRepository;
import com.lisapuetz.degreeroadmapbuilder.models.data.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("program")
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("programs", programRepository.findAll());
        return "program/index";
    }

    @GetMapping("add/{universityId}")
    public String displayAddProgramForm(Model model, @PathVariable int universityId) {
        model.addAttribute("university", universityRepository.findById(universityId));
        model.addAttribute(new Program());
        return "program/add";
    }

    @PostMapping("add/{universityId}")
    public String processAddProgramForm(@ModelAttribute @Valid Program newProgram,
                                        Errors errors, Model model, @PathVariable int universityId) {

        if (errors.hasErrors()) {
            return "program/add";
        }

        Optional<University> universityOptional = universityRepository.findById(universityId);
        if (universityOptional.isEmpty()) {
            return "add";
        }

        University university = universityOptional.get();
        newProgram.setUniversity(university);

        programRepository.save(newProgram);
        return "redirect:../list/programs";
    }
}