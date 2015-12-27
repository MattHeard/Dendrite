/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Test;

import com.deuteriumlabs.dendrite.view.AltView;

public class AltViewTest {

    /**
     * @author Matt
     * 
     * A mock page context.
     */
    private class MockPageContext extends PageContext {

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#forward(java.lang.String)
         */
        @Override
        public void forward(String arg0) throws ServletException, IOException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#getException()
         */
        @Override
        public Exception getException() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#getPage()
         */
        @Override
        public Object getPage() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#getRequest()
         */
        @Override
        public ServletRequest getRequest() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#getResponse()
         */
        @Override
        public ServletResponse getResponse() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#getServletConfig()
         */
        @Override
        public ServletConfig getServletConfig() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#getServletContext()
         */
        @Override
        public ServletContext getServletContext() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#getSession()
         */
        @Override
        public HttpSession getSession() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#handlePageException(java.lang.Exception)
         */
        @Override
        public void handlePageException(Exception arg0)
                throws ServletException, IOException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#handlePageException(java.lang.Throwable)
         */
        @Override
        public void handlePageException(Throwable arg0)
                throws ServletException, IOException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#include(java.lang.String)
         */
        @Override
        public void include(String arg0) throws ServletException, IOException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#include(java.lang.String, boolean)
         */
        @Override
        public void include(String arg0, boolean arg1) throws ServletException,
                IOException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#initialize(javax.servlet.Servlet, javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.String, boolean, int, boolean)
         */
        @Override
        public void initialize(Servlet arg0, ServletRequest arg1,
                ServletResponse arg2, String arg3, boolean arg4, int arg5,
                boolean arg6) throws IOException, IllegalStateException,
                IllegalArgumentException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.PageContext#release()
         */
        @Override
        public void release() {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#findAttribute(java.lang.String)
         */
        @Override
        public Object findAttribute(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getAttribute(java.lang.String)
         */
        @Override
        public Object getAttribute(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getAttribute(java.lang.String, int)
         */
        @Override
        public Object getAttribute(String arg0, int arg1) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getAttributeNamesInScope(int)
         */
        @Override
        public Enumeration<String> getAttributeNamesInScope(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getAttributesScope(java.lang.String)
         */
        @Override
        public int getAttributesScope(String arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getELContext()
         */
        @Override
        public ELContext getELContext() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getExpressionEvaluator()
         */
        @SuppressWarnings("deprecation")
        @Override
        public javax.servlet.jsp.el.ExpressionEvaluator getExpressionEvaluator() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getOut()
         */
        @Override
        public JspWriter getOut() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#getVariableResolver()
         */
        @SuppressWarnings("deprecation")
        @Override
        public javax.servlet.jsp.el.VariableResolver getVariableResolver() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#removeAttribute(java.lang.String)
         */
        @Override
        public void removeAttribute(String arg0) {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#removeAttribute(java.lang.String, int)
         */
        @Override
        public void removeAttribute(String arg0, int arg1) {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#setAttribute(java.lang.String, java.lang.Object)
         */
        @Override
        public void setAttribute(String arg0, Object arg1) {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.jsp.JspContext#setAttribute(java.lang.String, java.lang.Object, int)
         */
        @Override
        public void setAttribute(String arg0, Object arg1, int arg2) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * @author Matt
     *
     * A mock request which only stores a "p" parameter.
     */
    private class MockReq implements HttpServletRequest {
        
        /**
         * @param p
         */
        public MockReq(final String p) {
            super();
            this.p = p;
        }

        private String p;

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
         */
        @Override
        public Object getAttribute(String arg0) {
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getAttributeNames()
         */
        @SuppressWarnings("rawtypes")
        @Override
        public Enumeration getAttributeNames() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getCharacterEncoding()
         */
        @Override
        public String getCharacterEncoding() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getContentLength()
         */
        @Override
        public int getContentLength() {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getContentType()
         */
        @Override
        public String getContentType() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getInputStream()
         */
        @Override
        public ServletInputStream getInputStream() throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getLocalAddr()
         */
        @Override
        public String getLocalAddr() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getLocalName()
         */
        @Override
        public String getLocalName() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getLocalPort()
         */
        @Override
        public int getLocalPort() {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getLocale()
         */
        @Override
        public Locale getLocale() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getLocales()
         */
        @SuppressWarnings("rawtypes")
        @Override
        public Enumeration getLocales() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
         */
        @Override
        public String getParameter(String arg0) {
            if ("p".equals(arg0)) {
                return p;
            } else {
                return null;
            }
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getParameterMap()
         */
        @SuppressWarnings("rawtypes")
        @Override
        public Map getParameterMap() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getParameterNames()
         */
        @SuppressWarnings("rawtypes")
        @Override
        public Enumeration getParameterNames() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
         */
        @Override
        public String[] getParameterValues(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getProtocol()
         */
        @Override
        public String getProtocol() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getReader()
         */
        @Override
        public BufferedReader getReader() throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
         */
        @Override
        public String getRealPath(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getRemoteAddr()
         */
        @Override
        public String getRemoteAddr() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getRemoteHost()
         */
        @Override
        public String getRemoteHost() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getRemotePort()
         */
        @Override
        public int getRemotePort() {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
         */
        @Override
        public RequestDispatcher getRequestDispatcher(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getScheme()
         */
        @Override
        public String getScheme() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getServerName()
         */
        @Override
        public String getServerName() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#getServerPort()
         */
        @Override
        public int getServerPort() {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#isSecure()
         */
        @Override
        public boolean isSecure() {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
         */
        @Override
        public void removeAttribute(String arg0) {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#setAttribute(java.lang.String, java.lang.Object)
         */
        @Override
        public void setAttribute(String arg0, Object arg1) {
            // TODO Auto-generated method stub
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
         */
        @Override
        public void setCharacterEncoding(String arg0)
                throws UnsupportedEncodingException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getAuthType()
         */
        @Override
        public String getAuthType() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getContextPath()
         */
        @Override
        public String getContextPath() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getCookies()
         */
        @Override
        public Cookie[] getCookies() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
         */
        @Override
        public long getDateHeader(String arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
         */
        @Override
        public String getHeader(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
         */
        @SuppressWarnings("rawtypes")
        @Override
        public Enumeration getHeaderNames() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
         */
        @SuppressWarnings("rawtypes")
        @Override
        public Enumeration getHeaders(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
         */
        @Override
        public int getIntHeader(String arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getMethod()
         */
        @Override
        public String getMethod() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getPathInfo()
         */
        @Override
        public String getPathInfo() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
         */
        @Override
        public String getPathTranslated() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getQueryString()
         */
        @Override
        public String getQueryString() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
         */
        @Override
        public String getRemoteUser() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getRequestURI()
         */
        @Override
        public String getRequestURI() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getRequestURL()
         */
        @Override
        public StringBuffer getRequestURL() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
         */
        @Override
        public String getRequestedSessionId() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getServletPath()
         */
        @Override
        public String getServletPath() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getSession()
         */
        @Override
        public HttpSession getSession() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
         */
        @Override
        public HttpSession getSession(boolean arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
         */
        @Override
        public Principal getUserPrincipal() {
            // TODO Auto-generated method stub
            return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
         */
        @Override
        public boolean isRequestedSessionIdFromCookie() {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
         */
        @Override
        public boolean isRequestedSessionIdFromURL() {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
         */
        @Override
        public boolean isRequestedSessionIdFromUrl() {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
         */
        @Override
        public boolean isRequestedSessionIdValid() {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
         */
        @Override
        public boolean isUserInRole(String arg0) {
            // TODO Auto-generated method stub
            return false;
        }

    }

    @Test
    public final void testGetUrl() {
        final AltView view = new AltView();
        final MockPageContext pageContext = new MockPageContext();
        view.setPageContext(pageContext);
        final MockReq req = new MockReq("1");
        view.setRequest(req);
        view.initialise();
        String expected = "/alt?p=1";
        String actual = view.getUrl();
        assertEquals(expected, actual);
    }
}
