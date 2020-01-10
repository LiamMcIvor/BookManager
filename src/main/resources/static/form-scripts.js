"use strict"
const completionRadio = document.getElementById("toRead");

let everyAuthor = [];

$(document).ready(function () {
    $('.js-example-basic-multiple').select2({
        placeholder: "Select Author(s)",
        tags: true,
        maximumSelectionSize: 10,
    });
    getAuthorsForSelect();

});

function getAuthorsForSelect() {
    axios.get("http://localhost:8080/BookManager/author/getAll")
        .then((response) => {
            console.log(response.status)
            populateAuthorsSelect(response.data);
        }).catch((error) => {
            console.error(error);
        });
};

function populateAuthorsSelect(authorList) {
    $("#authors").empty();
    everyAuthor = authorList;
    for (let author of authorList) {
        let optionValue = author.penName;
        let newOption = new Option(optionValue, optionValue, false, false);
        $("#authors").append(newOption).trigger("change");
    }
};

$("form", "#addFormContainer").submit(function (event) {
    event.preventDefault();
    let formData = $(this).serializeObject();
    let repeatedAuthors = arrayDataForSubmit(formData.authors, everyAuthor, "penName");
    delete formData.authors;
    addBook(formData, repeatedAuthors);
});

$("form", "#updateFormContainer").submit(function (event) {
    event.preventDefault();
    let formData = $(this).serializeObject();
    let updatedAuthors = arrayDataForSubmit(formData.authors, everyAuthor, "penName");
    delete formData.authors;
    updateBook(formData, updatedAuthors, formData.id);
});