package com.kmware.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
          private static final long serialVersionUID = 1L;
 
 
          protected void doGet(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
                    doService(request, response);
          }
 
 
          protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
                    doService(request, response);
          }
 
 
          protected void doService(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
                    String facesRequest = request.getHeader("Faces-Request");
                    if (facesRequest != null && facesRequest.equals("partial/ajax")) {
                              String url = MessageFormat.format(
                                                  "{0}://{1}:{2,number,####0}{3}/LoginForm.jsp",
                                                  request.getScheme(), request.getServerName(),
                                                  request.getServerPort(), request.getContextPath());
                              PrintWriter pw = response.getWriter();
                              pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                              pw.println("<partial-response><redirect url=\"" + url
                                                  + "\"></redirect></partial-response>");
                              pw.flush();
                    } else {
                              response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
                    }
          }
 
}