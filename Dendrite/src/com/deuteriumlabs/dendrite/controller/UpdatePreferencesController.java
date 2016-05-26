/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.User;

public class UpdatePreferencesController {
    private String alignment;
    private String fontColour;
    private String fontSize;
    private String fontType;
    private String newPenName;
    private String spacing;
    private String theme;
    private int avatarId;

    private double getFontSizeNumber() {
        // TODO Replace with switch or dictionary lookup
        if (fontSize.equals("Huge")) {
            return 2;
        } else if (fontSize.equals("Large")) {
            return 1.5;
        } else if (fontSize.equals("Small")) {
            return 0.8;
        } else {
            return 1; // Medium
        }
    }

    private double getSpacingNumber() {
        // TODO Replace with switch or dictionary lookup
        if (spacing.equals("Huge")) {
            return 3;
        } else if (spacing.equals("Large")) {
            return 2;
        } else if (spacing.equals("Small")) {
            return 1;
        } else {
            return 1.5; // Medium
        }
    }

    public boolean isNewPenNameBlank() {
        return (newPenName == null || newPenName.equals(""));
    }

    public void setAlignment(final String alignment) {
        this.alignment = alignment;
    }

    public void setFontColour(final String fontColour) {
        this.fontColour = fontColour;
    }

    public void setFontSize(final String fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontType(final String fontType) {
        this.fontType = fontType;
    }

    public void setNewPenName(final String newPenName) {
        this.newPenName = newPenName;
    }

    public void setSpacing(final String spacing) {
        this.spacing = spacing;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public boolean updatePreferences(final User myUser) {
        if (myUser != null) {
            myUser.setDefaultPenName(newPenName);
            final double fontSize = getFontSizeNumber();
            myUser.setFontSize(fontSize);
            myUser.setFontType(fontType);
            myUser.setFontColour(fontColour);
            final double spacing = getSpacingNumber();
            myUser.setSpacing(spacing);
            myUser.setAlignment(alignment);
            myUser.setTheme(theme);
            myUser.setAvatarId(avatarId);

            myUser.update();
            return true;
        } else
            return false;
    }

    public void setAvatarId(final int avatarId) {
        this.avatarId = avatarId;
    }

}
