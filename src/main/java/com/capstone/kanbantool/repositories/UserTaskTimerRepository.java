package com.capstone.kanbantool.repositories;

import com.capstone.kanbantool.domain.ProjectTask;
import com.capstone.kanbantool.domain.UserTaskTimer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskTimerRepository extends CrudRepository<UserTaskTimer, Long> {

    List<UserTaskTimer> findAllByProjectTaskSequence(String projectSequence);

    UserTaskTimer findTop1ByProjectTaskSequenceOrderByIdDesc(String projectSequence);

    Optional<UserTaskTimer> findById(Long id);

}
