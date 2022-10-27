package hu.webuni.university.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.HistoryData;
import hu.webuni.university.model.QCourse;
import hu.webuni.university.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	private final CourseRepository courseRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<Course> searchCourses(Predicate predicate, Pageable pageable) {
		
		/*
		List<Course> courses = courseRepository.findAll(predicate, "Course.students", EntityGraphType.LOAD);		
		courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers", EntityGraphType.LOAD);
		*/
		
		Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
		
		BooleanExpression inPredicate = QCourse.course.in(coursePage.getContent());
		
		List<Course> courses = courseRepository.findAll(inPredicate, "Course.students", EntityGraphType.LOAD, Sort.unsorted());
		
		courses = courseRepository.findAll(inPredicate, "Course.teachers", EntityGraphType.LOAD, pageable.getSort());
		
		return courses;
	}

	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<HistoryData<Course>> getHistoryById(int id) {

		List resultList = AuditReaderFactory.get(em)
			.createQuery()
			.forRevisionsOfEntity(Course.class, false, true)
			.add(AuditEntity.property("id").eq(id))
			.getResultList().stream().map(o -> {
					Object[] objArray = (Object[]) o;
					
					DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) objArray[1];
					RevisionType revType = (RevisionType) objArray[2];
					
					Course course = (Course) objArray[0];
					course.getStudents().size();
					course.getTeachers().size();
					
					HistoryData<Course> historyData = 
						new HistoryData<>(
							course, revType,
							defaultRevisionEntity.getId(), defaultRevisionEntity.getRevisionDate());
					return historyData;
				}).toList();
		return resultList;
	}

	
}
