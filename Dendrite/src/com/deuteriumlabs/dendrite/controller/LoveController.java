/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.List;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.PgLovedNotification;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class LoveController {
	public String loverId;
	public PageId pageId;
	private String authorId;

	public int addLove() {
		final StoryPage pg = new StoryPage();
		pg.setId(pageId);
		pg.read();
		authorId = pg.getAuthorId();

		final List<String> formerlyLovingUsers = pg.getFormerlyLovingUsers();
		final boolean isUserAFormerLover;
		if (formerlyLovingUsers.contains(loverId) == true) {
			isUserAFormerLover = true;
			do {
				formerlyLovingUsers.remove(loverId);
			} while (formerlyLovingUsers.contains(loverId) == true);
		} else {
			isUserAFormerLover = false;
		}

		final boolean isAuthorNotifiable = (authorId != null);

		final boolean isLoverCurrentAuthor;
		isLoverCurrentAuthor = ((loverId != null) && loverId.equals(authorId));

		boolean isNotificationNeeded = (isUserAFormerLover == false)
				&& (isAuthorNotifiable == true)
				&& (isLoverCurrentAuthor == false);

		final List<String> lovingUsers = pg.getLovingUsers();
		int count = lovingUsers.size();
		if (lovingUsers.contains(loverId) == false) {
			lovingUsers.add(loverId);
			count++;
			pg.setFormerlyLovingUsers(formerlyLovingUsers);
			pg.setLovingUsers(lovingUsers);
			pg.update();
			recalculateStoryQuality(pg);
			if (isNotificationNeeded == true) {
				notifyLove();
			}
		}
		return count;
	}

	public int removeLove() {
		final StoryPage pg = new StoryPage();
		pg.setId(pageId);
		pg.read();

		final List<String> formerlyLovingUsers = pg.getFormerlyLovingUsers();
		if (formerlyLovingUsers.contains(loverId) == false) {
			formerlyLovingUsers.add(loverId);
			pg.setFormerlyLovingUsers(formerlyLovingUsers);
		}

		final List<String> lovingUsers = pg.getLovingUsers();
		int count = lovingUsers.size();
		if (lovingUsers.contains(loverId) == true) {
			do {
				lovingUsers.remove(loverId);
				count--;
			} while (lovingUsers.contains(loverId) == true);
			pg.setLovingUsers(lovingUsers);
			pg.update();
			recalculateStoryQuality(pg);
		}
		return count;
	}

	private void notifyLove() {
		final PgLovedNotification notification = new PgLovedNotification();
		notification.setPgId(pageId);
		notification.setLoverId(loverId);
		notification.setRecipientId(authorId);
		notification.create();
	}

	private void recalculateStoryQuality(final StoryPage pg) {
		final StoryBeginning beginning = new StoryBeginning();
		final PageId beginningId = pg.getBeginning();
		beginning.setPageNumber(beginningId.getNumber());
		beginning.read();
		beginning.recalculateQuality();
		beginning.update();
	}
}
