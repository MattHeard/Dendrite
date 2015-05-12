/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.List;

import com.deuteriumlabs.dendrite.model.User;

public class UnfollowController {

	private String sourceId;
	private String targetId;

	public void enableUnfollow() {
		final User target = new User();
		final String targetId = this.getTargetId();
		target.setId(targetId);
		target.read();
		final String sourceId = this.getSourceId();
		
		final List<String> followers = target.getFollowers();
		boolean isRemoved = false;
		while (followers.contains(sourceId) == true) {
			followers.remove(sourceId);
			isRemoved = true;
		}
		if (isRemoved == true) {
			target.setFollowers(followers);
			final List<String> formerFollowers = target.getFormerFollowers();
			if (formerFollowers.contains(sourceId) == false) {
				formerFollowers.add(sourceId);
				target.setFormerFollowers(formerFollowers);
			}
			target.update();
		}
	}

	private String getSourceId() {
		return this.sourceId;
	}

	private String getTargetId() {
		return this.targetId;
	}

	public void setSourceId(final String sourceId) {
		this.sourceId = sourceId;
	}

	public void setTargetId(final String targetId) {
		this.targetId = targetId;
	}

}
