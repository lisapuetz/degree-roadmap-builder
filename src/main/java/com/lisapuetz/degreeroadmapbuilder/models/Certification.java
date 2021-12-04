package com.lisapuetz.degreeroadmapbuilder.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Certification extends AbstractEntity{

    @ManyToMany
    private List<Course> requiredCourses = new ArrayList<>();

    @ManyToOne
    private Program program;

    @ManyToOne
    private University university;

    public Certification() {}

    public List<Course> getRequiredCourses() {
        return requiredCourses;
    }

    public void setRequiredCourses(List<Course> requiredCourses) {
        this.requiredCourses = requiredCourses;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
