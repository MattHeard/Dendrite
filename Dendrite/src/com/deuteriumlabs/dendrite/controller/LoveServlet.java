/**
 * 
 */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * 
 */
public class LoveServlet extends DendriteServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7662290110231192243L;
    private static final String PAGE_ID_PARAM_NAME = "p";
    private static final String USER_ID_PARAM_NAME = "user";
    private static final String IS_ADDED_PARAM_NAME = "isAdded";
    
    private int count;

    @Override
    protected void doPost(final HttpServletRequest req,
            final HttpServletResponse resp) throws ServletException,
            IOException {
        final LoveController controller = new LoveController();
        
        final PageId pageId = this.extractPageId(req);
        final String userId = this.extractUserId(req);
        if (!pageId.isValid()) {
            this.setInvalidPageIdError(resp);
        } else if (userId == null) {
            this.setInvalidUserIdError(resp);
        } else {
            controller.pageId = pageId;
            controller.userId = userId;
            
            final boolean isLoveAdded = this.extractIsLoveAdded(req);
            final int count;
            if (isLoveAdded) {
                count = controller.addLove();
            } else {
                count = controller.removeLove();
            }
            this.count = count;
            this.setLoveOkResponse(resp);
        }
    }

    /**
     * @param resp
     * @throws IOException 
     */
    private void setLoveOkResponse(final HttpServletResponse resp)
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(count);
    }

    /**
     * @param resp 
     * @throws IOException 
     * 
     */
    private void setInvalidPageIdError(final HttpServletResponse resp)
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println("PageId is invalid.");
    }
    
    /**
     * @param resp 
     * @throws IOException 
     * 
     */
    private void setInvalidUserIdError(final HttpServletResponse resp)
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println("UserId is invalid.");
    }

    /**
     * @param req
     * @return
     */
    private boolean extractIsLoveAdded(final HttpServletRequest req) {
        final String paramVal = req.getParameter(IS_ADDED_PARAM_NAME);
        return Boolean.valueOf(paramVal);
    }

    /**
     * @param req
     * @return
     */
    private String extractUserId(final HttpServletRequest req) {
        return req.getParameter(USER_ID_PARAM_NAME);
    }

    /**
     * @param req
     * @return
     */
    private PageId extractPageId(final HttpServletRequest req) {
        final String paramVal = req.getParameter(PAGE_ID_PARAM_NAME);
        return new PageId(paramVal);
    }

    
}
