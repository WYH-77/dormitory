package com.dormitory.dao;

import com.dormitory.entity.SystemAdmin;

public interface SystemAdminDao {
     SystemAdmin findByUsername(String username);
}
