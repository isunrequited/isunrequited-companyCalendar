package com.twotrillion.comcal.user.employee.controller;

import com.twotrillion.comcal.user.employee.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;
}
