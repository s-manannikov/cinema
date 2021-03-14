$(document).ready(function getSeats() {
    setTimeout(function() { location.reload(); }, 60000);
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cinema/seats',
    }).done(function (data) {
        for (let i = 0; i < data.length; i++) {
            const a = '#' + data[i];
            $(a).attr('disabled', true).attr('checked', true)
        }
    });
})

function validate() {
    const message = 'Choose at least one seat!';
    const seats = document.getElementsByName("seat");
    for (let i = 0; i < seats.length; i++) {
        if (seats[i].checked === true && seats[i].disabled === false) {
            popup();
            return true;
        }
    }
    alert(message);
    return false;
}

function popup() {
    window.open(
        "booking.html",
        "child",
        "toolbar=no, scrollbars=no, resizable=yes, top=200, left=400, width=600, height=600, location=no, title=yes"
    );
}