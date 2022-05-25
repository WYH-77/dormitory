package com.dormitory.dao;

import com.dormitory.entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> list();

    List<Student> search(int currentPage,int pageSize,String key, String value);

    Integer save(Student student);

    Integer update(Student student);

    Integer delete(Integer id);

    List<Integer> findStudentIdByDormitoryId(Integer id);

    Integer updateDorimtory(Integer studentId, Integer dormitoryId);

    List<Student> moveoutList(int currentPage,int pageSize,String key,String value);

    List<Student> searchForMoveout(String key, String value);

    Integer updateStateById(Integer id);

    List<Student> findByDormitoryId(Integer id);

    int getTotalCount(String key,String value) throws Exception;

    List<Student> queryStudentByPage(int currentPage, int pageSize,String key,String value);
}
