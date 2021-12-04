package com.lisapuetz.degreeroadmapbuilder.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class University extends AbstractEntity {

    @NotBlank(message = "Required field.")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50 characters")
    private String location;

    @NotBlank(message = "Required field.")
    private int maxCreditsPerSemester;

    @NotBlank(message = "Required field.")
    private int minCreditsPerSemester;

    @OneToMany
    @JoinColumn(name="university_id")
    private List<Program> programs = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="university_id")
    private List<Course> courses = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="university_id")
    private List<Certification> certifications = new ArrayList<>();

    public University() {}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxCreditsPerSemester() {
        return maxCreditsPerSemester;
    }

    public void setMaxCreditsPerSemester(int maxCreditsPerSemester) {
        this.maxCreditsPerSemester = maxCreditsPerSemester;
    }

    public int getMinCreditsPerSemester() {
        return minCreditsPerSemester;
    }

    public void setMinCreditsPerSemester(int minCreditsPerSemester) {
        this.minCreditsPerSemester = minCreditsPerSemester;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }
}
