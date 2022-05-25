package com.dormitory.servlet;

import com.dormitory.entity.Building;
import com.dormitory.entity.Dormitory;
import com.dormitory.entity.Page;
import com.dormitory.entity.Student;
import com.dormitory.service.BuildingService;
import com.dormitory.service.DormitoryService;
import com.dormitory.service.StudentService;
import com.dormitory.service.impl.BuildingServiceImpl;
import com.dormitory.service.impl.DormitoryServiceImpl;
import com.dormitory.service.impl.StudentServiceImpl;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private BuildingService buildingService = new BuildingServiceImpl();
    private StudentService studentService = new StudentServiceImpl();
    private DormitoryService dormitoryService = new DormitoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        switch (method){
            case "list":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("key",key);
                req.setAttribute("value",value);
                List<Building> buildingList = this.buildingService.list();
                List<Dormitory> dormitoryList2 = this.dormitoryService.findByBuildingId(buildingList.get(0).getId());
                req.setAttribute("buildingList", buildingList);
                req.setAttribute("dormitoryList2", dormitoryList2);
                Page p=new Page();
                String cPage = req.getParameter("currentPage");
                if (cPage == null) {
                    cPage = "1";
                }
                int currentPage = Integer.parseInt(cPage);
                p.setCurrentPage(currentPage);
                int totalCount = 0;
                try {
                    totalCount = studentService.getTotalCount(key,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                p.setTotalCount(totalCount);
                int pageSize =7;
                p.setPageSize(pageSize);
                List<Student> students = studentService.queryStudentByPage(currentPage, pageSize,key,value);
                p.setStudents(students);
                req.setAttribute("page", p);
                req.setAttribute("dormitoryList", this.dormitoryService.availableList());
                req.getRequestDispatcher("studentmanager.jsp").forward(req, resp);
                break;
           /* case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                p = new Page();
                cPage = req.getParameter("currentPage");
                if (cPage == null) {
                    cPage = "1";
                }
                currentPage = Integer.parseInt(cPage);
                p.setCurrentPage(currentPage);
                totalCount = 0;
                try {
                    totalCount = studentService.getTotalCount();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                p.setTotalCount(totalCount);
                pageSize = 7;
                p.setPageSize(pageSize);
                req.setAttribute("list", this.studentService.list());
                List<Student> querystudents = studentService.search(currentPage,pageSize,key,value);
                p.setStudents(querystudents);
                req.setAttribute("page", p);
*//*
                req.setAttribute("list", this.studentService.search(key, value));
*//*
                req.setAttribute("dormitoryList", this.dormitoryService.availableList());
                req.getRequestDispatcher("studentmanager.jsp").forward(req, resp);
                break;*/
            case "save":
                Map<String,String> map = (Map) req.getAttribute("map");
               String dormitoryStr =map.get("dormitoryId");
                Integer dormitoryId=Integer.parseInt(dormitoryStr);
                String number=map.get("number");
                String name=map.get("name");
                String gender =map.get("gender");
                String photo = (String) req.getAttribute("filename");
                this.studentService.save(new Student(number, name, gender, dormitoryId,photo));
                resp.sendRedirect("/student?method=list");
                break;
            case "update":
                String idStr = req.getParameter("id");
                Integer id = Integer.parseInt(idStr);
                String dormitoryIdStr2 = req.getParameter("dormitoryId");
                dormitoryId = Integer.parseInt(dormitoryIdStr2);
                number = req.getParameter("number"); 
                name = req.getParameter("name");
                gender = req.getParameter("gender");
                String oldDormitoryIdStr = req.getParameter("oldDormitoryId");
                Integer oldDormitoryId = Integer.parseInt(oldDormitoryIdStr);
                this.studentService.update(new Student(id, number, name, gender, dormitoryId),oldDormitoryId);
                resp.sendRedirect("/student?method=list");
                break;
            case "delete":
                String idStr2 = req.getParameter("id2");
               Integer id2 = Integer.parseInt(idStr2);
                String dormitoryIdStr3 = req.getParameter("dormitoryId");
                dormitoryId = Integer.parseInt(dormitoryIdStr3);
                this.studentService.delete(id2, dormitoryId);
                resp.sendRedirect("/student?method=list");
                break;
            case "findByDormitoryId":
                String dormitoryIdStr4 = req.getParameter("dormitoryId");
                dormitoryId = Integer.parseInt(dormitoryIdStr4);
                List<Student> studentList = this.studentService.findByDormitoryId(dormitoryId);
                JSONArray jsonArray = JSONArray.fromObject(studentList);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray.toString());
                break;
        }
    }
}
