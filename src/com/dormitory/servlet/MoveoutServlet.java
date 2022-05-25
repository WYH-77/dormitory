package com.dormitory.servlet;

import com.dormitory.entity.Moveout;
import com.dormitory.entity.Page;
import com.dormitory.entity.Student;
import com.dormitory.service.MoveoutService;
import com.dormitory.service.StudentService;
import com.dormitory.service.impl.MoveoutServiceImpl;
import com.dormitory.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/moveout")
public class MoveoutServlet extends HttpServlet {

    private StudentService studentService = new StudentServiceImpl();
    private MoveoutService moveoutService = new MoveoutServiceImpl();

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
                List<Student> students = studentService.moveoutList(currentPage,pageSize,key,value);
                p.setStudents(students);
                req.setAttribute("page",p);
                req.getRequestDispatcher("moveoutregister.jsp").forward(req, resp);
                break;
            case "moveout":
                String studentIdStr = req.getParameter("studentId");
                Integer studentId = Integer.parseInt(studentIdStr);
                String dormitoryIdStr = req.getParameter("dormitoryId");
                Integer dormitoryId = Integer.parseInt(dormitoryIdStr);
                String dormitoryName = req.getParameter("dormitoryName");
                String reason = req.getParameter("reason");
                this.moveoutService.save(new Moveout(studentId,dormitoryId,dormitoryName,reason));
                resp.sendRedirect("/moveout?method=list");
                break;
            /*case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("list", this.studentService.searchForMoveout(key, value));
                req.getRequestDispatcher("moveoutregister.jsp").forward(req, resp);
                break;*/
            case "record":
                req.setAttribute("list", this.moveoutService.list());
                req.getRequestDispatcher("moveoutrecord.jsp").forward(req, resp);
                break;
            case "recordSearch":
                key = req.getParameter("key");
                value = req.getParameter("value");
                req.setAttribute("list", this.moveoutService.search(key, value));
                req.getRequestDispatcher("moveoutrecord.jsp").forward(req, resp);
                break;
        }
    }
}
