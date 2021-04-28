package com.Netflow.V5;

import com.Netflow.UnsupportedProtocolException;
import com.Netflow.V5.NetflowPacketFactoryV5;
import nettrack.net.IpAddr;
import nettrack.net.netflow.*;

public class HandlerAction
        implements Accountant {
    static int count = 1;
    public void account(Flow f) {
        try {
            NetflowPacketFactoryV5.createNetflowClass(IpAddr.toString(f.getSrcAddr()),IpAddr.toString(f.getDstAddr())
                    ,String.valueOf(((V5Flow)f).getProt())
                    ,String.valueOf(f.getSrcPort()),String.valueOf(f.getDstPort()),System.currentTimeMillis());
        } catch (UnsupportedProtocolException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}