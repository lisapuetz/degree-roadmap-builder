package com.lisapuetz.degreeroadmapbuilder.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class BuiltMap {

    private University university;

    private List<Certification> certifications = new ArrayList<>();

    public BuiltMap() {}

    public BuiltMap(University university, List<Certification> certifications) {
        this.university = university;
        this.certifications = certifications;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
