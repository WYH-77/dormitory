package com.dormitory.dao;

import com.dormitory.entity.Moveout;

import java.util.List;

public interface MoveoutDao {
    Integer save(Moveout moveout);

    List<Moveout> list();

    List<Moveout> search(String key, String value);
}
