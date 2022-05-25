package com.dormitory.dao;

import com.dormitory.entity.Building;

import java.util.List;

public interface BuildingDao {
    List<Building> list();

    List<Building> search(String key, String value);

    Integer save(Building building);

    Integer update(Building building);

    Integer delete(Integer id);
}
