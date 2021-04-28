package com;

import com.Netflow.NetflowPacket;

import java.util.List;

public class Util {
    public static void printNetflowList(List<NetflowPacket> list){
        for (NetflowPacket flow: list) {
            System.out.println(flow.toString());
        }
    }
}
