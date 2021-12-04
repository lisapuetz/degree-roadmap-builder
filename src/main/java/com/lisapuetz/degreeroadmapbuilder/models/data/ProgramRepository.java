package com.lisapuetz.degreeroadmapbuilder.models.data;

import com.lisapuetz.degreeroadmapbuilder.models.Program;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends CrudRepository<Program, Integer> {
}
