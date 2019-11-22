package com.capstone.kanbantool.repositories;

import com.capstone.kanbantool.domain.Project;
import com.capstone.kanbantool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

    List<ProjectTask> findAllByProjectIdentifier(String id);

    List<ProjectTask> findAll();

    ProjectTask findByProjectSequence(String sequence);

    List<ProjectTask> findAllByProjectTaskAssigneeAndProjectIdentifier(String taskAssignee, String projectIdentifier);

    ProjectTask findByProjectTaskAssigneeAndProjectSequence(String taskAssignee, String projectSequence);
}
