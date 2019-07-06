package com.vitorgsevero.kanbantool.KanbanTool2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.Backlog;
import com.vitorgsevero.kanbantool.KanbanTool2.domain.Project;
import com.vitorgsevero.kanbantool.KanbanTool2.domain.User;
import com.vitorgsevero.kanbantool.KanbanTool2.exceptions.ProjectIdException;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.BacklogRepository;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.ProjectRepository;
import com.vitorgsevero.kanbantool.KanbanTool2.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdateProject(Project project, String username) {
		
		try {
			
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			//new project
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			//updating project
			if(project.getId()!=null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
			
		}catch(Exception e) {
			throw new ProjectIdException("Project ID '"+ project.getProjectIdentifier().toUpperCase()+"' already exists.");
		}
	
	}
	
	//find by ID
	public Project findProjectByIdentifier(String projectId) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project ID '"+ projectId+"' does not exist.");
		}
		
		return project;
	}
	
	//find All
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	//delete by ID
	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Cannot delete project with ID'"+projectId+"'. This project does not exist.");
		}
		
		projectRepository.delete(project);
		
	}
	

}
