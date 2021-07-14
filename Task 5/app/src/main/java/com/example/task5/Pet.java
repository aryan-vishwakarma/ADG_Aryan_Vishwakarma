package com.example.task5;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Pet {
    int image;
    String petName;
    public Pet(){
        image=0;
        petName="";
    }
    public Pet(int img, String name){
        image=img;
        petName=name;
    }

    public int getImage() {
        return image;
    }

    public String getPetName() {
        return petName;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}
