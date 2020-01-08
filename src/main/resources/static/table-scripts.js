"use strict"
const tableContainer = document.getElementById("bookTableContainer");
const table = document.getElementById("bookTable");
const tableBody = document.getElementById("bookTableBody");

function addRow(book) {
    let row = document.createElement("tr");
    row.setAttribute("id", book.id);
    row.setAttribute("name", book.title);

    let titleCell = document.createElement("td");
    titleCell.innerText = book.title;
    row.appendChild(titleCell);

    let authorsCell = document.createElement("td");
    let authorsString = "";
    for (let author of book.authors) {
        authorsString += author.penName + ", ";
    }
    authorsString = authorsString.replace(/,\s*$/, "");
    authorsCell.innerText = authorsString;
    row.appendChild(authorsCell);

    let seriesCell = document.createElement("td");
    seriesCell.innerText = book.series;
    row.appendChild(seriesCell);

    let ownedCell = document.createElement("td");
    ownedCell.innerText = book.owned;
    row.appendChild(ownedCell);

    let completionCell = document.createElement("td");
    completionCell.innerText = book.completion;
    row.appendChild(completionCell);

    let timesReadCell = document.createElement("td");
    timesReadCell.innerText = book.timesRead;
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
        message.innerText = "There Are Currently No Saved Books";
        message.classList.add("blank-table-background");
        tableContainer.appendChild(message);
    }
}

function clearTableBody() {
    tableBody.innerHTML = "";
}