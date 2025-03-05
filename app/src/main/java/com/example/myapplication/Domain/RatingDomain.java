package com.example.myapplication.Domain;

public class RatingDomain {
    private int place;
    private String pic;
    private String login;
    private String score;

    public RatingDomain(int place, String pic, String login, String score) {
        this.place = place;
        this.pic = pic;
        this.login = login;
        this.score = score;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
