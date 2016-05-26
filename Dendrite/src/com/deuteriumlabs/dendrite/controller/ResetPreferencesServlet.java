/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.User;

public class ResetPreferencesServlet extends UpdatePreferencesServlet {
    private static final long serialVersionUID = 5970059991233726340L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setResponse(resp);
        final ResetPreferencesController controller;
        controller = new ResetPreferencesController();
        final User myUser = User.getMyUser();
        controller.setCurrPenName(myUser);
        controller.setDefaultFontSize();
        controller.setDefaultFontType();
        controller.setDefaultFontColour();
        controller.setDefaultSpacing();
        controller.setDefaultAlignment();
        controller.setDefaultTheme();
        controller.setDefaultAvatarId();
        controller.updatePreferences(myUser);
        redirectToMyPreferencesPage();
    }

}
