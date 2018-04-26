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

    private <T extends Comparable>void assertEquals(PriorityQueue<T> pq, T ... values) {
        Assert.assertEquals(pq.getData().size(), values.length);
        for(int i =0;i<values.length;i++) {
            Assert.assertEquals(values[i].compareTo(pq.getData().get(i)),0);
        }
    }
}
