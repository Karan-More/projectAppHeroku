package com.capstone.kanbantool.repositories;

import com.capstone.kanbantool.domain.ProjectTaskHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskHistoryRepository extends CrudRepository<ProjectTaskHistory, Long> {

    Iterable<ProjectTaskHistory> findAllByProjectTaskForHistory_idOrderByIdDesc(Long id);
}
