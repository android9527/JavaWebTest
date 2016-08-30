package com.servlet.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gacl
 *         文件下载
 */
public class ResponseDemo02 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        downloadFileByOutputStream(response);//下载文件，通过OutputStream流
        downloadFileByPrintWriter(response);
    }

    /**
     * 下载文件，通过OutputStream流
     *
     * @param response
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void downloadFileByOutputStream(HttpServletResponse response)
            throws FileNotFoundException, IOException {
        //1.获取要下载的文件的绝对路径
        System.out.println(this.getServletContext().getContextPath());
        String realPath = this.getServletContext().getRealPath("/download/测试123.jpg");
        //2.获取要下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf("/") + 1);
        //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        //设置content-disposition响应头控制浏览器以下载的形式打开文件，中文文件名要使用URLEncoder.encode方法进行编码，否则会出现文件名乱码
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//        response.setHeader("content-disposition", "attachment;filename="+fileName);
        //4.获取要下载的文件输入流
        InputStream in = new FileInputStream(realPath);
        int len = 0;
        //5.创建数据缓冲区
        byte[] buffer = new byte[1024];
        //6.通过response对象获取OutputStream流
        OutputStream out = response.getOutputStream();
        //7.将FileInputStream流写入到buffer缓冲区
        while ((len = in.read(buffer)) > 0) {
            //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
            out.write(buffer, 0, len);
        }
        in.close();
    }

    /**
     * 下载文件，通过PrintWriter流，虽然也能够实现下载，但是会导致数据丢失，因此不推荐使用PrintWriter流下载文件
     *
     * @param response
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void downloadFileByPrintWriter(HttpServletResponse response)
            throws FileNotFoundException, IOException {
        String realPath = this.getServletContext().getRealPath("/download/测试123测试.jpg");//获取要下载的文件的绝对路径
        String fileName = realPath.substring(realPath.lastIndexOf("/") + 1);//获取要下载的文件名
        //设置content-disposition响应头控制浏览器以下载的形式打开文件，中文文件名要使用URLEncoder.encode方法进行编码
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        FileReader in = new FileReader(realPath);
        int len = 0;
        char[] buffer = new char[1024];
        PrintWriter out = response.getWriter();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);//将缓冲区的数据输出到客户端浏览器
        }
        in.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}