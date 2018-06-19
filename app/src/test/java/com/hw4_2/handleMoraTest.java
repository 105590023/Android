package com.hw4_2;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by KamijouKage on 2018/4/3.
 */

public class handleMoraTest {
    @Test
    public void handle() {
        handleMora hm = new handleMora();

        assertEquals(hm.handle(1,1), "draw");
        assertEquals(hm.handle(1,2), "lose");
        assertEquals(hm.handle(1,3), "win");

        assertEquals(hm.handle(2,1), "win");
        assertEquals(hm.handle(2,2), "draw");
        assertEquals(hm.handle(2,3), "lose");

        assertEquals(hm.handle(3,1), "lose");
        assertEquals(hm.handle(3,2), "win");
        assertEquals(hm.handle(3,3), "draw");
    }
}
