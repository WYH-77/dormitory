package com.dormitory.entity;

public class Student {
    private Integer id;
    private String number;
    private String name;
    private String gender;
    private Integer dormitoryId;
    private String dormitoryName;
    private String photo;
    private String state;
    private String createDate;
    private String buildingName;
    
    public Student(Integer id, String number, String name, String gender, Integer dormitoryId, String dormitoryName, String buildingName,String state) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.dormitoryId = dormitoryId;
        this.dormitoryName = dormitoryName;
        this.buildingName = buildingName;
        this.state = state;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }



    public Student(Integer id, String number, String name, String gender, String dormitoryName, String buildingName,String state ) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.dormitoryName = dormitoryName;
        this.buildingName = buildingName;
        this.state = state;

    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(Integer id, String number, String name, String gender, Integer dormitoryId) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.dormitoryId = dormitoryId;
    }

    public Student(String number, String name, String gender, Integer dormitoryId) {
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.dormitoryId = dormitoryId;
    }
    public Student(String number, String name, String gender, Integer dormitoryId,String photo) {
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.dormitoryId = dormitoryId;
        this.photo=photo;
    }

    public Student(Integer id, String number, String name, String gender, Integer dormitoryId, String buildingName, String state) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.dormitoryId = dormitoryId;
        this.buildingName = buildingName;
        this.state = state;
    }

    public Student(Integer id, String number, String name, String gender,String photo, Integer dormitoryId, String dormitoryName, String state, String createDate) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.photo=photo;
        this.dormitoryId = dormitoryId;
        this.dormitoryName = dormitoryName;
        this.state = state;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDormitoryId() {
        return dormitoryId;
    }

    public void setDormitoryId(Integer dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public String getDormitoryName() {
        return dormitoryName;
    }

    public void setDormitoryName(String dormitoryName) {
        this.dormitoryName = dormitoryName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
