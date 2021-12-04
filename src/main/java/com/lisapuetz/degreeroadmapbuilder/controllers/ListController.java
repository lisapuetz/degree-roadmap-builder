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

    @GetMapping("")
    public String selectUniversity(Model model) {
        model.addAttribute("title", "Select Your University");
        model.addAttribute("universities", universityRepository.findAll());
        return "list";
    }

    @PostMapping("")
    public String processSelectUniversity(Errors errors, Model model,
                                          @RequestParam int universityId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Select Your University");
            return "list";
        }

        Optional<University> universityOptional = universityRepository.findById(universityId);
        if (universityOptional.isEmpty()) {
            model.addAttribute("title", "Select Your University");
            return "list";
        }

        University university = universityOptional.get();

        return "redirect:/list/programs/" + universityId;
    }

    @GetMapping("programs/{universityId}")
    public String listPrograms(Model model, @PathVariable int universityId) {
        model.addAttribute("title", universityRepository.findById(universityId) + " Programs");

        List<Program> existingPrograms = new ArrayList<>();

        Iterable<Program> programs = programRepository.findAll();
        for (Program program : programs) {
            int programUniId = program.getUniversity().getId();
            if (programUniId == universityId) {
                existingPrograms.add(program);
            }
        }

        model.addAttribute("programs", existingPrograms);
        return "programs";
    }

    @GetMapping("courses/{universityId}")
    public String listCourses(Model model, @PathVariable int universityId) {
        model.addAttribute("title", universityRepository.findById(universityId) + " Courses");

        List<Course> existingCourses = new ArrayList<>();

        Iterable<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            int courseUniId = course.getUniversity().getId();
            if (courseUniId == universityId) {
                existingCourses.add(course);
            }
        }

        model.addAttribute("courses", existingCourses);
        return "courses";
    }
}
