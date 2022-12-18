package org.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    int count = 0;
    @Test
    void main(){
        System.out.println(count++);
        Main.main(new String[2]);
    }

    @Test
    void exception(){
        System.out.println(count++);
    }

    @Disabled
    @Test
    void disabled(){
        System.out.println(count++);
    }
}