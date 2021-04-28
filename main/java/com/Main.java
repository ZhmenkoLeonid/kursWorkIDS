package com;

//import com.IP.User;
import com.IP.User;
import com.Netflow.*;
import com.Netflow.V5.HandlerAction;
import com.RouterInteraction.SSH;
import com.Socket.SocketServerThread;
import nettrack.net.netflow.Accountant;
import nettrack.net.netflow.Collector;
import nettrack.net.netflow.V5FlowHandler;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    Accountant accountant = new HandlerAction();
    public static CopyOnWriteArrayList<NetflowPacket> list;

    public static void main(String[] args) throws UnknownHostException, SocketException, InterruptedException {
        /*
        String user = "admin";
        String password = "135790";
        String host = "192.168.1.1";
        int port = 22;
        //String remoteFile = "/home/john/test.txt";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            Channel channel=session.openChannel("shell");
            //((ChannelExec)channel).setCommand("show ip route");
            //((ChannelExec)channel).setErrStream(System.err);
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            InputStream in=channel.getInputStream();
            OutputStream out=channel.getOutputStream();

            channel.connect(3*1000);

            Thread readThread = new ReadThread(in,channel);
            readThread.start();
            Thread.sleep(1000);
            out.write("show running-config\n".getBytes());
            out.flush();
            Thread.sleep(1000);
            out.write("show ip route\n".getBytes());
            out.flush();
            readThread.join();
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        //new User("Me","192.168.1.4");
        //new User("phone","192.168.1.5");
        //new ProtocolList();
        InetAddress source = InetAddress.getByName("192.168.1.1");

        Collector collector = new Collector(2055);
        V5FlowHandler handler = new V5FlowHandler(source, 100);
        handler.addAccountant(new HandlerAction());
        collector.addFlowHandler(handler);
        collector.start();
        new NetflowPacketDeleteByTimeThread(30 * 60 * 1000);
        try {
            while (true) {
                System.out.println("--------START FLOW-------------");
                for (User user : User.getUserList()) {
                    System.out.println("User " + user.getHostName() + ": " + user.getIpAddress());

                    for (List<NetflowPacket> netflowPackets : user.getAllProtocolLists()) {
                        if (netflowPackets.size() == 0) {
                            continue;
                        }
                        System.out.println("--------" + netflowPackets.get(0).getProtocol() + "-------------");
                        Util.printNetflowList(new CopyOnWriteArrayList<>(netflowPackets));
                    }
                    System.out.print(UserStatistics.getUserStats(user));
                    System.out.println("Количество уникальных tcp-портов назначения: " +
                            UserStatistics.getUserUniqueProtocolDstPortsCount(user, Protocol.TCP));
                    System.out.println("Количество уникальных udp-портов назначения: " +
                            UserStatistics.getUserUniqueProtocolDstPortsCount(user, Protocol.UDP));
                }
                System.out.println("---------END FLOW--------------");
                Thread.sleep(2000);
            }
                /*
                CopyOnWriteArrayList<NetflowPacketV5> list = new CopyOnWriteArrayList<>(NetflowPacketV5.getAllPackets());
                ListIterator<NetflowPacketV5> iterator = list.listIterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next().toString());
                }
                // for (NetflowPacketV5 flow: NetflowPacketV5.getAllPackets()) {
                //   System.out.println(flow.toString());
                //}


                Thread.sleep(2000);
            }
        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }*/
        /*
        try {
            new SSH();
            System.out.println("result:"+SSH.sendCommand("show ip route"));
            System.out.println("result:"+SSH.sendCommand("show running-config"));
            Thread.sleep(1000*60*3);
            System.out.println("result:"+SSH.sendCommand("show running-config"));
         */
        } catch (Exception e) {
            e.printStackTrace();
        }
        SocketServerThread socketServerThread = new SocketServerThread();
    }

}