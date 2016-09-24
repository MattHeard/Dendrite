/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.http.HttpServletRequest;

import com.deuteriumlabs.dendrite.model.User;

public class PreferencesView extends View {

    private static final String NEW_PEN_NAME_IS_BLANK = "blankNewPenName";

    public static String getAvatarDesc(final int id) {
        return User.getAvatarDesc(id);
    }

    private String error;

    public int getAvatarId(final User myUser) {
        return myUser.getAvatarId();
    }

    public String getError() {
        return error;
    }

    @Override
    public String getUrl() {
        return "/preferences";
    }

    @Override
    public void initialise() {
        initialiseError();

        super.initialise();
    }

    public boolean isNewPenNameBlank() {
        final String error = getError();
        return NEW_PEN_NAME_IS_BLANK.equals(error);
    }

    public void setError(final String error) {
        this.error = error;
    }

    private void initialiseError() {
        final HttpServletRequest request = getRequest();
        final String error = request.getParameter("error");
        setError(error);
    }

    @Override
    protected String getMetaDesc() {
        return "Customise your reading experience and the look and feel of "
                + "Dendrite.";
    }
    
    public String getCheckedIfDeletionRequested(final User myUser) {
        return myUser.isDeletionRequested() ? "checked" : "";
    }
}
