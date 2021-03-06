package com.github.rafaritter44.java_pocs.thorntail_hazelcast.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.rafaritter44.java_pocs.thorntail_hazelcast.cache.HomeworkAssignmentCache;
import com.github.rafaritter44.java_pocs.thorntail_hazelcast.dao.HomeworkAssignmentDAO;
import com.github.rafaritter44.java_pocs.thorntail_hazelcast.entity.HomeworkAssignment;

@ApplicationScoped
public class HomeworkAssignmentService {
	
	@Inject
	private HomeworkAssignmentDAO dao;
	
	@Inject
	private HomeworkAssignmentCache cache;
	
	public List<HomeworkAssignment> findAll() {
		return dao.findAll();
	}
	
	public HomeworkAssignment findById(Long id) {
		return cache.findById(id).orElseGet(() -> dao.findById(id));
	}
	
	public HomeworkAssignment save(HomeworkAssignment homeworkAssignment) {
		return cache.save(dao.save(homeworkAssignment));
	}
	
	public HomeworkAssignment removeById(Long id) {
		cache.removeById(id);
		return dao.removeById(id);
	}
	
}
