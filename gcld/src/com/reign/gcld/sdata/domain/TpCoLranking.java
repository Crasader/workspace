package com.reign.gcld.sdata.domain;

import com.reign.framework.mybatis.*;

public class TpCoLranking implements IModel
{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer grade;
    private Integer rkHigh;
    private Integer rkLow;
    private Integer rewardExp;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Integer getGrade() {
        return this.grade;
    }
    
    public void setGrade(final Integer grade) {
        this.grade = grade;
    }
    
    public Integer getRkHigh() {
        return this.rkHigh;
    }
    
    public void setRkHigh(final Integer rkHigh) {
        this.rkHigh = rkHigh;
    }
    
    public Integer getRkLow() {
        return this.rkLow;
    }
    
    public void setRkLow(final Integer rkLow) {
        this.rkLow = rkLow;
    }
    
    public Integer getRewardExp() {
        return this.rewardExp;
    }
    
    public void setRewardExp(final Integer rewardExp) {
        this.rewardExp = rewardExp;
    }
}