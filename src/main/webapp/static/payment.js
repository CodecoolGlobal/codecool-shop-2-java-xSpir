// let emailer = document.getElementById('user');
const radios = document.querySelectorAll('input[type=radio]');
const pay = document.getElementById('paywith');
const userData = document.querySelectorAll('.user-data');

// function changeUserData() { //TODO: check for valid email address! ('@')
//     for (let i = 0; i < userData.length; i++) {
//         let content = userData[i].textContent;
//         let input = document.createElement('input');
//         input.value = content;
//         input.className = 'user-data';
//         if (i !== 2 && i !== 3 ) {
//             input.textContent += `<br>`;
//             i++;
//         }
//         userData[i].replaceWith(input);
//     }
//     emailer.textContent = "Save";
//     emailer.removeEventListener('click', changeUserData);
//     emailer.addEventListener('click', saveUserData);
// }
//
// function saveUserData() {
//     for (let i = 0; i < userData.length; i++) {
//         let content = userData[i].value;
//         let p = document.createElement('p');
//         p.textContent = content;
//         p.className = 'user-data';
//         p.style.margin='3px';
//         if (userData[i].nextElementSibling.nodeName === 'BR') userData[i].nextElementSibling.remove();
//         userData[i].replaceWith(p);
//     }
//     emailer.textContent = 'Change data';
//     emailer.removeEventListener('click', saveUserData);
//     emailer.addEventListener('click', changeUserData);
// }

function changePayButton() {
    pay.textContent = 'Pay with ';
    let buttonText = radios[0].checked ? radios[0].nextElementSibling.textContent : radios[1].nextElementSibling.textContent;
    pay.textContent += buttonText;

}

// emailer.addEventListener('click', saveUserData);
changePayButton();
radios.forEach(radio => radio.addEventListener('change', changePayButton));

function showModal() {
    if (this.textContent.includes('Credit')) {
        document.querySelector('.paypal').style.display = 'none';
        document.querySelector('.credit-card').style.display = 'block';
        document.querySelector('.modal-body').style.display = 'flow-root';
    } else {
        document.querySelector('.credit-card').style.display = 'none';
        document.querySelector('.paypal').style.display = 'block';
        document.querySelector('.modal-body').style.display = 'flow-root';
    }
}

pay.addEventListener('click', showModal);

function collectUserData() {
    let customer = {};
    customer['first_name'] = document.getElementById('first').value;
    customer['last_name'] = document.getElementById('last').value;
    customer['post_code'] = document.getElementById('postcode').value;
    customer['city'] = document.getElementById('city').value;
    customer['address'] = document.getElementById('address').value;
    customer['email'] = document.getElementById('email').value;
    if (document.getElementById('card-number1').value) {
        customer['card'] = document.getElementById('card-number1').value;
        customer['card'] += document.getElementById('card-number2').value;
        customer['card'] += document.getElementById('card-number3').value;
    }
    if (document.getElementById('username').value) {
        customer['username'] = document.getElementById('username').value;
    }

    fetch('/confirmation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(customer)
    }).then(resp => {
        if (resp.status === 200) window.location = '/confirmation';
    });
}

document.getElementById('pay').addEventListener('click', collectUserData);

