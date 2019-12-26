"use strict"
const table = document.getElementById("bookTable");
const tableBody = document.getElementById("bookTableBody");

function addBook() {
    axios.post("http://localhost:8080/book/createBook",
    {
    authors : [{penName : "Terry Pratchett"}, {penName : "Neil Gaiman"}],
    title : "Good Omens",
    isbn : "1234567890",
    series : "N/A",
    timesRead : 0,
    owned : "WISHLIST",
    completion : "TO_READ"})
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