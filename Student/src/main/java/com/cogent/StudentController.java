package com.cogent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	@PostMapping(path = { "/insert" })
	public StudentDao insertRecord(@RequestBody StudentDao studentDao) {
		return studentService.insert(studentDao);
	}
	
	@GetMapping(path = {"/fetch"})
	public List<StudentDao> fetchAll()
	{
		return studentService.fetchAllRecords();
	}
	
	@GetMapping(path = {"/fetch/{schoolid}"})
	public List<StudentDao> fetchById(@PathVariable("schoolid") int schoolid)
	{
		return studentService.fetchStudentsBySchooId(schoolid);
	}

}
