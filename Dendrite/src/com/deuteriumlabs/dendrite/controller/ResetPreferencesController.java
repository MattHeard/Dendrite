/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.User;

public class ResetPreferencesController extends UpdatePreferencesController {

    public void setDefaultFontSize() {
        this.setFontSize(User.getDefaultFontSize());
    }

    private void setFontSize(final double size) {
        this.setFontSize(Double.toString(size));
    }

    public void setDefaultFontType() {
        this.setFontType(User.getDefaultFontType());
    }

    public void setDefaultFontColour() {
        this.setFontColour(User.getDefaultFontColour());
    }

    public void setDefaultSpacing() {
        this.setSpacing(User.getDefaultSpacing());
    }

    private void setSpacing(final double spacing) {
        this.setSpacing(Double.toString(spacing));
    }

    public void setDefaultAlignment() {
        this.setAlignment(User.getDefaultAlignment());
    }

    public void setDefaultTheme() {
        this.setTheme(User.getDefaultTheme());
    }

    public void setDefaultAvatarId() {
        this.setAvatarId(User.getDefaultAvatarId());
    }

    public void setCurrPenName() {
        this.setNewPenName(User.getMyUser().getDefaultPenName());
    }
}
