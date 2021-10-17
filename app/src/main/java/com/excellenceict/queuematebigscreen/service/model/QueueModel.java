package com.excellenceict.queuematebigscreen.service.model;

public class QueueModel {
    private String roomNumber;
    private String currentQueueId;
    private String currentPersionName;
    private String nextQueueId;
    private String nextPersionName;
    private String waitingQueueId;
    private String waitingPersionName;

    public QueueModel(String roomNumber, String currentQueueId, String currentPersionName, String nextQueueId,
                      String nextPersionName, String waitingQueueId, String waitingPersionName) {
        this.roomNumber = roomNumber;
        this.currentQueueId = currentQueueId;
        this.currentPersionName = currentPersionName;
        this.nextQueueId = nextQueueId;
        this.nextPersionName = nextPersionName;
        this.waitingQueueId = waitingQueueId;
        this.waitingPersionName = waitingPersionName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCurrentQueueId() {
        return currentQueueId;
    }

    public void setCurrentQueueId(String currentQueueId) {
        this.currentQueueId = currentQueueId;
    }

    public String getCurrentPersionName() {
        return currentPersionName;
    }

    public void setCurrentPersionName(String currentPersionName) {
        this.currentPersionName = currentPersionName;
    }

    public String getNextQueueId() {
        return nextQueueId;
    }

    public void setNextQueueId(String nextQueueId) {
        this.nextQueueId = nextQueueId;
    }

    public String getNextPersionName() {
        return nextPersionName;
    }

    public void setNextPersionName(String nextPersionName) {
        this.nextPersionName = nextPersionName;
    }

    public String getWaitingQueueId() {
        return waitingQueueId;
    }

    public void setWaitingQueueId(String waitingQueueId) {
        this.waitingQueueId = waitingQueueId;
    }

    public String getWaitingPersionName() {
        return waitingPersionName;
    }

    public void setWaitingPersionName(String waitingPersionName) {
        this.waitingPersionName = waitingPersionName;
    }

    @Override
    public String toString() {
        return "QueueModel{" +
                "roomNumber='" + roomNumber + '\'' +
                ", currentQueueId='" + currentQueueId + '\'' +
                ", currentPersionName='" + currentPersionName + '\'' +
                ", nextQueueId='" + nextQueueId + '\'' +
                ", nextPersionName='" + nextPersionName + '\'' +
                ", waitingQueueId='" + waitingQueueId + '\'' +
                ", waitingPersionName='" + waitingPersionName + '\'' +
                '}';
    }
}
