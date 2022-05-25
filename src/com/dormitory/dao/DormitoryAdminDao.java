package com.dormitory.dao;

import com.dormitory.entity.DormitoryAdmin;

import java.util.List;

public interface DormitoryAdminDao {
    List<DormitoryAdmin> list();

    List<DormitoryAdmin> search(String key, String value);

    Integer save(DormitoryAdmin dormitoryAdmin);

    Integer update(DormitoryAdmin dormitoryAdmin);

    Integer delete(Integer id);

    DormitoryAdmin findByUsername(String username);
}
