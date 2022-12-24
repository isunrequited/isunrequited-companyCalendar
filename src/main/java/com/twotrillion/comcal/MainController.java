package com.twotrillion.comcal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public String main_page(HttpSession session) {
		System.out.println("[HomeController] home() CALLED!!");

		String nextPage = "/user/main_page";

		if (session.getAttribute("logged_in_employee_vo") == null) {
			nextPage = "/user/employee/login_page";
		}

		return nextPage;
	}
	
}
