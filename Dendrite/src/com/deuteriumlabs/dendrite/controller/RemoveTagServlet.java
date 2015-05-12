/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class RemoveTagServlet extends DendriteServlet {

	private static final long serialVersionUID = 3945779238617014034L;
	private static final String PG_ID_PARAMETER_NAME = "p";
	private static final String TAG_PARAMETER_NAME = "tag";

	private PageId pgId;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private String tag;

	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		this.setReq(req);
		this.setResp(resp);
		this.extractParameters();
		final PageId pgId = this.getPgId();
		if (pgId.isValid() == false) {
			this.returnFailure();
		} else {
			final String tag = this.getTag();
			final boolean isTagValid = isTagValid(tag);
			if (isTagValid == false) {
				this.returnFailure();
			} else {
				final RemoveTagController controller;
				controller = new RemoveTagController();
				controller.setPgId(pgId);
				controller.setTag(tag);
				controller.removeTag();
				this.returnOk();
			}
		}
	}

	private static boolean isTagValid(final String tag) {
		return (tag != null) && (tag.equals("") == false);
	}

	private void returnFailure() {
		final HttpServletResponse resp = this.getResp();
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	private String getTag() {
		return this.tag;
	}

	private void extractParameters() {
		this.extractPgId();
		this.extractTag();
	}

	private void extractTag() {
		final HttpServletRequest req = this.getReq();
		String tag = req.getParameter(TAG_PARAMETER_NAME);
		tag = tag.toLowerCase();
		this.setTag(tag);
	}

	private void setTag(final String tag) {
		this.tag = tag;
	}

	private void extractPgId() {
		final HttpServletRequest req = this.getReq();
		final String pgId = req.getParameter(PG_ID_PARAMETER_NAME);
		this.setPgId(pgId);
	}

	private PageId getPgId() {
		return this.pgId;
	}

	private HttpServletRequest getReq() {
		return this.req;
	}

	private HttpServletResponse getResp() {
		return resp;
	}

	private void returnOk() {
		final HttpServletResponse resp = this.getResp();
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	private void setPgId(final String id) {
		this.pgId = new PageId(id);
	}

	private void setReq(final HttpServletRequest req) {
		this.req = req;
	}

	private void setResp(final HttpServletResponse resp) {
		this.resp = resp;
	}
}
