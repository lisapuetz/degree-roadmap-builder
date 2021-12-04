package com.lisapuetz.degreeroadmapbuilder.models.data;

import com.lisapuetz.degreeroadmapbuilder.models.Certification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends CrudRepository<Certification, Integer> {
}
