package com.lisapuetz.degreeroadmapbuilder.controllers;

import com.lisapuetz.degreeroadmapbuilder.models.BuiltMap;
import com.lisapuetz.degreeroadmapbuilder.models.Program;
import com.lisapuetz.degreeroadmapbuilder.models.University;
import com.lisapuetz.degreeroadmapbuilder.models.data.CertificationRepository;
import com.lisapuetz.degreeroadmapbuilder.models.data.CourseRepository;
import com.lisapuetz.degreeroadmapbuilder.models.data.ProgramRepository;
import com.lisapuetz.degreeroadmapbuilder.models.data.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("select-university")
    public String selectUniversity(Model model) {
        model.addAttribute("title", "Select a University");
        model.addAttribute(new BuiltMap());
        model.addAttribute("universities", universityRepository.findAll());
        model.addAttribute("certifications", certificationRepository.findAll());
        return "select-university";
    }

    @PostMapping("select-university")
    public String processSelectUniversity(@ModelAttribute @Valid BuiltMap newMap,
                                   Errors errors, Model model,
                                   @RequestParam int universityId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Build Your Map");
            return "select-university";
        }

        Optional<University> universityOptional = universityRepository.findById(universityId);
        if (universityOptional.isEmpty()) {
            model.addAttribute("title", "Build Your Map");
            return "select-university";
        }

        University university = universityOptional.get();

        return "redirect:/select-program/" + universityId;
    }


    @GetMapping("select-program/{universityId}")
    public String selectProgram(Model model, @PathVariable int universityId) {
        model.addAttribute("title", "Select Your Programs");

        List<Program> possiblePrograms = new ArrayList<>();

        Iterable<Program> programs = programRepository.findAll();
        for (Program program : programs) {
            int programUniId = program.getUniversity().getId();
            if (programUniId == universityId) {
                possiblePrograms.add(program);
            }
        }

        model.addAttribute("programs", possiblePrograms);
        return "select-program";
    }

    @PostMapping("select-program/{universityId}")
    public String processBuildForm(@ModelAttribute @Valid BuiltMap newMap,
                                   Errors errors, Model model,
                                   @RequestParam(value = "certifications") int[] certifications,
                                   @PathVariable int universityId) {

        StringBuilder returnPathVariableAccumulator = new StringBuilder();

        if (errors.hasErrors()) {
            model.addAttribute("title", "Select Your Programs");
            return "select-program/" + universityId;
        }

        for (int certificationId : certifications) {
            returnPathVariableAccumulator.append("/").append(certificationId);
        }
        String returnPathVariableAdditions = returnPathVariableAccumulator.toString();
        return "redirect:/results" + returnPathVariableAdditions;
    }

}
