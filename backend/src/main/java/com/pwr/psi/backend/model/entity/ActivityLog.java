package com.pwr.psi.backend.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "activity_log")
@Setter
@Getter
@RequiredArgsConstructor
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    @NotNull
    private int activityId;

    @Column
    private String username;

    @Column
    @NotNull
    private String method;

    @Column
    @NotNull
    private String url;

    @Column(name = "remote_addr")
    @NotNull
    private String addr;

    @Column(name = "activity_date")
    @NotNull
    private Instant activityDate;

    @Column(name = "http_status")
    @NotNull
    private int httpStatus;
}
