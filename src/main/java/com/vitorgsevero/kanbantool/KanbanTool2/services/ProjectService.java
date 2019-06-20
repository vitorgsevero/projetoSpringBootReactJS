package com.vitorgsevero.kanbantool.KanbanTool2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.Project;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		//insert some logic here
		return projectRepository.save(project);
	}

}
