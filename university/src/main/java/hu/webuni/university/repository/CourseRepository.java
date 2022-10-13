package hu.webuni.university.repository;


import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.QCourse;

public interface CourseRepository extends 
	JpaRepository<Course, Integer>,
	QuerydslPredicateExecutor<Course>,
	QuerydslBinderCustomizer<QCourse>,
	QueryDslWithEntityGraphRepository<Course, Integer> {

	//1. Descartes szorzat miatt, túl sok eredmény sor a DB-ből
	//@EntityGraph(attributePaths = {"teachers", "students"})
	//List<Course> findAllWithTeachersAndStudents(Predicate predicate);
	
	//2. Itt már nincs Descartes-szorzat, de nem tudja a Spring Data querydsl support
//	@EntityGraph(attributePaths = {"teachers"})
//	List<Course> findAllWithTeachers(Predicate predicate);
//	
//	@EntityGraph(attributePaths = {"students"})
//	List<Course> findAllWithStudents(Predicate predicate);
	
	
	@Override
	default void customize(QuerydslBindings bindings, QCourse course) {

		bindings.bind(course.name).first((path, value) -> path.startsWithIgnoreCase(value));
		
		bindings.bind(course.teachers.any().name).first((path, value) -> path.startsWithIgnoreCase(value));
		
		bindings.bind(course.students.any().semester).all((path, values) -> {
			if(values.size() != 2)
				return Optional.empty();
			
			Iterator<? extends Integer> iterator = values.iterator();
			Integer from = iterator.next();
			Integer to = iterator.next();
			
			return Optional.of(path.between(from, to));
		});
		
		
	}

	
	
}
