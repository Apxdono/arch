package com.kmware.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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

	/**
	 * The logic of the servlet
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//Check Session
		if (!checkSession(request)) {
			//Check if we not are trying to login
			String login = (String) request.getParameter("j_username");
			if (login == null || (login + "").trim().length() < 2) {
				//Check if the origin of request is a partial ajax reques
				String facesRequest = request.getHeader("Faces-Request");
				if (facesRequest != null && facesRequest.equals("partial/ajax")) {
					sendSessionExpirationResponse(request, response);
				} else {
					redirectToLoginForm(request, response);
				}
			} else {
				performLogin(request, response);
			}
		} else {
			continueBrowsing(request, response);
		}
	}
	
	/**
	 * Redirect to a page where u came from or to a contextPath
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void continueBrowsing(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String origin = (String) request.getAttribute("javax.servlet.forward.request_uri");
		if (origin != null) {
			response.sendRedirect(origin);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}
	
	/**
	 * Try to login
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void performLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		String login = (String) request.getParameter("j_username");
		String password = (String) request.getParameter("j_password");
		String origin = (String) request.getAttribute("javax.servlet.forward.request_uri");
		request.login(login, password);
		if(request.getSession()!=null && request.getUserPrincipal()!=null){
			continueBrowsing(request, response);
		} else {
			redirectToLoginForm(request, response);
		}
		
	}
	
	/**
	 * Send a session expiration response to an ajax request
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void sendSessionExpirationResponse(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		PrintWriter pw = response.getWriter();
		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<partial-response><extension scripts=\"\" data=\"kmsessionExpired\" type=\"sessionExpiration\" portion=\"\" ></extension></partial-response>");
		pw.flush();
	}

	/**
	 * Redirect to login form
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void redirectToLoginForm(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect(request.getContextPath()
				+ "/LoginForm.jsp");
	}
	
	/**
	 * Check if session is created and not expired
	 * @param request
	 * @return
	 */
	private boolean checkSession(HttpServletRequest request) {
		return request.getRequestedSessionId() != null
				&& !request.isRequestedSessionIdValid();
	}

}