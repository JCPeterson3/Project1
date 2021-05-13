document.querySelector('#logout').addEventListener('click', logout);
document.querySelector('#add').addEventListener('click', submitTicket);

window.onload = function() {
    renderCurrentUser();
    renderTickets();
}

function renderCurrentUser() {
    fetch('http://localhost:7000/current_user', {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 400) {
            window.location.href = '/';
        }

        return response.json();
    }).then((data) => {
        let username = data.username;

        let userInfoElement = document.querySelector('#user');
        userInfoElement.innerHTML = `${username}`;
    })
}

function renderTickets() {
    fetch('http://localhost:7000/user_tickets', {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 400) {
            window.location.href = '/';
        }

        return response.json();
    }).then((data) => {
        populateTable(data);
    })
}

function populateTable(rows){
    const table = document.getElementById("reimbursements");

    rows.forEach( reimb => {
        let row = table.insertRow();
        let amount = row.insertCell(0);
        amount.innerHTML = reimb.amount;
        let submitted = row.insertCell(1);
        let subTime = new Date(reimb.submitted);
        submitted.innerHTML = subTime.toLocaleString();
        let resolved = row.insertCell(2);
        if (!!reimb.resolved) {
            let resTime = new Date(reimb.resolved);
            resolved.innerHTML = resTime.toLocaleString();
        }
        let description = row.insertCell(3);
        description.innerHTML = reimb.description;
        let status = row.insertCell(4);
        status.innerHTML = reimb.status;
        let type = row.insertCell(5);
        type.innerHTML = reimb.type;
        let receipt = row.insertCell(6);
        receipt.innerHTML = reimb.receipt;
    });
}

function logout() {
    fetch('http://localhost:7000/logout', {
        method: 'POST',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 200) {
            window.location.href = '/';
        }
    })
}

function submitTicket() {
    let tAmount = document.querySelector('#amount').value;
    let tDescription = document.querySelector('#description').value;
    let tType = document.querySelector('#type').value;
    let newTicket = false;

    let reimbursement = {
        id: 0,
        amount: tAmount,
        submitted: null,
        resolved: null,
        description: tDescription,
        receipt: null,
        author: null,
        resolver: null,
        status: null,
        type: tType
    }

    fetch('http://localhost:7000/add_ticket', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reimbursement)
    }).then((response) => {
        if (response.status === 200) {
            newTicket = true;
        }

        return response.json();
    }).then((data) => {   //doing a then(data) here and then we will have it populate a row
        if (newTicket){
            let list = [];
            list.push(data);
            populateTable(list);
        }

        newTicket = false;
        tAmount.innerHTML = "";
        tDescription.innerHTML = "";
        const done = document.getElementById("leave");
        done.click();
    })
}