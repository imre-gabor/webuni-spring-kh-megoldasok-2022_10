package hu.webuni.eduservice.xmlws;

import javax.jws.WebService;

@WebService
public interface StudentXmlWs {

	int getNumFreeSemestersForStudent(int eduId);

}