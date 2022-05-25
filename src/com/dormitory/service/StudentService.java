package com.dormitory.service;

import com.dormitory.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> list();

    List<Student> search(int currentPage,int pageSize,String key, String value);

    void save(Student student);

    void update(Student student, Integer oldDormitoryId);

    void delete(Integer id, Integer dormitoryId);

    List<Student> moveoutList(int currentPage,int pageSize,String key,String value);

    List<Student> searchForMoveout(int currentPage,int pageSize,String key, String value);

    List<Student> findByDormitoryId(Integer dormitoryId);

    int getTotalCount(String key,String value) throws Exception;

    List<Student> queryStudentByPage(int currentPage, int pageSize,String key, String value);
}
