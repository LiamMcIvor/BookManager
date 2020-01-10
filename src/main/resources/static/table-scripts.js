"use strict"
const tableContainer = document.getElementById("bookTableContainer");
const table = document.getElementById("bookTable");
const tableBody = document.getElementById("bookTableBody");
const searchBar = document.getElementById("searchBar");
const deletePopup = document.getElementById("deletePopup");
const popup = document.getElementById("popup");

let everyBook = [];

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
};

function constructTableBody(bookList) {
    clearTableBody();
    if (!jQuery.isEmptyObject(bookList)) {
        for (let book of bookList) {
            addRow(book);
        }
        $("#bookTable").toggle();
    }
    else {
        tableContainer.innerHTML="";
        let message = document.createElement("h3");
        message.innerText = "There Are Currently No Saved Books";
        message.classList.add("blank-table-background");
        tableContainer.appendChild(message);
    }
};

function getBooks(clickRow) {
    axios.get("http://3.9.214.211:8181/BookManager/book/getAll")
        .then((response) => {
            console.log(response.status)
            constructTableBody(response.data);
            $("#bookTable").DataTable({
                paging: false,
                scrollY: true,
                info: false,
            });
            if (clickRow) {
                clickable();
            }
        }).catch((error) => {
            console.error(error);
        });
};

function clearTableBody() {
    tableBody.innerHTML = "";
};

function clickable() {
    $("#bookTableBody").on("click", "tr", function () {
        let id = $(this).attr("id");
        let title = $(this).attr("name");
        popup.style.display = "block";
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
            $("#bookTableContainer").toggle();
            $("#updateFormContainer").toggle();
            $('.js-example-basic-multiple').select2({
                placeholder: "Select Author(s)",
                tags: true,
                maximumSelectionSize: 10,
                maximumInputLength: 80
            });
        })
    });
};