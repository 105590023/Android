package com.hw4_2;

/**
 * Created by KamijouKage on 2018/4/3.
 */

public class handleMora {
    public String handle(int player,int npc) {

        String result = "";

        if(player == 1 && npc == 1)
            result = "draw";
        if(player == 1 && npc == 2)
            result = "lose";
        if(player == 1 && npc == 3)
            result = "win";

        if(player == 2 && npc == 1)
            result = "win";
        if(player == 2 && npc == 2)
            result = "draw";
        if(player == 2 && npc == 3)
            result = "lose";

        if(player == 3 && npc == 1)
            result = "lose";
        if(player == 3 && npc == 2)
            result = "win";
        if(player == 3 && npc == 3)
            result = "draw";

        return result;
    }
}
