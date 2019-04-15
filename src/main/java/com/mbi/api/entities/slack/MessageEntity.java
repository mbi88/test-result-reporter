package com.mbi.api.entities.slack;

import javax.persistence.*;

/**
 * Slack entity.
 */
@Entity
@Table(name = "messages")
@SequenceGenerator(name = "message_id_seq", sequenceName = "message_id_seq", allocationSize = 1)
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}
