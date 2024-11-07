

//button hover effect
document.addEventListener("DOMContentLoaded", function() {
    const buttons = document.querySelectorAll(".btn");
    buttons.forEach(button => {
        button.addEventListener("mouseenter", function() {
            button.style.backgroundColor = "#228b22";
        });
        button.addEventListener("mouseleave", function() {
            button.style.backgroundColor = "";
        });
    });
});












