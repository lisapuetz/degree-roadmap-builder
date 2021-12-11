package com.lisapuetz.degreeroadmapbuilder.controllers;

import com.lisapuetz.degreeroadmapbuilder.models.*;
import com.lisapuetz.degreeroadmapbuilder.models.data.CertificationRepository;
import com.lisapuetz.degreeroadmapbuilder.models.data.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@Controller
public class ResultsController {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(value = {"results/{certId1}", "results/{certId1}/{certId2}", "results/{certId1}/{certId2}/{certId3}"})
    public String showResults(Model model,
                              @PathVariable Map<String, String> pathVarsMap) {

        List<Certification> chosenCertifications = new ArrayList<>();
        List<Course> requiredCourses = new ArrayList<>();
        //Using a LinkedHashMap so that the semesters remain in chronological order
        Map<String, List<Course> > userMap = new LinkedHashMap<>();
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

        //Loop through programs the 1, 2, or 3 programs entered as path variables and add to ArrayList if present.
        for (String certId : pathVarsMap.values()) {
            if (certId != null) {
                Integer certIdAsInt = Integer.valueOf(certId);
                Optional<Certification> certOptional = certificationRepository.findById(certIdAsInt);
                certOptional.ifPresent(chosenCertifications::add);
            }
        }

        //Adding the completed ArrayList of all certifications passed in as PathVariables
        model.addAttribute("certifications", chosenCertifications);

        for (Certification certification : chosenCertifications) {
            //Adding university's GenEd requirements to ArrayList of all required courses
            for (Course course : courseRepository.findAll()) {
                if (course.getIsGeneralEducationRequirement() &&
                        course.getUniversity().equals(certification.getUniversity()) &&
                        !(requiredCourses.contains(course))) {
                    requiredCourses.add(course);
                }
            }

            //Adding each certification's requirements to ArrayList of all required courses
            requiredCourses.addAll(certification.getRequiredCourses());

        }

        //Calculate total credit hours for Map
        int totalCreditHours = 0;
        for (Course course : requiredCourses) {totalCreditHours = totalCreditHours + course.getCreditHrs();}

        //TODO: If total exceeds maximum total for university, send an error message and return to program selection

        for (Map.Entry<String, List<Course> > entry : userMap.entrySet()) {
            int semesterCreditHrs = 0;

            for (Course course : requiredCourses) {
                boolean hasFulfilledAllPrereqs = true;
                boolean isAlreadyScheduled = false;
                List<Object> coursesAlreadyScheduled = new ArrayList<>();

                String currentSemesterKey = entry.getKey();
                List<String> keysList = new ArrayList<>(userMap.keySet());
                Integer currentSemesterIndex = keysList.indexOf(currentSemesterKey);
                List<List<Course>> valuesList = new ArrayList<>(userMap.values());

                for (int i=0; i < valuesList.size(); i++) {
                    //if semester index is index of entry, skip
                    if (!(currentSemesterIndex.equals(i))) {
                        coursesAlreadyScheduled.addAll(valuesList.get(i));
                    }
                }

                if (coursesAlreadyScheduled.contains(course)) {
                    isAlreadyScheduled = true;
                } else {
                    isAlreadyScheduled = false;
                }

                List<Course> prerequisites = course.getPrerequisites();
                //Check to see if each prereq exists in a past semester
                for (Course prereq : prerequisites) {
                    if (coursesAlreadyScheduled.contains(prereq)) {
                        hasFulfilledAllPrereqs = true;
                    } else {
                        hasFulfilledAllPrereqs = false;
                    }
                }

                if (!isAlreadyScheduled && hasFulfilledAllPrereqs) {
                    entry.getValue().add(course);
                    semesterCreditHrs = semesterCreditHrs + course.getCreditHrs();
                    //Check to see if the semester's new total credit hours exceed the amount
                    // of the map's totalCreditHours divided by the amount of semesters,
                    // so that it's more evenly distributed
                    if (semesterCreditHrs > (totalCreditHours / userMap.size())) break;
                }
            }
        }

        model.addAttribute("userMap", userMap);

        model.addAttribute("title", "Your Map");
        return "results";
    }

}
