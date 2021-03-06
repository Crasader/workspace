package com.reign.kfzb.domain;

import com.reign.framework.hibernate.model.*;
import java.util.*;

public class KfzbRuntimeMatch implements IModel
{
    private Integer pk;
    private int seasonId;
    private int scheduleId;
    private int player1Id;
    private int player2Id;
    private int player1Win;
    private int player2Win;
    private int layer;
    private int round;
    private int roundWinner;
    private int layerWinner;
    private Date startTime;
    private String reportId;
    private int matchId;
    
    public Integer getPk() {
        return this.pk;
    }
    
    public void setPk(final Integer pk) {
        this.pk = pk;
    }
    
    public int getSeasonId() {
        return this.seasonId;
    }
    
    public void setSeasonId(final int seasonId) {
        this.seasonId = seasonId;
    }
    
    public int getPlayer1Id() {
        return this.player1Id;
    }
    
    public void setPlayer1Id(final int player1Id) {
        this.player1Id = player1Id;
    }
    
    public int getPlayer2Id() {
        return this.player2Id;
    }
    
    public void setPlayer2Id(final int player2Id) {
        this.player2Id = player2Id;
    }
    
    public int getPlayer1Win() {
        return this.player1Win;
    }
    
    public void setPlayer1Win(final int player1Win) {
        this.player1Win = player1Win;
    }
    
    public int getPlayer2Win() {
        return this.player2Win;
    }
    
    public void setPlayer2Win(final int player2Win) {
        this.player2Win = player2Win;
    }
    
    public int getLayer() {
        return this.layer;
    }
    
    public void setLayer(final int layer) {
        this.layer = layer;
    }
    
    public int getRound() {
        return this.round;
    }
    
    public void setRound(final int round) {
        this.round = round;
    }
    
    public int getRoundWinner() {
        return this.roundWinner;
    }
    
    public void setRoundWinner(final int roundWinner) {
        this.roundWinner = roundWinner;
    }
    
    public int getLayerWinner() {
        return this.layerWinner;
    }
    
    public void setLayerWinner(final int layerWinner) {
        this.layerWinner = layerWinner;
    }
    
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }
    
    public String getReportId() {
        return this.reportId;
    }
    
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }
    
    public int getMatchId() {
        return this.matchId;
    }
    
    public void setMatchId(final int matchId) {
        this.matchId = matchId;
    }
    
    public static int getMatchKey(final int matchId, final int round) {
        return matchId << 6 | round;
    }
    
    public static int getMatchIdFromMatchKey(final int matchKey) {
        return matchKey >> 6;
    }
    
    public static int getRoundFromMatchKey(final int matchKey) {
        return matchKey & 0x10;
    }
    
    public int getMatchKey() {
        return getMatchKey(this.matchId, this.round);
    }
}
