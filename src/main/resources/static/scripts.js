"use strict"
const tableContainer = document.getElementById("bookTableContainer");
const table = document.getElementById("bookTable");
const tableBody = document.getElementById("bookTableBody");
const createForm = document.getElementById("addBookForm");
const updateForm = document.getElementById("updateBookForm")
let everyAuthor = [];


$(document).ready(function () {
    $('.js-example-basic-multiple').select2({
        placeholder: "Select Author(s)",
        tags: true,
        maximumSelectionSize: 10,
    });
    getAuthorsForSelect();
    $("#bookTableBody").on("click", "tr", function () {
        let id = $(this).attr("id");
        getBookForUpdate(id);
        $("#bookTable").toggle();
        $("#updateFormContainer").toggle();
        $('.js-example-basic-multiple').select2({
            placeholder: "Select Author(s)",
            tags: true,
            maximumSelectionSize: 10,
        });
        console.log(typeof id + id);
    });
});



$("form", "#addFormContainer").submit(function (event) {
    event.preventDefault();
    let formData = $(this).serializeObject();
    let repeatedAuthors = [];
    if (!jQuery.isEmptyObject(formData.authors)) {
        if (formData.authors.length > 1) {
            for (let checkedAuthor in formData.authors) {
                for (let author of everyAuthor) {
                    if (author.penName === formData.authors[checkedAuthor].penName) {
                        formData.authors.splice(checkedAuthor, 1);
                        repeatedAuthors.push(author);
                    }
                }
            }
        }
        else {
            for (let author of everyAuthor) {
                console.log(author.penName);
                console.log(formData.authors.penName);
                if (author.penName === formData.authors.penName) {
                    formData.authors.splice(checkedAuthor, 1);
                    repeatedAuthors.push(author);
                }
            }
        }
    }
    addBook(formData, repeatedAuthors);
});

$("form", "#updateFormContainer").submit(function (event) {
    event.preventDefault();
    let formData = $(this).serializeObject();
    let updatedAuthors = [];
    if (!jQuery.isEmptyObject(formData.authors)) {
        let authorIndex = -1;
        for (let checkedAuthor of formData.authors) {
            authorIndex++;
            for (let author of everyAuthor) {
                console.log(formData.authors)
                console.log(checkedAuthor.penName)
                if (author.penName === checkedAuthor.penName) {
                    updatedAuthors.push(author);
                }
                else {
                    updatedAuthors.push(checkedAuthor);
                }
            }
        }
    }
    formData.authors = undefined;

    console.log(formData.authors);

    updateBook(formData, updatedAuthors, formData.id);
})


function serializeLogic(value, name, object, arrayField) {
    if (object[name]) {
        if (!object[name].push) {
            object[name] = [object[name]];
        }
        object[name].push(value);
    }
    else if (arrayField) {
        object[name] = [];
        object[name].push(value);
    }
    else {
        object[name] = value;
    }
}

$.fn.serializeObject = function () {
    var serializedObject = {};
    var serializedArray = this.serializeArray();
    $.each(serializedArray, function () {
        if (this.name.indexOf("[") != -1) {
            let adjustedString = this.name.replace(/]/g, "").split("[");
            let obj = {};
            obj[adjustedString[1]] = this.value || "";
            serializeLogic(obj, adjustedString[0], serializedObject, true);
        }
        else {
            serializeLogic(this.value || "", this.name, serializedObject, false);
        }
    });
    return serializedObject;
};

function authorExists(checkedAuthor, allAuthors) {
    for (author of allAuthors) {
        if (author.penName === checkedAuthor) {
            return true;
        };
    };
    return false;
}

function getAllAuthors() {
    let authorList = {};
    axios.get("http://localhost:8080/author/getAll")
        .then((response) => {
            authorList["authors"] = response.data;
        }).catch((error) => {
            console.error(error);
        });
    return authorList;
}

function getAuthorsForSelect() {
    axios.get("http://localhost:8080/author/getAll")
        .then((response) => {
            populateAuthorsSelect(response.data);
        }).catch((error) => {
            console.error(error);
        });
}

