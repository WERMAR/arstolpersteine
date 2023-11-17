package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "databasechangelog")
public class databasechangelog {

    @Column(name = "ID")
    private String id;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "DATEEXECUTED")
    private Date dateExecuted;

    @Column(name = "ORDEREXECUTED")
    private int orderExecuted;

    @Column(name = "EXECTYPE")
    private String exectype;

    @Column(name = "MD5SUM")
    private String md5sum;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "LIQUIBASE")
    private String liquibase;

    @Column(name = "CONTEXTS")
    private String contexts;

    @Column(name = "LABELS")
    private String labels;

    @Column(name = "DEPLOYMENT_ID")
    private String deploymentId;

}
