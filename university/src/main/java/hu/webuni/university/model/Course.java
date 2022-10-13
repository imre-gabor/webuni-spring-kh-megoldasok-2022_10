package hu.webuni.university.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NamedEntityGraph(
	name = "Course.students",
	attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(
		name = "Course.teachers",
		attributeNodes = @NamedAttributeNode("teachers"))
public class Course {

	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private int id;

	@ToString.Include
	private String name;
	
	@ManyToMany
	private Set<Student> students;

	@ManyToMany
	private Set<Teacher> teachers;
}