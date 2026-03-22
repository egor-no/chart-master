package com.chart.TopChart.data.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUser")
    private int id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="nickname")
    private String nickname;

    @Column(name="bio")
    private String bio;

    @Column(name="slogan")
    private String slogan;

    @Column(name="avatar")
    private String avatar;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChartInfo> charts;

    public User() {
    }

    public User(int id, String login, String password, String nickname, String bio, String slogan, String avatar, LocalDateTime createdAt, List<ChartInfo> charts) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nickname = nickname;
        this.bio = bio;
        this.slogan = slogan;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.charts = charts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public List<ChartInfo> getCharts() {
        return charts;
    }

    public void setCharts(List<ChartInfo> charts) {
        this.charts = charts;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
