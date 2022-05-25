package com.dormitory.servlet; /**
 * @Author 王宇航
 * @Date 2022/4/28 22:27
 * @Description
 * @Version 1.0
 */

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "uploadFileServlet", value = "/uploadFileServlet")
/*@MultipartConfig*/
public class uploadFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String> map=new HashMap<>();
        /* response.setContentType("text/html;charset=utf-8"); */
        /*创建DiskFileItemFactory工厂对象*/
       /* request.setAttribute("username",request.getParameter("username"));
        request.setAttribute("password",request.getParameter("password"));
        request.setAttribute("name",request.getParameter("name"));
        request.setAttribute("gender",request.getParameter("gender"));
        request.setAttribute("telephone",request.getParameter("telephone"));*/
        DiskFileItemFactory factory=new DiskFileItemFactory();
       /*设置缓存目录,如果不存在，就创建一个*/
        File f=new File("D:\\IDEA");
       if (!f.exists()){
           f.mkdirs();
       }
       /*设置文件缓存路径*/
       factory.setRepository(f);
       /*创建ServletFileUpload对象*/
        ServletFileUpload fileUpload=new ServletFileUpload(factory);
        /*设置字符编码*/
        fileUpload.setHeaderEncoding("utf-8");
       /*   //限制上传大小 10kb
         fileUpload.setSizeMax(1024*10); 是void 方法,无法判断   1kb=1024b
         */
            List<FileItem> fileItems = null;
            try {
                /*解析request，得到上传文件的FileItem对象*/
                fileItems = fileUpload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        /*遍历集合*/
            for (FileItem fileItem : fileItems) {
                /*如果上传文件大于10kb 就请求转发到jsp页面*/
                if (fileItem.getSize() > 10240) {
                    JOptionPane.showMessageDialog(null, "图片太大,请重新选择");
                    request.getRequestDispatcher("adminmanager.jsp").forward(request, response);
                } else{
                    /*判断是否为普通字段*/
                    if (fileItem.isFormField()) {
                        /*获得字段名和字段值*/
                    String name = fileItem.getFieldName();
                        String value=null;
                        /*文件不为空，保存在value中*/
                        if (!fileItem.getString().equals("")) {
                             value = fileItem.getString("utf-8");
                        }

                    map.put(name,value);
                    request.setAttribute("map",map);
                } else {
                        /*获取上传文件名*/
                    String filename = fileItem.getName();
                    /*处理上传文件名*/
                    if (filename != null && !filename.equals("")) {
                        /*   writer.print("上传的名称："+filename+"<br/>");*/
                      /*截取文件名*/
                        filename = filename.substring(filename.lastIndexOf("\\") + 1);
                      /*  //防止文件名重复 此项操作可以使文件名唯一
                        filename = UUID.randomUUID().toString() + "_" + filename;*/
                        request.setAttribute("filename", filename);
                       /*在服务器创建同名文件*/
                        String webPath = "/photo/";
                        /*将服务器中文件路径夹与文件名组合成完整的服务器段路径*/
                        String filepath = getServletContext().getRealPath(webPath + filename);
                       /*创建文件*/
                        File file = new File(filepath);
                        file.createNewFile();
                        /*获得上传文件流*/
                        InputStream in = fileItem.getInputStream();
                        /*使用FileOutputStream打开服务器端的上传文件*/
                        FileOutputStream out = new FileOutputStream(file);
                        byte[] buffer = new byte[1024]; //每次读取一个字节
                        int len;
                        /*开始读取上传文件的字节，并将其输出到服务器端的上传文件输出流中*/
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        /*关闭流*/
                        in.close();
                        out.close();
                        /*删除临时文件*/
                        fileItem.delete();
                    }
                }
            }
            }
        String identity = request.getParameter("identity");
            if (identity.equals("student")){
                request.getRequestDispatcher("/student?method=save").forward(request, response);
            }else if (identity.equals("adminmanager")){
                request.getRequestDispatcher("/dormitoryAdmin?method=save").forward(request, response);
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doGet(request,response);

    }
}
