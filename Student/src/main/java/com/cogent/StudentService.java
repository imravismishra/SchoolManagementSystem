package com.cogent;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public StudentDao insert(StudentDao studentDao) {

		StudentEntity studentEntity = StudentEntity.builder()
				.name(studentDao.getName())
				.email(studentDao.getEmail())
				.schoolId(studentDao.getSchoolId())
				.build();

		studentEntity = studentRepository.save(studentEntity);

		return studentDao.builder()
				.name(studentEntity.getName())
				.email(studentEntity.getEmail())
				.schoolId(studentEntity.getSchoolId())
				.build();
	}

	public List<StudentDao> fetchAllRecords() {
		
		List<StudentEntity> allRecords = studentRepository.findAll();
		return allRecords.stream()
				.map((record) -> StudentDao.builder()
						.name(record.getName())
						.email(record.getEmail())
						.schoolId(record.getSchoolId())
						.build())
				.collect(Collectors.toList());
		
	}

	public List<StudentDao> fetchStudentsBySchooId(int schoolid) {
		// TODO Auto-generated method stub
		List<StudentEntity> allRecords = studentRepository.findAllBySchoolId(schoolid);
		 return allRecords.stream()
					.map((record) -> StudentDao.builder()
							.name(record.getName())
							.email(record.getEmail())
							.schoolId(record.getSchoolId())
							.build())
					.collect(Collectors.toList());
	}

}
