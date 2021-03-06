package com.lisapuetz.degreeroadmapbuilder.controllers;

import com.lisapuetz.degreeroadmapbuilder.models.Course;
import com.lisapuetz.degreeroadmapbuilder.models.University;
import com.lisapuetz.degreeroadmapbuilder.models.data.CourseRepository;
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
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping("add/{universityId}")
    public String displayAddCourseForm(Model model, @PathVariable int universityId) {

        Optional<University> universityOptional = universityRepository.findById(universityId);
        if (universityOptional.isEmpty()) {
            return "list";
        }
        University university = universityOptional.get();

        model.addAttribute("university", university);
        model.addAttribute(new Course());
        return "course/add";
    }

    @PostMapping("add/{universityId}")
    public String processAddCourseForm(@ModelAttribute @Valid Course newCourse,
                                       Errors errors, Model model, @PathVariable int universityId,
                                       @RequestParam(required = false) List<Integer> prerequisites) {
        if (errors.hasErrors()) {
            return "course/add/{universityId}";
        }

        Optional<University> universityOptional = universityRepository.findById(universityId);
        if (universityOptional.isEmpty()) {
            return "course/add/{universityId}";
        }

        University university = universityOptional.get();
        newCourse.setUniversity(university);

        if (!(prerequisites == null)) {
            List<Course> prereqObjects = (List<Course>) courseRepository.findAllById(prerequisites);
            newCourse.setPrerequisites(prereqObjects);
        }

        courseRepository.save(newCourse);
        return "redirect:../list/courses";
    }
}
