package com.cogent;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service",url = "localhost:8081/api/v1/students")
public interface StudentClientInSchool {

	@GetMapping(path = {"/fetch/{schoolid}"})
    List<Student> fetchById(@PathVariable("schoolid") int schoolid);
}
