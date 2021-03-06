package com.reign.gcld.sdata.domain;

import com.reign.framework.mybatis.*;

public class KtSdRanking implements IModel
{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer kindomLv;
    private Integer taskR;
    private Integer highLv;
    private Integer lowLv;
    private Integer rewardExp;
    private Integer rewardIron;
    private Integer lv;
    private Integer gemItem;
    private Integer gemNum;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Integer getKindomLv() {
        return this.kindomLv;
    }
    
    public void setKindomLv(final Integer kindomLv) {
        this.kindomLv = kindomLv;
    }
    
    public Integer getTaskR() {
        return this.taskR;
    }
    
    public void setTaskR(final Integer taskR) {
        this.taskR = taskR;
    }
    
    public Integer getHighLv() {
        return this.highLv;
    }
    
    public void setHighLv(final Integer highLv) {
        this.highLv = highLv;
    }
    
    public Integer getLowLv() {
        return this.lowLv;
    }
    
    public void setLowLv(final Integer lowLv) {
        this.lowLv = lowLv;
    }
    
    public Integer getRewardExp() {
        return this.rewardExp;
    }
    
    public void setRewardExp(final Integer rewardExp) {
        this.rewardExp = rewardExp;
    }
    
    public Integer getRewardIron() {
        return this.rewardIron;
    }
    
    public void setRewardIron(final Integer rewardIron) {
        this.rewardIron = rewardIron;
    }
    
    public Integer getLv() {
        return this.lv;
    }
    
    public void setLv(final Integer lv) {
        this.lv = lv;
    }
    
    public Integer getGemItem() {
        return this.gemItem;
    }
    
    public void setGemItem(final Integer gemItem) {
        this.gemItem = gemItem;
    }
    
    public Integer getGemNum() {
        return this.gemNum;
    }
    
    public void setGemNum(final Integer gemNum) {
        this.gemNum = gemNum;
    }
}
