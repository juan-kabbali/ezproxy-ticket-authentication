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

    function EZproxyTicket($EZproxyServerURL, $secret, $user, $groups = "")
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

    function URL($url)
    {
        return $this->EZproxyStartingPointURL . "&url=" . $url;
    }
}

?>