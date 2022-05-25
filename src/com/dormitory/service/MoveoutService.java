package com.dormitory.service;

import com.dormitory.entity.Moveout;

import java.util.List;

public interface MoveoutService {
     void save(Moveout moveout);

     List<Moveout> list();

     List<Moveout> search(String key, String value);
}
