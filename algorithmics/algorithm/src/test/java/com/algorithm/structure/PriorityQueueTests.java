package com.algorithm.structure;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PriorityQueueTests {


    @BeforeClass
    public static void before() {

    }


    @Test
    public void basicTest() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.push(4);
        assertEquals(pq, 4);
        pq.push(10);
        assertEquals(pq, 10, 4);
        pq.push(2);
        assertEquals(pq, 10, 4, 2);
        pq.push(12);
        assertEquals(pq, 12, 10, 2, 4);

    }

    @Test
    public void normalTest() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.push(4);
        assertEquals(pq, 4);
        pq.push(10);
        assertEquals(pq, 10, 4);
        pq.push(12);
        assertEquals(pq, 12, 4, 10);
        pq.push(14);
        assertEquals(pq, 14, 12, 10, 4);
        pq.push(7);
        assertEquals(pq, 14, 12, 10, 4, 7);
        Assert.assertEquals(14, pq.take().longValue());
        assertEquals(pq, 12, 7, 10, 4);


    }




    /*-----------------------helper methods *************************/

    private <T extends Comparable> void assertEquals(PriorityQueue<T> pq, T... values) {
        Assert.assertEquals(pq.getData().size(), values.length);
        for (int i = 0; i < values.length; i++) {
            Assert.assertEquals(values[i].compareTo(pq.getData().get(i)), 0);
        }
    }
}
