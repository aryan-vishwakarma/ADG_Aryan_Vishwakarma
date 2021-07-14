package com.example.task5;

public class People {
    int image;
    String name;
    String about;
    String followers;
    String posts;
    String following;

    public People(int image, String name, String about, String followers, String posts, String following) {
        this.image = image;
        this.name = name;
        this.about = about;
        this.followers = followers;
        this.posts = posts;
        this.following = following;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
