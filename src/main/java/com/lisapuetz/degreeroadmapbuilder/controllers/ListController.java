package com.lisapuetz.degreeroadmapbuilder.controllers;

import com.lisapuetz.degreeroadmapbuilder.models.BuiltMap;
import com.lisapuetz.degreeroadmapbuilder.models.Course;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private UniversityRepository universityRepository;


    @GetMapping("programs")
    public String listPrograms(Model model) {
        model.addAttribute("title", "Programs");

        model.addAttribute("universities", universityRepository.findAll());
        model.addAttribute("programs", programRepository.findAll());
        return "list/programs";
    }

    @GetMapping("courses")
    public String listCourses(Model model) {
        model.addAttribute("title", "Courses");

        model.addAttribute("universities", universityRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "list/courses";
    }
}
