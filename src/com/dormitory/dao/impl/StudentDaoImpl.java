package com.dormitory.dao.impl;

import com.dormitory.dao.StudentDao;
import com.dormitory.entity.Page;
import com.dormitory.entity.Student;
import com.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public List<Student> list() {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select s.id,s.number,s.name,s.gender,s.photo,s.dormitory_id,d.name,s.state,s.create_date from student s,dormitory d where s.dormitory_id = d.id and s.state='入住'";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Student> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String number = resultSet.getString(2);
                String name = resultSet.getString(3);
                String gender = resultSet.getString(4);
                String photo=resultSet.getString(5);
                Integer dormitoryId = resultSet.getInt(6);
                String dormitoryName = resultSet.getString(7);
                String state = resultSet.getString(8);
                String createDate = resultSet.getString(9);
                list.add(new Student(id, number, name, gender,photo, dormitoryId, dormitoryName, state, createDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }

    @Override
    public List<Student> search(int currentPage,int pageSize,String key, String value) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select s.id,s.number,s.name,s.gender,s.photo,s.dormitory_id,d.name,s.state,s.create_date from student s,dormitory d where s.state='入住' and s.dormitory_id = d.id and s." + key + " like '%" + value + "%' limit ?,?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Student> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,(currentPage-1)*pageSize);
            statement.setInt(2,pageSize);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String number = resultSet.getString(2);
                String name = resultSet.getString(3);
                String gender = resultSet.getString(4);
                String photo=resultSet.getString(5);
                Integer dormitoryId = resultSet.getInt(6);
                String dormitoryName = resultSet.getString(7);
                String state = resultSet.getString(8);
                String createDate = resultSet.getString(9);
                list.add(new Student(id, number, name, gender,photo, dormitoryId, dormitoryName, state, createDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }

    @Override
    public Integer save(Student student) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "insert into student(number,name,gender,dormitory_id,photo,state,create_date) values(?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        Integer result = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getNumber());
            statement.setString(2, student.getName());
            statement.setString(3, student.getGender());
            statement.setInt(4, student.getDormitoryId());
            statement.setString(5,student.getPhoto());
            statement.setString(6, student.getState());
            statement.setString(7, student.getCreateDate());
            result = statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, null);
        }
        return result;
    }

    @Override
    public Integer update(Student student) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "update student set number = ?,name = ?,gender = ?,dormitory_id = ? where id = ?";
        PreparedStatement statement = null;
        Integer result = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getNumber());
            statement.setString(2, student.getName());
            statement.setString(3, student.getGender());
            statement.setInt(4, student.getDormitoryId());
            statement.setInt(5, student.getId());
            result = statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, null);
        }
        return result;
    }

    @Override
    public Integer delete(Integer id) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "delete from student where id = "+id;
        PreparedStatement statement = null;
        Integer result = null;
        try {
            statement = connection.prepareStatement(sql);
            result = statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, null);
        }
        return result;
    }

    @Override
    public List<Integer> findStudentIdByDormitoryId(Integer id) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select id from student where dormitory_id =" + id;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }

    @Override
    public Integer updateDorimtory(Integer studentId, Integer dormitoryId) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "update student set dormitory_id = ? where id = ?";
        PreparedStatement statement = null;
        Integer result = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, dormitoryId);
            statement.setInt(2, studentId);
            result = statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, null);
        }
        return result;
    }

    @Override
    public List<Student> moveoutList(int currentPage,int pageSize,String key,String value) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "SELECT s.id,s.number,s.name,s.gender,d.id,d.name,b.name,s.state FROM student s,dormitory d,building b WHERE s.dormitory_id = d.id AND b.id=d.building_id AND s.state = '入住'";
        StringBuilder sb=new StringBuilder(sql);
        if (!"".equals(value) && value!=null){
            sb.append(" and s.").append(key).append(" like '%").append(value).append("%'");
        }
        sb.append(" limit ?,?");
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Student> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sb.toString());
            statement.setInt(1,(currentPage-1)*pageSize);
            statement.setInt(2,pageSize);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getInt(5),resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                list.add(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }

    @Override
    public List<Student> searchForMoveout(String key, String value) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select s.id,s.number,s.name,s.gender,d.name,b.name,s.state from student s,dormitory d,building b " +
                "where s.dormitory_id = d.id and b.id=d.building_id and s.state = '入住' and "+"s."+key+" like "+"'%"+value+"%'";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Student> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String number = resultSet.getString(2);
                String name = resultSet.getString(3);
                String gender = resultSet.getString(4);
                String dormitoryName= resultSet.getString(5);
                String  buildingName= resultSet.getString(6);
                String state = resultSet.getString(7);
                list.add(new Student(id, number, name, gender, dormitoryName,buildingName , state));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }

    @Override
    public Integer updateStateById(Integer id) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "update student set state = '迁出' where id = " + id;
        PreparedStatement statement = null;
        Integer result = null;
        try {
            statement = connection.prepareStatement(sql);
            result = statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, null);
        }
        return result;
    }

    @Override
    public List<Student> findByDormitoryId(Integer id) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "select id,name from student where state ='入住' and dormitory_id =" + id;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Student> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                list.add(new Student(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }

    @Override
    public int getTotalCount(String key,String value) throws Exception {
        String sql="select count(*) from student where 1=1 and state='入住'";
        StringBuilder sb=new StringBuilder(sql);
        if (!"".equals(value) && value!=null){
            sb.append(" and ").append(key).append(" like '%").append(value).append("%'");
        }
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement statement=connection.prepareStatement(sb.toString());
        ResultSet rs=statement.executeQuery();
        int count = 0;
        while (rs.next()){
             count = rs.getInt(1);
        }

        return count;
    }

    @Override
    public List<Student> queryStudentByPage(int currentPage, int pageSize,String key,String value) {
        String sql = "select s.id,s.number,s.name,s.gender,s.photo,s.dormitory_id,d.name,s.state,s.create_date from student s,dormitory d where s.dormitory_id = d.id and s.state='入住'";
       StringBuilder sb=new StringBuilder(sql);
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (!"".equals(value) && value!=null){
            sb.append(" and s.").append(key).append(" like '%").append(value).append("%'");
        }
        sb.append(" limit ?,?");
        List<Student> list=new ArrayList<>();
        try {
             statement = connection.prepareStatement(sb.toString());
             statement.setInt(1,(currentPage-1)*pageSize);
             statement.setInt(2,pageSize);
             resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String number = resultSet.getString(2);
                String name = resultSet.getString(3);
                String gender = resultSet.getString(4);
                String photo=resultSet.getString(5);
                Integer dormitoryId = resultSet.getInt(6);
                String dormitoryName = resultSet.getString(7);
                String state = resultSet.getString(8);
                String createDate = resultSet.getString(9);
                list.add(new Student(id, number, name, gender,photo, dormitoryId, dormitoryName, state, createDate));
          }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(connection,statement,resultSet);
        }
        return list;
    }
}
