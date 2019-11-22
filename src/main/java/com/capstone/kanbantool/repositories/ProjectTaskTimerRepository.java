package com.capstone.kanbantool.repositories;

import com.capstone.kanbantool.domain.ProjectTaskTimer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskTimerRepository extends CrudRepository<ProjectTaskTimer, Long> {

    ProjectTaskTimer findByProjectTask_Id(Long id);
}
