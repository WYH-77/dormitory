package com.dormitory.entity;

import com.dormitory.entity.Student;

import java.util.List;

/**
 * @Author Õı”Ó∫Ω
 * @Date 2022/4/24 18:12
 * @Description
 * @Version 1.0
 */
public class Page {
    public int getCurrentPage() {

        return currentPage;
    }

    public void setCurrentPage(int currentPage) {

        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : totalCount / this.pageSize + 1;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Page(int currentPage, int pageSize, int totalCount, int totalPage, List<Student> students) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.students = students;

    }

    public Page() {
    }

    private int currentPage;
    private int pageSize;
    private int totalCount;

    public List<Dormitory> getDormitoryList() {
        return dormitoryList;
    }

    public void setDormitoryList(List<Dormitory> dormitoryList) {
        this.dormitoryList = dormitoryList;
    }

    private int totalPage;
    private List<Student> students;
    private List<Dormitory> dormitoryList;

}
