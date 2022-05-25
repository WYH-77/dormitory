package com.dormitory.service;

import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Student;

import java.util.List;

public interface DormitoryService {
     List<Dormitory> availableList();

     List<Dormitory> list();

     List<Dormitory> search(String key, String value);

     void save(Dormitory dormitory);

     void update(Dormitory dormitory);

     void delete(Integer id);

     List<Dormitory> findByBuildingId(Integer buildingId);

    int getTotalCount(String key, String value);

     List<Dormitory> queryDormitoryByPage(int currentPage, int pageSize, String key, String value);
}
