package hu.webuni.university.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.university.aspect.Retry;
import hu.webuni.university.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CentralEducationService {
		
//	@Autowired
//	CentralEducationService centralEducationService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	private Random random = new Random();

	
	@Retry(times = 5, waitTime = 500)
	public int getNumFreeSemestersForStudent(int eduId) {
		int rnd = random.nextInt(0, 2);
		if (rnd == 0) {
			throw new RuntimeException("Central Education Service timed out.");
		} else {
			return random.nextInt(0, 10);
		}
	}
	
//	@Scheduled(cron = "${university.freeSemesterUpdater.cron}")
//	public void updateFreeSemesters() {
//		List<Student> students = studentRepository.findAll();
//		
//		students.forEach(student -> {
//			System.out.format("Get number of free semesters of student in CentralEducationService %s%n", student.getName());
//	
//			try {
//				Integer eduId = student.getEduId();
//				if(eduId != null) {
//					int numFreeSemesters = getNumFreeSemestersForStudent(eduId);
//					student.setNumFreeSemesters(numFreeSemesters);
//					studentRepository.save(student);
//				}
//			} catch (Exception e) {
//				log.error("Error calling central education service.", e);
//			}
//		});
//	}
}
