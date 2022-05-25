package com.dormitory.service;

import com.dormitory.entity.Building;

import java.util.List;

public interface BuildingService {
    List<Building> list();

    List<Building> search(String key, String value);

    void save(Building building);

    void update(Building building);

    void delete(Integer id);
}
