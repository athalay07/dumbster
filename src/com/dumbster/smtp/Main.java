package com.dumbster.smtp;

public class Main {
    public static void main(String[] args) {
        SmtpServer server;
        if (args.length == 1) {
            server = SmtpServerFactory.startServer(port(args[0]));
        } else if(args.length == 2){
            String directoryToSaveMails = args[1];
            server = SmtpServerFactory.startServer(port(args[0]), directoryToSaveMails);
        } else {
            server = SmtpServerFactory.startServer();
        }
        server.setThreaded(true);
        System.out.println("Dumbster SMTP Server started.\n");
    }

    private static int port(String s)  {
        return Integer.parseInt(s);
    }

}
