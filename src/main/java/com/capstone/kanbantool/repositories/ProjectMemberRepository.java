package com.capstone.kanbantool.repositories;

import com.capstone.kanbantool.domain.ProjectMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends CrudRepository<ProjectMember, Long> {

    Iterable<ProjectMember> findAllByUsername(String username);


    List<ProjectMember> findAllByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<ProjectMember> findAll();
}
