package com.capstone.kanbantool.repositories;

import com.capstone.kanbantool.domain.TaskChecklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskChecklistRepository extends CrudRepository<TaskChecklist, Long> {

}
