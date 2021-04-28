package com.RouterInteraction;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;

public class SSH {
    private static final int port = 22;
    private static String user;
    private static String password;
    private static String host;
    private static JSch jsch;
    private static Session session;
    private static Channel channel;
    private static InputStream in;
    private static OutputStream out;

    //String remoteFile = "/home/john/test.txt";
    public SSH(String usr, String pass, String hostIP) throws InterruptedException,
            IOException, JSchException {
        user = usr;
        password = pass;
        host = hostIP;
        jsch = new JSch();
        session = jsch.getSession(user, host, port);
        session.setPassword(pass);
        session.setConfig("StrictHostKeyChecking", "no");
    }

    public SSH() throws IOException, InterruptedException, JSchException {
        user = "admin";
        password = "135790";
        host = "192.168.1.1";
        jsch = new JSch();
        session = jsch.getSession(user, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
    }


    public static String sendCommand(String command) throws JSchException, IOException, InterruptedException {
        if (!session.isConnected()) {
            establishConnection();
        }
        out.write((command + '\n').getBytes());
        out.flush();
        String result = "";
        byte[] answer = new byte[1024];
        Thread.sleep(1000);
        while (in.available() > 0) {
            int i = in.read(answer, 0, 1024);
            result += new String(answer, 0, i);
           // if (i < 0) break;
        }
        return result;
    }

    private static void establishConnection() throws JSchException, IOException, InterruptedException {
        System.out.println("Establishing Connection...");
        session.connect();
        System.out.println("Connection established.");
        channel = session.openChannel("shell");
        channel.setOutputStream(System.out);
        channel.setInputStream(System.in);
        in = channel.getInputStream();
        out = channel.getOutputStream();
        channel.connect();
        Thread.sleep(1000);
    }
    /*
    {
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");
        session.connect();
        System.out.println("Connection established.");
        Channel channel = session.openChannel("shell");
        //((ChannelExec)channel).setCommand("show ip route");
        //((ChannelExec)channel).setErrStream(System.err);
        channel.setInputStream(System.in);
        OutputStream out = new BufferedOutputStream(channel.getOutputStream());
        channel.setOutputStream(System.out);
        InputStream in = channel.getInputStream();

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
}

