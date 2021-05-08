package com.zhuyl10.dao.impl;

import com.zhuyl10.dao.StudentDao;
import com.zhuyl10.domain.Student;

/**
 * @author zhuyl10
 */
public class StudentDaoImpl implements StudentDao {

    public StudentDaoImpl() {
        System.out.println("对象创建了");
    }

    public Student getStudentById(int id) {
        return new Student("国服最强西施",30,30);
    }
}
