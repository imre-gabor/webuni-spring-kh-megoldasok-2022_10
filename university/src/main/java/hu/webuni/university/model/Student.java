package hu.webuni.university.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Audited
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class Student extends UniversityUser {

	
	private int semester;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;
	
	private Integer eduId;
	private Integer numFreeSemesters;
	
	private int balance;
	@Override
	public UserType getUserType() {
		return UserType.STUDENT;
	}
}
