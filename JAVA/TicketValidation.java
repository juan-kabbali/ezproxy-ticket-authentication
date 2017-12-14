package ticketvalidation;

/**
 *
 * @author Kabbali
 */
public class TicketValidation {

    private static final String URL_PROXY = "http://recursosdigitales.comfenalcoantioquia.com";
    private static final String URL_MENU = URL_PROXY + "/menu";


    public static void main(String[] args) {

        // the url generated has the ticket as a query string parameter,
        // with this url, the proxy will recognize the user as a valid one
        EzproxyTicket ezproxy = new EzproxyTicket(URL_PROXY, "shhhh!", "username");
        System.out.println(ezproxy.URL(URL_MENU));

    }

}
