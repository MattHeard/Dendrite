/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.User;

public class AddTagServlet extends DendriteServlet {
    private static final String PG_ID_PARAMETER_NAME = "p";
    private static final long serialVersionUID = 3295021739229957210L;
    private static final String TAG_PARAMETER_NAME = "tag";

    private PageId pageId;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private String tag;

    private void extractParameters() {
        extractPageId();
        extractTag();
    }

    private void extractPageId() {
        final String idString = req.getParameter(PG_ID_PARAMETER_NAME);
        pageId = new PageId(idString);
    }

    private void extractTag() {
        tag = req.getParameter(TAG_PARAMETER_NAME).toLowerCase();
    }

    private boolean isTagValid(final String tag) {
        return (tag != null) && (tag.equals("") == false);
    }

    private void returnFailure() {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void returnOk() {
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(final HttpServletRequest req,
            final HttpServletResponse resp)
                    throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        extractParameters();
        if (pageId.isValid() == false) {
            returnFailure();
        } else {
            final boolean isTagValid = isTagValid(tag);
            if (isTagValid == false) {
                returnFailure();
            } else {
                final AddTagController controller = new AddTagController();
                controller.setPageId(pageId);
                controller.setTag(tag);
                final User myUser = User.getMyUser();
                final boolean isAdded = controller.addTag(myUser);
                if (isAdded) {
                    returnOk();
                } else {
                    returnFailure();
                }
            }
        }
    }
}
