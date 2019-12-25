"use strict"
const table = document.getElementById("bookTable");
const tableBody = document.getElementById("bookTableBody");

function addRow(book, authorList) {
    let row = document.createElement("tr");

    let titleCell = document.createElement("td");
    titleCell.innerHTML = book.title;
    row.appendChild(titleCell);

    let authorsCell = document.createElement("td");
    let authors;
    for (author of authorList) {
        authors += author.penName + ", ";
    }
    authorsCell.innerHTML = authors;
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

function constructTableBody() {
    clearTableBody();


}

function getBooks() {
    axios.get("http://localhost:8080/book/getAll")
    .then((response) => {
        console.log(response);
    }).catch((error) => {
        console.error(error);
    });
}

function clearTableBody() {
    tableBody.innerHTML = "";
}