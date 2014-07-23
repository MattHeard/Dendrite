/**
 * 
 */
package com.deuteriumlabs.dendrite.controller;

import java.util.List;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

/**
 * 
 */
public class LoveController {
    public PageId pageId;
    public String userId;
    
    /**
     * @return 
     * 
     */
    public int addLove() {
        final StoryPage p = new StoryPage();
        p.setId(pageId);
        p.read();
        final List<String> lovingUsers = p.getLovingUsers();
        int count = lovingUsers.size();
        if (lovingUsers.contains(userId) == false) {
            lovingUsers.add(userId);
            count++;
            p.setLovingUsers(lovingUsers);
            p.update();
        }
        return count;
    }

    public int removeLove() {
        final StoryPage p = new StoryPage();
        p.setId(pageId);
        p.read();
        final List<String> lovingUsers = p.getLovingUsers();
        int count = lovingUsers.size();
        if (lovingUsers.contains(userId) == true) {
            while (lovingUsers.contains(userId) == true) {
                lovingUsers.remove(userId);
                count--;
            }
            p.setLovingUsers(lovingUsers);
            p.update();
        }
        return count;
    }
}
