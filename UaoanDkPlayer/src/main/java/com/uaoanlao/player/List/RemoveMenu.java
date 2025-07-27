package com.uaoanlao.player.List;

import com.uaoanlao.player.DkPlayerView;

import java.util.ArrayList;

public class RemoveMenu {
    private ArrayList<String> arrayName= DkPlayerView.arrayName;
    private ArrayList<Integer> arrayImage=DkPlayerView.arrayImage;
    public void remove(String name){
        arrayImage.remove(arrayName.indexOf(name));
        arrayName.remove(name);
    }
}
