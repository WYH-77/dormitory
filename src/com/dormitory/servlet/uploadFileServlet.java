package com.dormitory.servlet; /**
 * @Author ���
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
        /*����DiskFileItemFactory��������*/
       /* request.setAttribute("username",request.getParameter("username"));
        request.setAttribute("password",request.getParameter("password"));
        request.setAttribute("name",request.getParameter("name"));
        request.setAttribute("gender",request.getParameter("gender"));
        request.setAttribute("telephone",request.getParameter("telephone"));*/
        DiskFileItemFactory factory=new DiskFileItemFactory();
       /*���û���Ŀ¼,��������ڣ��ʹ���һ��*/
        File f=new File("D:\\IDEA");
       if (!f.exists()){
           f.mkdirs();
       }
       /*�����ļ�����·��*/
       factory.setRepository(f);
       /*����ServletFileUpload����*/
        ServletFileUpload fileUpload=new ServletFileUpload(factory);
        /*�����ַ�����*/
        fileUpload.setHeaderEncoding("utf-8");
       /*   //�����ϴ���С 10kb
         fileUpload.setSizeMax(1024*10); ��void ����,�޷��ж�   1kb=1024b
         */
            List<FileItem> fileItems = null;
            try {
                /*����request���õ��ϴ��ļ���FileItem����*/
                fileItems = fileUpload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        /*��������*/
            for (FileItem fileItem : fileItems) {
                /*����ϴ��ļ�����10kb ������ת����jspҳ��*/
                if (fileItem.getSize() > 10240) {
                    JOptionPane.showMessageDialog(null, "ͼƬ̫��,������ѡ��");
                    request.getRequestDispatcher("adminmanager.jsp").forward(request, response);
                } else{
                    /*�ж��Ƿ�Ϊ��ͨ�ֶ�*/
                    if (fileItem.isFormField()) {
                        /*����ֶ������ֶ�ֵ*/
                    String name = fileItem.getFieldName();
                        String value=null;
                        /*�ļ���Ϊ�գ�������value��*/
                        if (!fileItem.getString().equals("")) {
                             value = fileItem.getString("utf-8");
                        }

                    map.put(name,value);
                    request.setAttribute("map",map);
                } else {
                        /*��ȡ�ϴ��ļ���*/
                    String filename = fileItem.getName();
                    /*�����ϴ��ļ���*/
                    if (filename != null && !filename.equals("")) {
                        /*   writer.print("�ϴ������ƣ�"+filename+"<br/>");*/
                      /*��ȡ�ļ���*/
                        filename = filename.substring(filename.lastIndexOf("\\") + 1);
                      /*  //��ֹ�ļ����ظ� �����������ʹ�ļ���Ψһ
                        filename = UUID.randomUUID().toString() + "_" + filename;*/
                        request.setAttribute("filename", filename);
                       /*�ڷ���������ͬ���ļ�*/
                        String webPath = "/photo/";
                        /*�����������ļ�·�������ļ�����ϳ������ķ�������·��*/
                        String filepath = getServletContext().getRealPath(webPath + filename);
                       /*�����ļ�*/
                        File file = new File(filepath);
                        file.createNewFile();
                        /*����ϴ��ļ���*/
                        InputStream in = fileItem.getInputStream();
                        /*ʹ��FileOutputStream�򿪷������˵��ϴ��ļ�*/
                        FileOutputStream out = new FileOutputStream(file);
                        byte[] buffer = new byte[1024]; //ÿ�ζ�ȡһ���ֽ�
                        int len;
                        /*��ʼ��ȡ�ϴ��ļ����ֽڣ�������������������˵��ϴ��ļ��������*/
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        /*�ر���*/
                        in.close();
                        out.close();
                        /*ɾ����ʱ�ļ�*/
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
