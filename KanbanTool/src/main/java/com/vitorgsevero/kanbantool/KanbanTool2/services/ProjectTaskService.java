package com.vitorgsevero.kanbantool.KanbanTool2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.Backlog;
import com.vitorgsevero.kanbantool.KanbanTool2.domain.Project;
import com.vitorgsevero.kanbantool.KanbanTool2.domain.ProjectTask;
import com.vitorgsevero.kanbantool.KanbanTool2.exceptions.ProjectNotFoundException;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.BacklogRepository;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.ProjectRepository;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Iterable<ProjectTask> findBacklogById(String id) {
		
		Project project = projectRepository.findByProjectIdentifier(id);
		
		if(project==null) {
			throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist!"); 
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	//get by ID
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		
		//make sure we are searching on an existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog==null) {
			throw new ProjectNotFoundException("Project with ID'"+backlog_id+"' does not exist!");
		}
		
		//make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found!");
		}
		
		//make sure that the backlog/project id in the path corresponds to the right project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist on the following Project: '"+backlog_id+"'");
		}
		
		
		return projectTaskRepository.findByProjectSequence(pt_id);
	}

	//post
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		
		try{
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
				

		}catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found!");
		}
		
	}
	
	//update
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		
		ProjectTask projectTask = this.findPTByProjectSequence(backlog_id, pt_id);
		
		projectTask = updatedTask;
		
		return projectTaskRepository.save(projectTask);		
		
	}
	
	
	//delete
	public void deleteByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		
		Backlog backlog = projectTask.getBacklog();
		
		List<ProjectTask> pts = backlog.getProjectTasks(); 
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		
		projectTaskRepository.delete(projectTask);
		
	}
	
	
	
	
}
