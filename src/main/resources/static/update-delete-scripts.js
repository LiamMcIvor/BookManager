"use strict"
const close = document.getElementById("close");
const deleteClose = document.getElementById("deleteClose");
const updateForm = document.getElementById("updateBookForm");
const cancelDelete = document.getElementById("cancelDelete");

close.onclick = function() {
    popup.style.display = "none";
};
deleteClose.onclick = function() {
    deletePopup.style.display = "none";
};
cancelDelete.onclick = function() {
    deletePopup.style.display = "none";
};

window.onclick = function(event) {
    if (event.target == popup) {
        popup.style.display = "none";
    }
    else if (event.target == deletePopup) {
        deletePopup.style.display = "none";
    };
};

function prepopulateForm(bookData) {
    $.each(bookData, function (key, value) {
        let formField;
        if (key === "authors") {
            let selectedAuthors = [];
            formField = $("#authors", updateForm);
            for (let author of bookData.authors) {
                selectedAuthors.push(author.penName);
            }
            formField.val(selectedAuthors);
            formField.trigger("change");
        }
        else {
            formField = $(`[name=${key}]`, updateForm);
            switch (formField.prop("type")) {
                case "radio":
                    formField.each(function () {
                        if ($(this).attr("value") === value) {
                            $(this).attr("checked", value);
                        };
                    });
                    break;
                default:
                    formField.val(value);
            }
        };
    });
};