/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.List;

import com.deuteriumlabs.dendrite.model.FollowNotification;
import com.deuteriumlabs.dendrite.model.User;

public class FollowController {

	private String sourceId;
	private String targetId;

	public void enableFollow() {
		final User target = new User();
		final String targetId = this.getTargetId();
		target.setId(targetId);
		target.read();
		final List<String> formerFollowers = target.getFormerFollowers();
		final String sourceId = this.getSourceId();
		boolean isNeedingAnUpdate = false;
		boolean isSourceAFormerFollower;
		if (formerFollowers.contains(sourceId) == true) {
			formerFollowers.remove(sourceId);
			target.setFormerFollowers(formerFollowers);
			isNeedingAnUpdate = true;
			isSourceAFormerFollower = true;
		} else {
			isSourceAFormerFollower = false;
		}
		final List<String> followers = target.getFollowers();
		if (followers.contains(sourceId) == false) {
			followers.add(sourceId);
			target.setFollowers(followers);
			isNeedingAnUpdate = true;
		}
		if (isNeedingAnUpdate == true) {
			target.update();
			if (isSourceAFormerFollower == false) {
				this.notifyFollow();
			}
		}
	}

	private String getSourceId() {
		return this.sourceId;
	}

	private String getTargetId() {
		return this.targetId;
	}

	private void notifyFollow() {
		final FollowNotification notification = new FollowNotification();
		final String followerId = this.getSourceId();
		notification.setFollowerId(followerId);
		final String recipientId = this.getTargetId();
		notification.setRecipientId(recipientId);
		notification.create();
	}

	public void setSourceId(final String sourceId) {
		this.sourceId = sourceId;
	}

	public void setTargetId(final String targetId) {
		this.targetId = targetId;
	}

}
