package com.algorithm.structure;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PriorityQueueTests {


    @BeforeClass
    public static void before(){

    }


    @Test
    public void basicTest(){
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.push(4);
        assertEquals(pq,4);

    }

    private <T>void assertEquals(PriorityQueue<T extends Comparable> pq, T ... values) {
    }
}
