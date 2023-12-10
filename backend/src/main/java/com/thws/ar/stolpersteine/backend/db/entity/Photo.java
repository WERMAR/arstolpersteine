package com.thws.ar.stolpersteine.backend.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Photo")
public class Photo {

    @Id
    @GeneratedValue(generator="photo_photo_id_seq")
    @SequenceGenerator(name="photo_photo_id_seq",sequenceName="photo_photo_id_seq", allocationSize=1)
    private Long photoId;

    @Column(name = "heading")
    private Integer heading;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "created_user_name")
    private String createdUsername;

    @Column(name = "approved_user_name")
    private String approvedUsername;

    @ManyToOne
    @JoinColumn(name = "stolperstein_id")
    private Stolperstein stolperstein;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "resource_group_id")
    private Long resourceGroupId;
}
