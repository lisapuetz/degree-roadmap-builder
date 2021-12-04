package com.lisapuetz.degreeroadmapbuilder.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Program extends AbstractEntity {

    @NotBlank(message = "Required field.")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50 characters")
    private String category;

    @OneToMany
    @JoinColumn(name="program_id")
    private List<Certification> certifications = new ArrayList<>();

    @ManyToOne
    private University university;

    public Program() {}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
