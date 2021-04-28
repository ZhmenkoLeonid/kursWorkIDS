package com.Netflow.V5;

import com.IP.User;
import com.Netflow.NetflowPacket;
import com.Netflow.Protocol;
import com.Netflow.UnsupportedProtocolException;

public class NetflowPacketFactoryV5 {

    public static NetflowPacket createNetflowClass(String srcIpAddress, String dstIpAddress, String protocol,
                                                   String srcPort, String dstPort, long timestamp)
                                                    throws UnsupportedProtocolException {
        if (!User.isExist(srcIpAddress)){
            new User(srcIpAddress);
        }
        switch (protocol) {
            case "6":
                return new NetflowPacketV5(srcIpAddress,dstIpAddress,srcPort,dstPort, Protocol.TCP,timestamp);
            case "17":
                return new NetflowPacketV5(srcIpAddress,dstIpAddress,srcPort,dstPort,Protocol.UDP,timestamp);
            case "1":
                return new NetflowPacketV5(srcIpAddress,dstIpAddress,srcPort,dstPort,Protocol.ICMP,timestamp);
            case "2":
                return new NetflowPacketV5(srcIpAddress,dstIpAddress,srcPort,dstPort,Protocol.IGMP,timestamp);
            default:
                throw new UnsupportedProtocolException(protocol);
        }
    }
}
