package ticketvalidation;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kabbali
 */
public class EzproxyTicket {

    private final String ezProxyStartingPointURL;

    /**
     * Inicialize the ezProxyStartingPointURL value
     *
     * @param ezProxyServerURL
     * @param secret
     * @param user
     */
    public EzproxyTicket(String ezProxyServerURL, String secret, String user) {

        String ezProxyTicket = "";
        try {
            String packet = "$u" + Long.toString(System.currentTimeMillis());
            packet = packet.substring(0, packet.length() - 3) + "$e";
            String md5string = md5(secret + user + packet);
            ezProxyTicket = URLEncoder.encode(md5string + packet, "UTF-8");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EzproxyTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.ezProxyStartingPointURL = ezProxyServerURL + "/login?user=" + URLEncoder.encode(user) + "&ticket=" + ezProxyTicket;

    }

    /**
     *
     * @param url
     * @return entire url which contains all needed parameters to recognize the
     * user as a valid one
     */
    public String URL(String url) {
        return this.ezProxyStartingPointURL + "&url=" + url;
    }

    /**
     *
     * @param input: the string which will be converted to md5
     * @return md5 hash
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String input) throws NoSuchAlgorithmException {
        String result = input;
        if (input != null) {
            MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
            md.update(input.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            while (result.length() < 32) { //40 for SHA-1
                result = "0" + result;
            }
        }
        return result;
    }

}