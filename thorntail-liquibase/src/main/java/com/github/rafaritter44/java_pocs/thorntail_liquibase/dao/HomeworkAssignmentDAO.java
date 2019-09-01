package com.github.rafaritter44.java_pocs.thorntail_liquibase.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import com.github.rafaritter44.java_pocs.thorntail_liquibase.entity.HomeworkAssignment;

@ApplicationScoped
public class HomeworkAssignmentDAO {
	
	@PersistenceContext(unitName = "thorntail-liquibase-PU")
	private EntityManager entityManager;
	
	public List<HomeworkAssignment> findAll() {
		CriteriaQuery<HomeworkAssignment> query = entityManager.getCriteriaBuilder().createQuery(HomeworkAssignment.class);
		return entityManager.createQuery(query.select(query.from(HomeworkAssignment.class))).getResultList();
	}
	
	public HomeworkAssignment findById(Long id) {
		return entityManager.find(HomeworkAssignment.class, id);
	}
	
	public HomeworkAssignment save(HomeworkAssignment homeworkAssignment) {
		return entityManager.merge(homeworkAssignment);
	}
	
	public HomeworkAssignment removeById(Long id) {
		HomeworkAssignment homeworkAssignment = findById(id);
		entityManager.remove(homeworkAssignment);
		return homeworkAssignment;
	}

}
