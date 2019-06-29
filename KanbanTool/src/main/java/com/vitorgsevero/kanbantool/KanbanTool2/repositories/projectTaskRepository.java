package com.vitorgsevero.kanbantool.KanbanTool2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long>{

}
