package com.reign.gcld.task.message;

public class TaskMessageGeneral extends TaskMessage
{
    private int num;
    
    public TaskMessageGeneral(final int playerId, final int num) {
        super(playerId, 6);
        this.num = num;
    }
    
    public int getNum() {
        return this.num;
    }
}
