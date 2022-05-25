package com.dormitory.servlet;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dormitory")
public class DormitoryServlet extends HttpServlet {

    private DormitoryService dormitoryService = new DormitoryServiceImpl();
    private BuildingService buildingService = new BuildingServiceImpl();
    private StudentService studentService = new StudentServiceImpl();

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
                    totalCount = dormitoryService.getTotalCount(key,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                p.setTotalCount(totalCount);
                int pageSize =7;
                p.setPageSize(pageSize);
                List<Dormitory> dormitoryList2 = dormitoryService.queryDormitoryByPage(currentPage,pageSize,key,value);
                p.setDormitoryList(dormitoryList2);
                req.setAttribute("page", p);
               /* req.setAttribute("list", this.dormitoryService.list());*/
                req.setAttribute("buildingList", this.buildingService.list());
                req.getRequestDispatcher("dormitorymanager.jsp").forward(req, resp);
                break;
          /*  case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("list", this.dormitoryService.search(key,value ));
                req.setAttribute("buildingList", this.buildingService.list());
                req.getRequestDispatcher("dormitorymanager.jsp").forward(req, resp);
                break;*/
            case "save":
                String buildingIdStr = req.getParameter("buildingId");
                Integer buildingId = Integer.parseInt(buildingIdStr);
                String name = req.getParameter("name");
                String typeStr = req.getParameter("type");
                Integer type = Integer.parseInt(typeStr);
                String telephone = req.getParameter("telephone");
                this.dormitoryService.save(new Dormitory(buildingId, name, type, type, telephone));
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "update":
                String idStr = req.getParameter("id");
                Integer id = Integer.parseInt(idStr);
                name = req.getParameter("name");
                telephone = req.getParameter("telephone");
                this.dormitoryService.update(new Dormitory(id, name, telephone));
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "delete":
                idStr = req.getParameter("id2");
                id = Integer.parseInt(idStr);
                this.dormitoryService.delete(id);
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "findByBuildingId":
                buildingIdStr = req.getParameter("buildingId");
                buildingId = Integer.parseInt(buildingIdStr);
                List<Dormitory> dormitoryList = this.dormitoryService.findByBuildingId(buildingId);
                List<Student> studentList = this.studentService.findByDormitoryId(dormitoryList.get(0).getId());
                Map<String,List> map = new HashMap<>();
                map.put("dormitoryList",dormitoryList);
                map.put("studentList",studentList);
                JSONArray jsonArray = JSONArray.fromObject(map);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray.toString());
                break;
        }
    }
}
