package com.algorithm.problem.notwoteammbrsnxt2eachothers;

import com.algorithm.structure.PriorityQueue;

import java.util.*;


/*

No Two Team Members Next to Each Other
Input: 1, 1, 2, 2, 2, 567, 567, 10000076, 4, 2, 3, 3
Explanation: There are 12 people listed above. They belong to 6 teams (Team 1, Team 2, Team 3, Team 567, Team 10000076, and Team 4). As you can see people are identified in the list by the team number.
Output: 1, 2, 1, 2, 4, 2, 567, 3, 567, 100076, 2, 3

 */

public class App {


    private static Map<Integer, Integer> teams = new LinkedHashMap<>();
    private static Integer total = 0;

    public static void main(String[] args) {
        PriorityQueue<Team> pq = new PriorityQueue<>();
        addTeam(1, 1, 2, 2, 2, 567, 567, 10000076, 4, 2, 3, 3);
        teams.entrySet().stream().forEach(e -> {
            pq.push(new Team(e.getKey(), e.getValue()));
        });

//        Queue<Team> ls = queueIt(pq.getData());
//        ls.stream().forEach(System.out::println);

        arrangeTeams(pq).stream().forEach(System.out::println);

    }

    private static Queue<Team> queueIt(List<Team> data) {
        Queue<Team> q = new LinkedList<>();
        data.stream().forEach(e -> q.offer(e));
        return q;
    }

    private static List<Integer> arrangeTeams(PriorityQueue<Team> ls) {
        List<Integer> ret = new ArrayList<>(total);
        for(int i=0;i<total;i++) {
            ret.add(-1);
        }
        int par=0;
        int impar = 1;
        int pos;
        int i=0;
        while (!ls.isEmpty()) {
            Team t1 = ls.take();
            if(ls.isEmpty() && t1.count>1) {
                System.out.println("Not a solution");
                break;
            }
            pos = Math.min(par,impar);
            for (i = pos; i < pos + 2 * t1.count; i += 2)
                ret.set(i, t1.no);
            if(pos%2==0)
                par=i;
            else
                impar=i;
        }
        return ret;

    }


    private static void addTeam(Integer... values) {
        total = values.length;
        for (Integer value : values) {
            if (null == teams.computeIfPresent(value, (k, v) -> v + 1)) {
                teams.put(value, 1);
            }

        }
    }


}
