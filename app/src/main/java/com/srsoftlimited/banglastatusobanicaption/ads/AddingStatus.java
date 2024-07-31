package com.srsoftlimited.banglastatusobanicaption.ads;

import java.util.ArrayList;

public class AddingStatus {

    public static ArrayList<MyModel> sl = new ArrayList<>(); //status list

    public static void createSub(String subject, int imgRes, ArrayList<String > statusList){
        sl.add(new MyModel(subject, imgRes, statusList));
    }


}
