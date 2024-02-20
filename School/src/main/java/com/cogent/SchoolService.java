package com.cogent;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

	@Autowired
	private SchoolRepository schoolRepository;
	
	@Autowired
	private StudentClientInSchool studentClientInSchool;

	public SchoolDao addRecord(SchoolDao schoolDao) {
		
		SchoolEntity schoolEntity = SchoolEntity.builder()
				.name(schoolDao.getName())
				.address(schoolDao.getAddress())
				.build();
		
		schoolEntity = schoolRepository.save(schoolEntity);
		return schoolDao.builder()
				.name(schoolEntity.getName())
				.address(schoolEntity.getAddress())
				.build();
	}
	
	public List<SchoolDao> fetchAllRecords()
	{
		List<SchoolEntity> data = schoolRepository.findAll();
		
		return data.stream().map((record) -> SchoolDao.builder()
				.name(record.getName())
				.address(record.getAddress())
				.build())
				.collect(Collectors.toList());
	}

	public SchoolStudentRecord findSchoolWithId(int schoolId) {
		
		SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElse(new SchoolEntity());
		//find all students from Student Microservices
		List<Student> student = studentClientInSchool.fetchById(schoolId);
		return SchoolStudentRecord.builder()
				.name(schoolEntity.getName())
				.address(schoolEntity.getAddress())
				.students(student)
				.build();
	}
	
	
}
