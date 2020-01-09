package stu.napls.boostimcenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import stu.napls.boostimcenter.core.dictionary.StatusCode;

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

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @Column(name = "clientNumber")
    private int clientNumber = 0;

    @JsonIgnore
    @Column(name = "createDate")
    @CreatedDate
    private Date createDate;

    @JsonIgnore
    @Column(name = "updateDate")
    @LastModifiedDate
    private Date updateDate;

    @JsonIgnore
    @Column(name = "status", columnDefinition = "integer default " + StatusCode.NORMAL)
    private int status;
}
