/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.User;

public class ResetPreferencesController extends UpdatePreferencesController {
    public void setCurrPenName(final User myUser) {
        setNewPenName(myUser.getDefaultPenName());
    }

    public void setDefaultAlignment() {
        setAlignment(User.getDefaultAlignment());
    }

    public void setDefaultAvatarId() {
        setAvatarId(User.getDefaultAvatarId());
    }

    public void setDefaultFontColour() {
        setFontColour(User.getDefaultFontColour());
    }

    public void setDefaultFontSize() {
        setFontSize(User.getDefaultFontSize());
    }

    public void setDefaultFontType() {
        setFontType(User.getDefaultFontType());
    }

    public void setDefaultSpacing() {
        setSpacing(User.getDefaultSpacing());
    }

    public void setDefaultTheme() {
        setTheme(User.getDefaultTheme());
    }

    private void setFontSize(final double size) {
        setFontSize(Double.toString(size));
    }

    private void setSpacing(final double spacing) {
        setSpacing(Double.toString(spacing));
    }
}
