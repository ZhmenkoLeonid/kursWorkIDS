package com.Netflow.V5;

import com.IP.User;
import com.Netflow.NetflowPacket;
import com.Netflow.Protocol;

public class NetflowPacketV5 extends NetflowPacket {
    private final int version = 5;

    public NetflowPacketV5(String srcIpAddress, String dstIpAddress, String srcPort, String dstPort
            ,Protocol protocol, long timestamp) {
        this.srcIpAddress = srcIpAddress;
        this.dstIpAddress = dstIpAddress;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        this.timestamp = timestamp;
        this.protocol = protocol;
        User.getUserByIpAddress(srcIpAddress).protocolList.addFlow(this);
    }

    public int getVersion() {
        return version;
    }
/*
    public static List<NetflowPacket>[] getAllLists(){
        List<NetflowPacket>[] allLists = new LinkedList[Protocol.values().length];
        allLists[0] = NetflowPacketV5TCP.getListOfPackets();
        allLists[1] = NetflowPacketV5UDP.getListOfPackets();
        allLists[2] = NetflowPacketV5ICMP.getListOfPackets();
        allLists[3] = NetflowPacketV5IGMP.getListOfPackets();
        return allLists;
    }
*/
    @Override
    public String toString() {
        return "{" +
                "version=" + this.version +
                ", srcIpAddress=" + this.srcIpAddress +
                ", dstIpAddress='" + this.dstIpAddress + '\'' +
                ", srcPort=" + srcPort +
                ", dstPort=" + dstPort +
                ", protocol=" + protocol +
                ", timestamp=" + new java.text.SimpleDateFormat("HH:mm:ss.S")
                                                .format(new java.util.Date(timestamp)) +
                '}';
    }

}
