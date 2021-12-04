package com.lisapuetz.degreeroadmapbuilder.models.data;

import com.lisapuetz.degreeroadmapbuilder.models.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends CrudRepository<University, Integer> {
}
