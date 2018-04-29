package com.algorithm.problem.notwoteammbrsnxt2eachothers;

public class Team implements Comparable<Team>{
    Integer no;
    Integer count;


    public Team(Integer no, Integer count) {
        this.no = no;
        this.count = count;
    }

    @Override
    public int compareTo(Team o) {
        return count.compareTo(o.getCount());
    }

    public Integer getNo() {
        return no;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public int hashCode() {
        return no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (no != null ? !no.equals(team.no) : team.no != null) return false;
        return count != null ? count.equals(team.count) : team.count == null;
    }

    @Override
    public String toString() {
        return "Team{" +
                "no=" + no +
                ", count=" + count +
                '}';
    }
}