function populateAuthorsSelect(authorList) {
    $("#authors").empty();
    everyAuthor = authorList;
    for (let author of authorList) {
        let optionValue = author.penName;
        let newOption = new Option(optionValue, optionValue, false, false);
        $("#authors").append(newOption).trigger("change");
    }
}

function createTestBook() {
    let book = {
        authors: [{ penName: "Terry Pratchett" }, { penName: "Neil Gaiman" }],
        title: "Good Omens",
        isbn: "1234567890",
        series: "N/A",
        timesRead: 0,
        owned: "WISHLIST",
        completion: "TO_READ"
    };
    addBook(book);
}
const config = { headers: { 'Content-Type': 'application/json' } };

function addBook(book, repeatedAuthors) {
    console.log(book);
    axios.post("http://localhost:8080/book/createBook", book, config)
        .then((response) => {
            appendRepeatedAuthors(response.data.id, repeatedAuthors);
        }).catch((error) => {
            console.error(error);
        });
}

function updateBook(book, authorList, id) {
    console.log(book);
    console.log(authorList);
    console.log(typeof id);
    let updateUrl = `http://localhost:8080/book/updateBook/${id}`;
    axios.put(updateUrl, book, config)
        .then((response) => {
            console.log(response);
            updateBookAuthors(authorList, id);
        }).catch((error) => {
            console.error(error);
        });
}

function updateBookAuthors(authorList, id) {
    let updateUrl = `http://localhost:8080/book/updateBookAuthors/${id}`;
    axios.patch(updateUrl, authorList)
        .then((response) => {
            console.log(response);
        }).catch((error) => {
            console.error(error);
        });
}

function appendRepeatedAuthors(id, authorList) {
    let appendUrl = `http://localhost:8080/book/appendAuthor/${id}`;
    axios.patch(appendUrl, authorList)
        .then((response) => {
            console.log(response);
        }).catch((error) => {
            console.error(error);
        });
}

function getBookForUpdate(id) {
    let getOneUrl = `http://localhost:8080/book/get/${id}`
    axios.get(getOneUrl)
        .then((response) => {
            console.log(response.data);
            prepopulateForm(response.data);
        }).catch((error) => {
            console.error(error);
        });
}

function getBooks(clickable) {
    axios.get("http://localhost:8080/book/getAll")
        .then((response) => {
            constructTableBody(response.data, clickable);
        }).catch((error) => {
            console.error(error);
        });
}

function addRow(book, clickable) {
    let row = document.createElement("tr");
    row.setAttribute("id", book.id);

    let titleCell = document.createElement("td");
    titleCell.innerHTML = book.title;
    row.appendChild(titleCell);

    let authorsCell = document.createElement("td");
    let authorsString = "";
    for (let author of book.authors) {
        authorsString += author.penName + ", ";
    }
    authorsString = authorsString.replace(/,\s*$/, "");
    authorsCell.innerHTML = authorsString;
    row.appendChild(authorsCell);

    let isbnCell = document.createElement("td");
    isbnCell.innerHTML = book.isbn;
    row.appendChild(isbnCell);

    let seriesCell = document.createElement("td");
    seriesCell.innerHTML = book.series;
    row.appendChild(seriesCell);

    let ownedCell = document.createElement("td");
    ownedCell.innerHTML = book.owned;
    row.appendChild(ownedCell);

    let completionCell = document.createElement("td");
    completionCell.innerHTML = book.completion;
    row.appendChild(completionCell);

    let timesReadCell = document.createElement("td");
    timesReadCell.innerHTML = book.timesRead;
    row.appendChild(timesReadCell);

    tableBody.appendChild(row);
}

function constructTableBody(bookList, clickable) {
    clearTableBody();
    if (!jQuery.isEmptyObject(bookList)) {
        for (let book of bookList) {
            addRow(book, clickable);
        }
        $("#bookTable").toggle();
    }
    else {
        let message = document.createElement("h3");
        message.innerHTML = "There Are Currently No Saved Books";
        message.classList.add("blank-table-background");
        tableContainer.appendChild(message);
    }
}

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
        console.log(formField.prop("type") + key);
    })

}

function clearTableBody() {
    tableBody.innerHTML = "";
}
