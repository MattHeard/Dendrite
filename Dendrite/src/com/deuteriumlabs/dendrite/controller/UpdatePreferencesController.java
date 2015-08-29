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

    private String getAlignment() {
        return this.alignment;
    }

    private String getFontColour() {
        return this.fontColour;
    }

    private String getFontSize() {
        return this.fontSize;
    }

    private double getFontSizeNumber() {
        final String fontSizeName = this.getFontSize();
        if (fontSizeName.equals("Huge")) {
            return 2;
        } else if (fontSizeName.equals("Large")) {
            return 1.5;
        } else if (fontSizeName.equals("Small")) {
            return 0.8;
        } else {
            return 1; // Medium
        }
    }

    private String getFontType() {
        return this.fontType;
    }

    private String getNewPenName() {
        return this.newPenName;
    }

    private String getSpacing() {
        return this.spacing;
    }

    private double getSpacingNumber() {
        final String spacingName = this.getSpacing();
        if (spacingName.equals("Huge")) {
            return 3;
        } else if (spacingName.equals("Large")) {
            return 2;
        } else if (spacingName.equals("Small")) {
            return 1;
        } else {
            return 1.5; // Medium
        }
    }

    private String getTheme() {
        return this.theme;
    }

    public boolean isNewPenNameBlank() {
        final String newPenName = this.getNewPenName();
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

    public boolean updatePreferences() {
        final User myUser = User.getMyUser();
        if (myUser != null) {
            final String penName = this.getNewPenName();
            myUser.setDefaultPenName(penName);
            final double fontSize = this.getFontSizeNumber();
            myUser.setFontSize(fontSize);
            final String fontType = this.getFontType();
            myUser.setFontType(fontType);
            final String fontColour = this.getFontColour();
            myUser.setFontColour(fontColour);
            final double spacing = this.getSpacingNumber();
            myUser.setSpacing(spacing);
            final String alignment = this.getAlignment();
            myUser.setAlignment(alignment);
            final String theme = this.getTheme();
            myUser.setTheme(theme);

            final int avatarId = this.getAvatarId();
            myUser.setAvatarId(avatarId);

            myUser.update();
            return true;
        } else
            return false;
    }

    private int getAvatarId() {
        return this.avatarId;
    }

    public void setAvatarId(final int avatarId) {
        this.avatarId = avatarId;
    }

}
