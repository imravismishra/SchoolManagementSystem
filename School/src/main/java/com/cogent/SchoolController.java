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
@RequestMapping("/api/v1/school")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	@PostMapping("/insert")
	public SchoolDao insert(@RequestBody SchoolDao schoolDao) {
		return schoolService.addRecord(schoolDao);
	}

	@GetMapping("/fetch")
	public List<SchoolDao> fetchAllRecords() {
		return schoolService.fetchAllRecords();
	}

	@GetMapping("/fetch/{schoolid}")
	public SchoolStudentRecord findAllRecordsById(@PathVariable("schoolid") int schoolId) {
		return schoolService.findSchoolWithId(schoolId);
	}

}
