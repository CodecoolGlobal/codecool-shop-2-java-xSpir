if (document.URL.match("[\?]")) {
    let alertParams = new URLSearchParams(document.URL.split('?')[1]);

    switch (alertParams.get("redir")) {
        case "payment_fail":
            alert("Payment unsuccessful due to invalid payment data or insufficient funds");
            break;
        case "empty_cart":
            alert("You may not pay with an empty cart");
            break;
        case null:
            break;
        default:
            alert("Invalid redirect");
            break;
    }
}
