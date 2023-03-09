package com.example.graduate.domain;

public class Swiper {

    private Integer id;

    private String photo;

    private Byte isshow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getisShow() {
        return isshow;
    }

    public void setisShow(Byte isshow) {
        this.isshow = isshow;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Swiper{" +
                "id=" + id +
                ", photo='" + photo + '\'' +
                ", isshow=" + isshow +
                '}';
    }
}
