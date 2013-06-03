package com.kmware.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeoutFilter implements Filter {

	private static final String TIMEOUT_PAGE = "LoginForm.jsp";
	private static final String LOGIN_PAGE = "LoginForm.jsp";

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if ((request instanceof HttpServletRequest)
				&& (response instanceof HttpServletResponse)) {
			HttpServletRequest requestHttp = (HttpServletRequest) request;
			HttpServletResponse responseHttp = (HttpServletResponse) response;
			if (checkResource(requestHttp)) {
				String requestPath = requestHttp.getRequestURI();
				 String facesRequestHeader = requestHttp
				            .getHeader( "Faces-Request" );

				if (checkSession(requestHttp)) {
					boolean isAjaxRequest = facesRequestHeader != null
				            && facesRequestHeader.equals( "partial/ajax" );
					String timeoutUrl = requestHttp.getContextPath() + "/"
							+ TIMEOUT_PAGE;
				    if( isAjaxRequest )
				    {
				            String url = MessageFormat.format( "{0}://{1}:{2,number,####0}{3}",
				            request.getScheme(), request.getServerName(),
				            request.getServerPort(), timeoutUrl );

				            PrintWriter pw = response.getWriter();
				                pw.println( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
				            pw.println( "<partial-response><redirect url=\"" + url
				            + "\"></redirect></partial-response>" );
				            pw.flush();
				    }
				    else{
						responseHttp.sendRedirect(timeoutUrl);
				    }
					return;
				}
			}
			filterChain.doFilter(request, response);
		}
	}

	private boolean checkResource(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		return !(requestPath.contains(TIMEOUT_PAGE)
				|| requestPath.contains(LOGIN_PAGE) || requestPath
					.equals(request.getContextPath() + "/"));
	}

	private boolean checkSession(HttpServletRequest request) {
		return request.getRequestedSessionId() != null
				&& !request.isRequestedSessionIdValid();
	}

	public void destroy() {
	}

}
