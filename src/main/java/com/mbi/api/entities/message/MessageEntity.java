package com.mbi.api.entities.message;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

/**
 * Slack message entity.
 */
@Entity
@Table(name = "messages")
@SequenceGenerator(name = "message_id_seq", sequenceName = "message_id_seq", allocationSize = 1)
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
    private Integer id;

    private String channel;

    private String ts;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Object message;

    private String error;

    private int currentPage;

    private int testRunId;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(final int testRunId) {
        this.testRunId = testRunId;
    }
}
