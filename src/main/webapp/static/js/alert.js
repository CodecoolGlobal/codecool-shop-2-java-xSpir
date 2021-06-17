if (document.URL.match("[\?]")) {
    let alertParams = new URLSearchParams(document.URL.split('?')[1]);

    console.log(alertParams.get("redir"));
    console.log(alertParams.keys());

    switch (alertParams.get("redir")) {
        case "payment_fail":
            alert("Payment unsuccessful due to invalid payment data or insufficient funds");
            break;
        case "empty_cart":
            alert("You may not pay with an empty cart");
            break;
        default:
            alert("Invalid redirect");
            break;
    }
}
