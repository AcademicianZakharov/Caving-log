
//event listener for form submission
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("registrationForm");
    if (form) {
        form.onsubmit = function(event) {
            if (!validateForm()) {
                event.preventDefault();
            }
        };
    }
});
//button hover effect
document.addEventListener("DOMContentLoaded", function() {
    const buttons = document.querySelectorAll(".btn");
    buttons.forEach(button => {
        button.addEventListener("mouseenter", function() {
            button.style.backgroundColor = "#45a049";
        });
        button.addEventListener("mouseleave", function() {
            button.style.backgroundColor = "";
        });
    });
});
//confirmation message
document.addEventListener("DOMContentLoaded", function() {
    const submitButton = document.getElementById("submitBtn");
    if (submitButton) {
        submitButton.addEventListener("click", function() {
            alert("Form submitted successfully!");
        });
    }
});











