package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "databasechangeloglock")
public class databasechangeloglock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "LOCKED")
    private short locked;

    @Column(name = "LOCKGRANTED")
    private Date lockgranted;

    @Column(name = "LOCKEDBY")
    private String lockedBy;
}
