package com.datawarehouse.model;

import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Staging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDateTime time;
    private String name;
    private String linkAvatar;
    private String linkFilm;
    private String theaterName;

    public Staging(LocalDateTime time, String name, String linkAvatar, String linkFilm, String theaterName) {
        this.time = time;
        this.name = name;
        this.linkAvatar = linkAvatar;
        this.linkFilm = linkFilm;
        this.theaterName = theaterName;
    }
}
