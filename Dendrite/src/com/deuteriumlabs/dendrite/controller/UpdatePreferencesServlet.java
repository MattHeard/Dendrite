/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.User;

public class UpdatePreferencesServlet extends DendriteServlet {

    private static final long serialVersionUID = 8943369549424406002L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setResponse(resp);
        final UpdatePreferencesController controller;
        controller = new UpdatePreferencesController();
        final String newPenName = req.getParameter("newPenName");
        controller.setNewPenName(newPenName);
        final String fontSize = req.getParameter("fontSize");
        controller.setFontSize(fontSize);
        final String fontType = req.getParameter("fontType");
        controller.setFontType(fontType);
        final String fontColour = req.getParameter("fontColour");
        controller.setFontColour(fontColour);
        final String spacing = req.getParameter("spacing");
        controller.setSpacing(spacing);
        final String alignment = req.getParameter("alignment");
        controller.setAlignment(alignment);
        final String theme = req.getParameter("theme");
        controller.setTheme(theme);

        int avatarId = getAvatarId(req);
        controller.setAvatarId(avatarId);

        if (controller.isNewPenNameBlank() == true)
            redirectFromBlankNewPenName();
        else {
            final User myUser = User.getMyUser();
            final boolean isUpdated = controller.updatePreferences(myUser);
            if (isUpdated == true)
                redirectToMyPreferencesPage();
            else
                redirectFromUpdateFailure();
        }
    }

    private int getAvatarId(final HttpServletRequest req) {
        final String avatarNumberParameter = req.getParameter("avatar");
        if (avatarNumberParameter != null) {
            int avatarNumber;
            try {
                avatarNumber = Integer.parseInt(avatarNumberParameter);
            } catch (NumberFormatException e) {
                avatarNumber = 0;
            }
            return avatarNumber;
        } else {
            return 0;
        }
    }

    private void redirectFromBlankNewPenName() {
        final String url = "/preferences?error=blankNewPenName";
        redirect(url);
    }

    private void redirectFromUpdateFailure() {
        final String url = "/preferences?error=updateFailed";
        redirect(url);
    }

    protected void redirectToMyPreferencesPage() {
        final String url = "/preferences";
        redirect(url);
    }

}
