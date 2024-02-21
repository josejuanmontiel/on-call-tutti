package com.accreativos.comparator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OnCallSchedule {

    Long startDate;
    Long endDate;
    long workerId;
    Integer priority;

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss'Z'");
        return sdf.format(new Date(startDate))+"-"+sdf.format(new Date(endDate))+"-"+workerId+"-"+priority;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = startDate.equals(((OnCallSchedule) obj).getStartDate()) 
            && endDate.equals(((OnCallSchedule) obj).getEndDate())
            && workerId == ((OnCallSchedule) obj).getWorkerId()
            && priority.equals(((OnCallSchedule) obj).getPriority());
        return result;
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss'Z'");
        return sdf.format(new Date(startDate))+","+sdf.format(new Date(endDate))+","+workerId;
    }

}
