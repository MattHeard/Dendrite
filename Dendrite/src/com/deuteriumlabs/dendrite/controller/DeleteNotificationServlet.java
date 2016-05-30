/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteNotificationServlet extends DendriteServlet {
    private static final long serialVersionUID = 8206847215334258623L;

    @Override
    protected void doPost(final HttpServletRequest req,
            final HttpServletResponse resp)
                    throws ServletException, IOException {
        final DeleteNotificationController controller;
        controller = new DeleteNotificationController();
        final String notificationId = req.getParameter("id");
        controller.setNotificationId(notificationId);
        final boolean isMyUserTheRecipient = controller.isMyUserTheRecipient();
        if (isMyUserTheRecipient == true) {
            controller.deleteNotification();
        }
    }
}
