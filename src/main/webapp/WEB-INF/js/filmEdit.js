var form  = document.getElementsByTagName('form')[0];
var text = document.getElementById('values');
var error = document.querySelector('.error');

text.addEventListener("input", function (event) {
    // Каждый раз, когда пользователь вводит что-либо, мы проверяем,
    // является ли корректным поле электронной почты.
    if (text.validity.valid) {
        // В случае появления сообщения об ошибке, если поле
        // является корректным, мы удаляем сообщение об ошибке.
        error.innerHTML = ""; // Сбросить содержимое сообщения
        error.className = "error"; // Сбросить визуальное состояние сообщения
    }
}, false);

form.addEventListener("submit", function (event) {
    // Каждый раз, когда пользователь пытается отправить данные, мы проверяем
    // валидность поля электронной почты.

    if (text.validity.patternMismatch) {

        // Если поле невалидно, отображается пользовательское
        // сообщение об ошибке.
        error.innerHTML = "Incorrect input.";
        error.className = "error active";
        // И мы предотвращаем отправку формы путем отмены события
        event.preventDefault();
    }
}, false);