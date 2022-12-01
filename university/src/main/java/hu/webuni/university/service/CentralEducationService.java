package hu.webuni.university.service;

import java.util.Random;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import hu.webuni.eduservice.wsclient.StudentXmlWsImplService;
import hu.webuni.university.aspect.Retry;
import hu.webuni.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

@RequiredArgsConstructor
public class CentralEducationService {

	private static final String DEST_FREE_SEMESTER_REQUESTS = "free_semester_requests";
	public static final String DEST_FREE_SEMESTER_RESPONSES = "free_semester_responses";

	private final JmsTemplate educationJmsTemplate;
	private final StudentRepository studentRepository;
	
	private Random random = new Random();

	
	@Retry(times = 5, waitTime = 500)
	public int getNumFreeSemestersForStudent(int eduId) {
		int rnd = random.nextInt(0, 2);
		if (rnd == 0) {
			throw new RuntimeException("Central Education Service timed out.");
		} else {
			return new StudentXmlWsImplService()
					.getStudentXmlWsImplPort()
					.getNumFreeSemestersForStudent(eduId);
		}
	}
	
	public void askNumFreeSemestersForStudent(int eduId) {
	
	}
}
