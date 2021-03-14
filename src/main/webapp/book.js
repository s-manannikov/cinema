$(document).ready(function getTicketInfo() {
    const price = 400;
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cinema/book',
    }).done(function (data) {
        let info = document.getElementById('info');
        let choice = document.createTextNode('Your choice:');
        let li = document.createElement('li');
        li.style.fontSize = '14px';
        li.appendChild(choice);
        info.appendChild(li)
        for (let i = 0; i < data.length; i++) {
            let row = data[i].toString().split('')[0];
            let seat = data[i].toString().split('')[1];
            let li = document.createElement('li');
            li.style.fontSize = '14px';
            let text = document.createTextNode('row ' + row + ' seat ' + seat);
            li.appendChild(text);
            info.appendChild(li)
        }
        let cost = document.createTextNode('Total price is: ' + data.length * price + ' RUB');
        let li2 = document.createElement('li');
        li2.style.fontSize = '14px';
        li2.appendChild(cost);
        info.appendChild(li2)
    });
});

function validate() {
    const message = 'All fields are required!';
    const name = $('#username').val();
    const phone = $('#phone').val();
    if ((name === '') || (phone === '')) {
        alert(message);
        return false;
    }
}

$(function() {
    $("#phone").mask("+7 (999) 999-9999");
});