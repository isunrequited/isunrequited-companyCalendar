package com.twotrillion.comcal.user.employee.service;

import com.twotrillion.comcal.user.employee.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberDao memberDao;
}
