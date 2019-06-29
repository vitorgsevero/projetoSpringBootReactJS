package com.vitorgsevero.kanbantool.KanbanTool2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.Backlog;
import com.vitorgsevero.kanbantool.KanbanTool2.domain.ProjectTask;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.BacklogRepository;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		//PTs to be added to a specific project, project != null, BL exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		//Set the Backlog to the Project Task
		projectTask.setBacklog(backlog);
		//we want our project sequence to be like this: IDPRO-1, IDPRO-2, IDPRO-3 ...150
		Integer BacklogSequence = backlog.getPTSequence();
		//Update the Backlog Sequence
		BacklogSequence++;
		
		backlog.setPTSequence(BacklogSequence);
		
		//Add Sequence to Project Task
		projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		//Setting a Initial Priority to the Project Task when is NULL 
		if(projectTask.getPriority()==null) {
		projectTask.setPriority(3);
		}
		
		//Setting a Initial Status to the Project Task when is NULL 
		if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
			projectTask.setStatus("TO_DO");
		}
		
		return projectTaskRepository.save(projectTask);
		
	}
	
	public Iterable<ProjectTask> findBacklogById(String id){
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	
	
	
}
