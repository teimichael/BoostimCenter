package stu.napls.boostim.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import stu.napls.boostim.core.dictionary.StatusCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "node")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createDate")
    @CreatedDate
    private Date createDate;

    @Column(name = "updateDate")
    @LastModifiedDate
    private Date updateDate;

    @Column(name = "status", columnDefinition = "integer default " + StatusCode.NORMAL)
    private int status;
}
