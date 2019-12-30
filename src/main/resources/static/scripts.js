"use strict"
const table = document.getElementById("bookTable");
const tableBody = document.getElementById("bookTableBody");
const form = document.getElementById("addBookForm");
let authorList;

$(document).ready(function() {
    $('.js-example-basic-multiple').select2({
        placeholder: "Select Author(s)",
        tags: true,
        maximumSelectionSize: 10
    });
});

$("form").submit(function(event) {
    event.preventDefault();
    let formData = $(this).serializeObject();
    addBook(formData);
});

function serializeLogic(value, name, object) {
    if (object[name]) {
        if (!object[name].push) {
            object[name] = [object[name]];
        }
        object[name].push(value);
    } else {
        object[name] = value;
    }
}

$.fn.serializeObject = function() {
    var serializedObject = {};
    var serializedArray = this.serializeArray();
    $.each(serializedArray, function() {
        if (this.name.indexOf("[") != -1) {
            let adjustedString = this.name.replace(/]/g, "").split("[");
            let obj = {};
            obj[adjustedString[1]] = this.value || "";
            serializeLogic(obj, adjustedString[0], serializedObject)
        }
        else {
            serializeLogic(this.value || "", this.name, serializedObject);
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
    axios.get("http://localhost8080/author/getAll")
    .then((response) => {
        authorList = response.data;
    }).catch((error) => {
        console.error(error)
    });
}

function createTestBook() {
    let book = {
        authors : [{penName : "Terry Pratchett"}, {penName : "Neil Gaiman"}],
        title : "Good Omens",
        isbn : "1234567890",
        series : "N/A",
        timesRead : 0,
        owned : "WISHLIST",
        completion : "TO_READ"};
    addBook(book);
}
const config = {headers: {'Content-Type': 'application/json'}};

function addBook(book) {
    console.log(book);
    axios.post("http://localhost:8080/book/createBook", book, config)
        .then((response) => {
        console.log(response);
    }).catch((error) => {
        console.error(error);
    });
}

function getBooks() {
    axios.get("http://localhost:8080/book/getAll")
    .then((response) => {
        constructTableBody(response.data);
    }).catch((error) => {
        console.error(error);
    });
}

function addRow(book) {
    let row = document.createElement("tr");

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

function constructTableBody(bookList) {
    clearTableBody();
    for (let book of bookList) {
        addRow(book);
    }
}

function clearTableBody() {
    tableBody.innerHTML = "";
}