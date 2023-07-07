package org.zerock.j1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j1.domain.Sample;

public interface SampleRepository extends JpaRepository<Sample, String>{
  
}
