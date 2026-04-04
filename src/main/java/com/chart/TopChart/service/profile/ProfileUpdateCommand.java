package com.chart.TopChart.service.profile;

import javax.servlet.http.Part;

public class ProfileUpdateCommand {
        private Integer viewerId;
        private String nickname;
        private String slogan;
        private String bio;
        private String password;
        private boolean removeAvatar;
        private Part avatarPart;
        private String uploadPath;

        public ProfileUpdateCommand() {
        }

        public Integer getViewerId() {
            return viewerId;
        }

        public void setViewerId(Integer viewerId) {
            this.viewerId = viewerId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isRemoveAvatar() {
            return removeAvatar;
        }

        public void setRemoveAvatar(boolean removeAvatar) {
            this.removeAvatar = removeAvatar;
        }

        public Part getAvatarPart() {
            return avatarPart;
        }

        public void setAvatarPart(Part avatarPart) {
            this.avatarPart = avatarPart;
        }

        public String getUploadPath() {
            return uploadPath;
        }

        public void setUploadPath(String uploadPath) {
            this.uploadPath = uploadPath;
        }
    }