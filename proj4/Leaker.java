/**
 * 
 * @author Jahongir Amirkulov
 * @version 04/08/18
 */

 public class Leaker {

    // main method
    public static void main(String[] args) {
        // number of arguements
        if (args.length != 6) usage();

        try {
            // User input
            String rhost = args[0];
            int rport = Integer.parseInt(args[1]);
            String lhost = args[2];
            int lport = Integer.parseInt(args[3]);
            String publicKeyFile = args[4];
            String message = args[5];
            DatagramSocket reporter = new DatagramSocket(new InetSocketAddress (rport, rport));
            LeakerProxy proxy = new LeakerProxy (reporter, new InetSocketAddress(lhost, lport));

            LeakerModel lm = new LeakerModel(message, proxy, publicKeyFile);
        } catch (Exception e) {
            System.err.println("Illegal arguement.");
            usage();
        }

    }

    /**
    * Print a usage message and exit.
    */
    private static void usage() {
        System.err.println ("Usage: java Leaker <rhost> <rport> <lhost> <lport> <publickeyfile> <message>");
        System.exit (1);
    }
 }