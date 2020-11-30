<?php
// If you use a shared secret of shhh, then in user.txt/ezproxy.usr you
// might use:
//      ::Ticket
//      MD5 shhhh
//      /Ticket
// to allow EZproxy to recognzie your tickets.
//
// Once the object is created, you can call its url method with a
// database URL to generate a ticket URL.
class EZproxyTicket
{
    var $EZproxyStartingPointURL;
    /**
     * EZproxyTicket constructor. Inicialize the ezProxyStartingPointURL value
     * @param $EZproxyServerURL
     * @param $secret
     * @param $user
     * @param string $groups
     */
    public function __construct($EZproxyServerURL, $secret, $user, $groups = "")
    {
        if (strcmp($secret, "") == 0) {
            echo("EZproxyURLInit secret cannot be blank");
            exit(1);
        }
        $packet = '$u' . time();
        if (strcmp($groups, "") != 0) {
            $packet .= '$g' . $groups;
        }
        $packet .= '$e';
        $EZproxyTicket = urlencode(md5($secret . $user . $packet) . $packet);
        $this->EZproxyStartingPointURL = $EZproxyServerURL . "/login?user=" . urlencode($user) . "&ticket=" . $EZproxyTicket;
    }
    /**
     * @param $url
     * @return string entire url which contains all needed parameters to recognize the
     * user as a valid one
     */
    function URL($url)
    {
        return $this->EZproxyStartingPointURL . "&url=" . $url;
    }
}
?>
