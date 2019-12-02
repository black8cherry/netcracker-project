var form  = document.getElementsByTagName('form')[0];
var text = document.getElementById('values');
var error = document.querySelector('.error');

text.addEventListener("input", function (event) {
    if (text.validity.valid) {
        error.className = "error";
    }
}, false);

form.addEventListener("submit", function (event) {

    if (text.validity.patternMismatch) {
        error.className = "error active";
        event.preventDefault();
    }
}, false);

