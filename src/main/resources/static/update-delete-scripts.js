"use strict"
const popup = document.getElementById("popup");
const close = document.getElementById("close");
const deletePopup = document.getElementById("deletePopup");
const deleteClose = document.getElementById("deleteClose");
const updateForm = document.getElementById("updateBookForm");
const cancelDelete = document.getElementById("cancelDelete");


$(document).ready(function () {
    $("#bookTableBody").on("click", "tr", function () {
        let id = $(this).attr("id");
        let title = $(this).attr("name");
        popup.style.display = "block";
        $("#bookId", popup).html(id);
        $("#delete", popup).on("click", function() {
            $("#deletePopupText", deletePopup).html(`Are you sure you want to delete <br> ${title} <br> from your book collection?`);
            popup.style.display = "none";
            deletePopup.style.display = "block";
            $("#delete", deletePopup).on("click", function() {
                deleteBook(id, title);
                deletePopup.style.display = "none";
            });
        });
        $("#update", popup).on("click", function() {
            popup.style.display = "none";
            getBookForUpdate(id);
            $("#bookTable").toggle();
            $("#updateFormContainer").toggle();
            $('.js-example-basic-multiple').select2({
                placeholder: "Select Author(s)",
                tags: true,
                maximumSelectionSize: 10,
                maximumInputLength: 80
            });
        })
    });
});

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