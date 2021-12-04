package com.lisapuetz.degreeroadmapbuilder.controllers;

import com.lisapuetz.degreeroadmapbuilder.models.*;
import com.lisapuetz.degreeroadmapbuilder.models.data.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

@Controller
public class ResultsController {

    @Autowired
    private CertificationRepository certificationRepository;

    @GetMapping(value = {"results/{certId1}", "results/{certId1}/{certId2}", "results/{certId1}/{certId2}/{certId3}"})
    public String showResults(Model model,
                              @PathVariable Map<String, String> pathVarsMap) {

        List<Certification> chosenCertifications = new ArrayList<>();
        List<Course> requiredCourses = new ArrayList<>();
        Map<String, List<Course> > userMap = new HashMap<>();
        List<Course> semester1 = new ArrayList<>();
        List<Course> semester2 = new ArrayList<>();
        List<Course> semester3 = new ArrayList<>();
        List<Course> semester4 = new ArrayList<>();
        List<Course> semester5 = new ArrayList<>();
        List<Course> semester6 = new ArrayList<>();
        List<Course> semester7 = new ArrayList<>();
        List<Course> semester8 = new ArrayList<>();
        userMap.put("Semester 1", semester1);
        userMap.put("Semester 2", semester2);
        userMap.put("Semester 3", semester3);
        userMap.put("Semester 4", semester4);
        userMap.put("Semester 5", semester5);
        userMap.put("Semester 6", semester6);
        userMap.put("Semester 7", semester7);
        userMap.put("Semester 8", semester8);
        int pathVarsIndexCounter = 1;

        //Loop through programs the 1, 2, or 3 programs entered as path variables and add to ArrayList if present.
        for (Map.Entry<String,String> entry : pathVarsMap.entrySet()) {
            String certId = pathVarsMap.get("certId" + pathVarsIndexCounter);
            if (certId != null) {
                Integer certIdAsInt = Integer.valueOf(certId);
                Optional<Certification> certOptional = certificationRepository.findById(certIdAsInt);
                certOptional.ifPresent(chosenCertifications::add);
                pathVarsIndexCounter++;
            }
        }

        //Adding the completed ArrayList of all certifications passed in as PathVariables
        model.addAttribute("certifications", chosenCertifications);

        //Generating an ArrayList of all required courses
        for (Certification certification : chosenCertifications) {
            requiredCourses.addAll(certification.getRequiredCourses());
        }

        for (Map.Entry<String, List<Course> > entry : userMap.entrySet()) {
            int semesterCreditHrs = 0;
            for (Course course : requiredCourses) {
                List<Course> prerequisites = course.getPrerequisites();
                boolean fulfilledAllPrereqs = true;

                //Check to see if the semester's total credit hours already exceed the max credit hours for that university
                if (semesterCreditHrs >= course.getUniversity().getMaxCreditsPerSemester()) {
                    break;
                }

                for (Course prereq : prerequisites) {
                    //Check to see if the prereq exists in a past semester
                    for (List<Course> accumulatedSemester : userMap.values()) {
                        if (!(accumulatedSemester.contains(prereq))) {
                            fulfilledAllPrereqs = false;
                            break;
                        } else {
                            fulfilledAllPrereqs = true;
                        }
                    }
                }
                if (fulfilledAllPrereqs) {
                    entry.getValue().add(course);
                    semesterCreditHrs += course.getCreditHrs();
                }
            }
        }

        model.addAttribute("userMap", userMap);

        model.addAttribute("title", "Your Map");
        return "results";
    }

}
