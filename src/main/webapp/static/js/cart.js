async function sendModification(id, add) {
    let xhttp = new XMLHttpRequest();
    await xhttp.open("GET", (add ? "/cart?add="+id : "/cart?rm="+id), true);
    xhttp.send();

    await updateQuantity(id);
}

async function updateQuantity(id) {
    const response = await fetch("/cartapi?id="+id);
    const result = await response.text();
    if (result === "null\r\n") location.reload();
    else {
        document.getElementById("quantity"+id).innerText = result.split(' ')[0] + "x";
        let price = Math.round(result.split(' ')[1] * 10 / 10).toFixed(1).toString();
        document.getElementById('price').textContent = price + " " + result.split(' ')[2];
    }
}

async function sendRmf(id) {
    let xhttp = new XMLHttpRequest();
    await xhttp.open("GET", "/cart?rmf="+id, true);
    xhttp.send();
}