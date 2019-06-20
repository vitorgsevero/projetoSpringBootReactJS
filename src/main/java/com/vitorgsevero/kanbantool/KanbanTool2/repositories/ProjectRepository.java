package com.vitorgsevero.kanbantool.KanbanTool2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	@Override
	Iterable<Project> findAllById(Iterable<Long> iterable);

}
