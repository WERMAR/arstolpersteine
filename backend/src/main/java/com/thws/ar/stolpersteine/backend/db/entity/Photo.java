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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
