package com.dormitory.servlet;

import com.dormitory.dto.DormitoryAdminDto;
import com.dormitory.dto.SystemAdminDto;
import com.dormitory.service.DormitoryAdminService;
import com.dormitory.service.SystemAdminService;
import com.dormitory.service.impl.DormitoryAdminServiceImpl;
import com.dormitory.service.impl.SystemAdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private SystemAdminService systemAdminService = new SystemAdminServiceImpl();
    private DormitoryAdminService dormitoryAdminService = new DormitoryAdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method){
            case "login":
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String type = req.getParameter("type");
                switch (type){
                    case "systemAdmin":
                        SystemAdminDto systemAdminDto = this.systemAdminService.login(username, password);
                        switch (systemAdminDto.getCode()){
                            case -1:
                                req.setAttribute("usernameError", "�û���������");
                                req.getRequestDispatcher("login.jsp").forward(req, resp);
                                break;
                            case -2:
                                req.setAttribute("passwordError", "�������");
                                req.getRequestDispatcher("login.jsp").forward(req, resp);
                                break;
                            case 0:
                                //��ת����¼�ɹ�����
                                HttpSession session = req.getSession();
                                session.setAttribute("systemAdmin", systemAdminDto.getSystemAdmin());
                                resp.sendRedirect("/systemadmin.jsp");
                                break;
                        }
                        break;
                    case "dormitoryAdmin":
                        DormitoryAdminDto dormitoryAdminDto = this.dormitoryAdminService.login(username, password);
                        switch (dormitoryAdminDto.getCode()){
                            case -1:
                                req.setAttribute("usernameError", "�û���������");
                                req.getRequestDispatcher("login.jsp").forward(req, resp);
                                break;
                            case -2:
                                req.setAttribute("passwordError", "�������");
                                req.getRequestDispatcher("login.jsp").forward(req, resp);
                                break;
                            case 0:
                                //��ת����¼�ɹ�����
                                HttpSession session = req.getSession();
                                session.setAttribute("dormitoryAdmin", dormitoryAdminDto.getDormitoryAdmin());
                                resp.sendRedirect("/dormitoryadmin.jsp");
                                break;
                        }
                        break;
                }
                break;
            case "logout":
                req.getSession().invalidate();
                resp.sendRedirect("/login.jsp");
                break;
        }

    }
}