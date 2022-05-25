package com.dormitory.service.impl;

import com.dormitory.dao.DormitoryDao;
import com.dormitory.dao.StudentDao;
import com.dormitory.dao.impl.DormitoryDaoImpl;
import com.dormitory.dao.impl.StudentDaoImpl;
import com.dormitory.entity.Student;
import com.dormitory.service.StudentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao = new StudentDaoImpl();
    private DormitoryDao dormitoryDao = new DormitoryDaoImpl();

    @Override
    public List<Student> list() {
        return this.studentDao.list();
    }

    @Override
    public List<Student> search(int currentPage,int pageSize,String key, String value) {
        if(value.equals("")) return this.studentDao.queryStudentByPage(currentPage,pageSize,key,value);
        return this.studentDao.search(currentPage,pageSize,key, value);
    }

    @Override
    public void save(Student student) {
        student.setState("入住");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        student.setCreateDate(simpleDateFormat.format(date));
        Integer save = this.studentDao.save(student);
        Integer sub = this.dormitoryDao.subAvailable(student.getDormitoryId());
        if(save != 1 || sub != 1) throw new RuntimeException("添加学生信息失败");
    }

    @Override
    public void update(Student student,Integer oldDormitoryId) {
        Integer update = this.studentDao.update(student);
        //原宿舍available+1，新宿舍available-1
        Integer dormitory1 = this.dormitoryDao.addAvailable(oldDormitoryId);
        Integer dormitory2 = this.dormitoryDao.subAvailable(student.getDormitoryId());
        if(update != 1 || dormitory1 != 1 || dormitory2 != 1) throw new RuntimeException("更新学生信息失败");
    }

    @Override
    public void delete(Integer id, Integer dormitoryId) {
        Integer delete = this.studentDao.delete(id);
        Integer available = this.dormitoryDao.addAvailable(dormitoryId);
        if(delete != 1 || available != 1) throw new RuntimeException("删除学生信息失败");
    }

    @Override
    public List<Student> moveoutList(int currentPage,int pageSize,String key,String value) {
        return this.studentDao.moveoutList(currentPage,pageSize,key,value);
    }

    @Override
    public List<Student> searchForMoveout(int currentPage,int pageSize ,String key, String value) {
        if(value.equals("")) return this.studentDao.moveoutList(currentPage,pageSize,key,value);
        return this.studentDao.searchForMoveout(key, value);
    }

    @Override
    public List<Student> findByDormitoryId(Integer dormitoryId) {
        return this.studentDao.findByDormitoryId(dormitoryId);
    }

    @Override
    public int getTotalCount(String key,String value) throws Exception {
        return studentDao.getTotalCount(key,value);
    }

    @Override
    public List<Student> queryStudentByPage(int currentPage, int pageSize,String key,String value) {
        return studentDao.queryStudentByPage(currentPage,pageSize,key,value);
    }

}
