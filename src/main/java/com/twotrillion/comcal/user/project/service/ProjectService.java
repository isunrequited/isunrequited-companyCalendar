package com.twotrillion.comcal.user.project.service;

import com.twotrillion.comcal.user.project.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    ProjectDao projectDao;
}
