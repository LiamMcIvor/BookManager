"use strict"

function getBookForUpdate(id) {
    let getOneUrl = `http://localhost:8080/book/get/${id}`
    axios.get(getOneUrl)
        .then((response) => {
            console.log(response.status);
            prepopulateForm(response.data);
        }).catch((error) => {
            console.error(error);
        });
};

const config = { headers: { 'Content-Type': 'application/json' } };

function deleteBook(id, title) {
    let deleteUrl = `http://localhost:8080/book/delete/${id}`;
    axios.delete(deleteUrl)
        .then((response) => {
            console.log(response);
            if (!alert(`${title} Has Been Deleted`)) location.reload();
        }).catch((error) => {
            console.error(error);
        });
};

function addBook(book, authorList) {
    console.log(book);
    axios.post("http://localhost:8080/book/createBook", book, config)
        .then((response) => {
            updateBookAuthors(authorList, response.data.id);
            if (!alert(`${response.data.title} Has Been Created`)) location.reload();
        }).catch((error) => {
            console.error(error);
        });
};

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
};

function updateBookAuthors(authorList, id) {
    let updateUrl = `http://localhost:8080/book/updateBookAuthors/${id}`;
    axios.patch(updateUrl, authorList)
        .then((response) => {
            console.log(response.status);
            if (!alert(`${response.data.title} Has Been Updated`)) location.reload();
        }).catch((error) => {
            console.error(error);
        });
};

function getBooks() {
    axios.get("http://localhost:8080/book/getAll")
        .then((response) => {
            constructTableBody(response.data);
        }).catch((error) => {
            console.error(error);
        });
};