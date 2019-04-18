package com.mbi.api.models.response;

/**
 * Slack response.
 */
public class SlackResponse {

    private boolean ok;

    private String channel;

    private String ts;

    private Object message;

    private String error;

    public boolean getOk() {
        return ok;
    }

    public void setOk(final boolean ok) {
        this.ok = ok;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(final String channel) {
        this.channel = channel;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(final String ts) {
        this.ts = ts;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(final Object message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}
