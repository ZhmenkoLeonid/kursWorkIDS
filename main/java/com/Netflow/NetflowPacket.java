package com.Netflow;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class NetflowPacket {
    protected String srcIpAddress;
    protected String dstIpAddress;
    protected String srcPort;
    protected String dstPort;
    protected Protocol protocol;
    protected long timestamp;
    private static boolean busy = false;

    public static boolean isBusy(){
        return busy;
    }
    public static void swapBusy(){
        busy = !busy;
    }
    public static void waitQueue(){
        try {
            while (busy) {
                Thread.sleep(300);
            }
            swapBusy();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getSrcIpAddress() {
        return srcIpAddress;
    }

    public void setSrcIpAddress(String srcIpAddress) {
        this.srcIpAddress = srcIpAddress;
    }

    public String getDstIpAddress() {
        return dstIpAddress;
    }

    public void setDstIpAddress(String dstIpAddress) {
        this.dstIpAddress = dstIpAddress;
    }


    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
