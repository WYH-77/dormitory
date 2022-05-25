package com.dormitory.service;

import com.dormitory.dto.SystemAdminDto;

public interface SystemAdminService {
     SystemAdminDto login(String username, String password);
}
