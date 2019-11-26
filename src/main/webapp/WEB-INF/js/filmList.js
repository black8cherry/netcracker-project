var form  = document.getElementsByTagName('form')[0];
var text = document.getElementById('values');
var error = document.querySelector('.error');

text.addEventListener("input", function (event) {
    if (text.validity.valid) {
        error.innerHTML = "";
        error.className = "error";
    }
}, false);

form.addEventListener("submit", function (event) {

    if (text.validity.patternMismatch) {
        error.innerHTML = "Incorrect input.";
        error.className = "error active";
        event.preventDefault();
    }
}, false);

