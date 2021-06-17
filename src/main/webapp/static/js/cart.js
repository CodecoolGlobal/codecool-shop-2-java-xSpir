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
        let price = result.split(' ')[1];
        document.getElementById(`price${id}`).textContent = price + " " + result.split(' ')[2];
    }
}

async function sendRmf(id) {
    let xhttp = new XMLHttpRequest();
    await xhttp.open("GET", "/cart?rmf="+id, true);
    xhttp.send();
}