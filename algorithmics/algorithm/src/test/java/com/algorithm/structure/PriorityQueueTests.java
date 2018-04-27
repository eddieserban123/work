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
        pq.push(10);
        assertEquals(pq,10,4);
        pq.push(2);
        assertEquals(pq,10,4,2);
        pq.push(12);
        assertEquals(pq,12,10,2,4);



    }




    /*-----------------------helper methods *************************/

    private <T extends Comparable>void assertEquals(PriorityQueue<T> pq, T ... values) {
        Assert.assertEquals(pq.getData().size(), values.length);
        for(int i =0;i<values.length;i++) {
            Assert.assertEquals(values[i].compareTo(pq.getData().get(i)),0);
        }
    }
}
