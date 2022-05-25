package com.dormitory.servlet;

import com.dormitory.entity.DormitoryAdmin;
import com.dormitory.service.DormitoryAdminService;
import com.dormitory.service.impl.DormitoryAdminServiceImpl;
import org.apache.commons.fileupload.FileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dormitoryAdmin")
public class DormitoryAdminServlet extends HttpServlet {

    private DormitoryAdminService dormitoryAdminService = new DormitoryAdminServiceImpl();

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
                req.setAttribute("list", this.dormitoryAdminService.list());
                req.getRequestDispatcher("adminmanager.jsp").forward(req, resp);
            break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("list", this.dormitoryAdminService.search(key, value));
                req.getRequestDispatcher("adminmanager.jsp").forward(req, resp);
                break;
            case "save":
                Map<String,String> map =(Map) req.getAttribute("map");
                String username = map.get("username");
                String password = map.get("password");
                String name = map.get("name");
                String gender = map.get("gender");
                String photo = (String) req.getAttribute("filename");
                String telephone= map.get("telephone");

                this.dormitoryAdminService.save(new DormitoryAdmin(username, password, name, gender,photo,telephone));
                resp.sendRedirect("/dormitoryAdmin?method=list");
                break;
            case "update":
                String idStr = req.getParameter("id");
                Integer id = Integer.parseInt(idStr);
                username = req.getParameter("username");
                password = req.getParameter("password");
                name = req.getParameter("name");
                gender = req.getParameter("gender");
                telephone = req.getParameter("telephone");
                this.dormitoryAdminService.update(new DormitoryAdmin(id, username, password, name, gender, telephone));
                resp.sendRedirect("/dormitoryAdmin?method=list");
                break;
            case "delete":
                idStr = req.getParameter("id2");
                id = Integer.parseInt(idStr);
                this.dormitoryAdminService.delete(id);
                resp.sendRedirect("/dormitoryAdmin?method=list");
                break;
        }
    }
}
