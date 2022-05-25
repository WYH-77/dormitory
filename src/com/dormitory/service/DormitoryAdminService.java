package com.dormitory.service;

import com.dormitory.dto.DormitoryAdminDto;
import com.dormitory.entity.DormitoryAdmin;

import java.util.List;

public interface DormitoryAdminService {
     List<DormitoryAdmin> list();

     List<DormitoryAdmin> search(String key, String value);

     void save(DormitoryAdmin dormitoryAdmin);

     void update(DormitoryAdmin dormitoryAdmin);

     void delete(Integer id);

     DormitoryAdminDto login(String username, String password);
}
