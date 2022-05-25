package com.dormitory.service.impl;

import com.dormitory.dao.SystemAdminDao;
import com.dormitory.dao.impl.SystemAdminDaoImpl;
import com.dormitory.dto.SystemAdminDto;
import com.dormitory.entity.SystemAdmin;
import com.dormitory.service.SystemAdminService;

public class SystemAdminServiceImpl implements SystemAdminService {

    private SystemAdminDao systemAdminDao = new SystemAdminDaoImpl();

    @Override
    public SystemAdminDto login(String username, String password) {
        //1、通过username查询数据库
        //2、查询结果为空，username错误
        //3、查询结果不为空，再判断password是否正确
        SystemAdmin systemAdmin = this.systemAdminDao.findByUsername(username);
        SystemAdminDto systemAdminDto = new SystemAdminDto();
        if(systemAdmin == null){
            systemAdminDto.setCode(-1);
        } else {
            if(!systemAdmin.getPassword().equals(password)){
                systemAdminDto.setCode(-2);
            } else {
                systemAdminDto.setCode(0);
                systemAdminDto.setSystemAdmin(systemAdmin);
            }
        }
        return systemAdminDto;
    }
}
