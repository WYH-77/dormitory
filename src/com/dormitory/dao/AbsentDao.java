package com.dormitory.dao;

import com.dormitory.entity.Absent;

import java.util.List;

public interface AbsentDao {
    Integer save(Absent absent);

    List<Absent> list();

    List<Absent> search(String key, String value);
}
