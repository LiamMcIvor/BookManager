"use strict"

function getBookForUpdate(id) {
    let getOneUrl = `http://3.9.214.211:8181/BookManager/book/get/${id}`
    axios.get(getOneUrl)
        .then((response) => {
            console.log(response.status);
            prepopulateForm(response.data);
        }).catch((error) => {
            console.error(error);
            if (error.response.status === 404) {
                alert("ID Not Found");
            };
        });
};

const config = { headers: { 'Content-Type': 'application/json' } };

function deleteBook(id, title) {
    let deleteUrl = `http://3.9.214.211:8181/BookManager/book/delete/${id}`;
    axios.delete(deleteUrl)
        .then((response) => {
            console.log(response.status);
            if (!alert(`${title} Has Been Deleted`)) location.reload();
        }).catch((error) => {
            console.error(error);
            if (error.response.status === 404) {
            alert("ID Not Found");
            };
        });
};

function addBook(book, authorList) {
    console.log(book);
    axios.post("http://3.9.214.211:8181/BookManager/book/createBook", book, config)
        .then((response) => {
            console.log(response.status)
            updateBookAuthors(authorList, response.data.id);
            if (!alert(`${response.data.title} Has Been Created`)) location.reload();
        }).catch((error) => {
            console.error(error);
            if (error.response.status === 409) {
                alert("Please Ensure You Have Not Duplicated A Book Title");
            }
            else if (error.response.status === 406) {
                alert("Please Ensure The Entry Fields Obey The Specified Limits");
            };
        });
};

function updateBook(book, authorList, id) {
    console.log(book);
    console.log(authorList);
    console.log(typeof id);
    let updateUrl = `http://3.9.214.211:8181/BookManager/book/updateBook/${id}`;
    axios.put(updateUrl, book, config)
        .then((response) => {
            console.log(response.status);
            updateBookAuthors(authorList, id);
        }).catch((error) => {
            console.error(error);
            if (error.response.status === 409) {
                alert("Please Ensure You Have Not Duplicated A Book Title");
            }
            else if (error.response.status === 406) {
                alert("Please Ensure The Entry Fields Obey The Specified Limits");
            };
        });
};

function updateBookAuthors(authorList, id) {
    let updateUrl = `http://3.9.214.211:8181/BookManager/book/updateBookAuthors/${id}`;
    axios.patch(updateUrl, authorList)
        .then((response) => {
            console.log(response.status);
            if (!alert(`${response.data.title} Has Been Updated`)) location.reload();
        }).catch((error) => {
            console.error(error);
            if (error.response.status === 404) {
                alert("ID Not Found");
            }
            else if (error.response.status === 500) {
                alert("Please Ensure You Have Not Created Any Repeated Authors")
            };
        });
};