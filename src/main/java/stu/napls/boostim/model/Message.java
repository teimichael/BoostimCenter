package stu.napls.boostim.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "conversationUuid")
    private String conversationUuid;

    @Column(name = "createDate")
    @CreatedDate
    private Date createDate;

    @Column(name = "updateDate")
    @LastModifiedDate
    private Date updateDate;

}
