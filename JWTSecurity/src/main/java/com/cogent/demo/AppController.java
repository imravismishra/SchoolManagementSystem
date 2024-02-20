package com.cogent.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/main")
public class AppController {

	@GetMapping("/")
	public String welcome() {
		return "Secure EndPoints";
	}
}
