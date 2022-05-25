package com.dormitory.dao;

import com.dormitory.entity.Dormitory;

import java.util.List;

public interface DormitoryDao {
    List<Dormitory> list();

    List<Dormitory> search(String key, String value);

    List<Dormitory> availableList();

    Integer subAvailable(Integer id);

    Integer addAvailable(Integer id);

    List<Integer> findDormitoryIdByBuildingId(Integer id);

    Integer availableId();

    Integer deleteById(Integer id);

    Integer save(Dormitory dormitory);

    Integer update(Dormitory dormitory);

    List<Dormitory> findByBuildingId(Integer id);

    int getTotalCount(String key, String value);

    List<Dormitory> queryDormitoryByPage(int currentPage, int pageSize, String key, String value);
}
