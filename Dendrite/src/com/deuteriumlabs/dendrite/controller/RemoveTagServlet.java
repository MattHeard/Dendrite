/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class RemoveTagServlet extends DendriteServlet {
	private static final String PG_ID_PARAMETER_NAME = "p";
	private static final long serialVersionUID = 3945779238617014034L;
	private static final String TAG_PARAMETER_NAME = "tag";

	private PageId pgId;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private String tag;
	
	private void extractParameters() {
		extractPgId();
		extractTag();
	}

	private void extractPgId() {
		final HttpServletRequest req = getReq();
		final String pgId = req.getParameter(PG_ID_PARAMETER_NAME);
		setPgId(pgId);
	}

	private void extractTag() {
		final HttpServletRequest req = getReq();
		String tag = req.getParameter(TAG_PARAMETER_NAME);
		tag = tag.toLowerCase();
		setTag(tag);
	}

	private PageId getPgId() {
		return pgId;
	}

	private HttpServletRequest getReq() {
		return req;
	}

	private HttpServletResponse getResp() {
		return resp;
	}

	private String getTag() {
		return tag;
	}

	private boolean isTagValid(final String tag) {
		return (tag != null) && (tag.equals("") == false);
	}

	private void returnFailure() {
		final HttpServletResponse resp = getResp();
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	private void returnOk() {
		final HttpServletResponse resp = getResp();
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	private void setPgId(final String id) {
		pgId = new PageId(id);
	}

	private void setReq(final HttpServletRequest req) {
		this.req = req;
	}

	private void setResp(final HttpServletResponse resp) {
		this.resp = resp;
	}

	private void setTag(final String tag) {
		this.tag = tag;
	}

	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		setReq(req);
		setResp(resp);
		extractParameters();
		final PageId pgId = getPgId();
		if (pgId.isValid() == false) {
			returnFailure();
		} else {
			final String tag = getTag();
			final boolean isTagValid = isTagValid(tag);
			if (isTagValid == false) {
				returnFailure();
			} else {
				final RemoveTagController controller;
				controller = new RemoveTagController();
				controller.setPgId(pgId);
				controller.setTag(tag);
				controller.removeTag();
				returnOk();
			}
		}
	}
}
