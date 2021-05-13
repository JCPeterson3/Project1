document.querySelector('#logout').addEventListener('click', logout);
// document.querySelector('#getTickets').addEventListener('click', renderTickets);

window.onload = function() {
    redirectUser()
    renderCurrentUser();
    renderTickets();
}

function redirectUser() {
    fetch('http://localhost:7000/current_user', {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 400) {
            window.location.href = '/';
        }

        return response.json();
    }).then((data) => {
        let id = data.id;
        let username = data.username;
        let firstName = data.firstName;
        let lastName = data.lastName;
        let userEmail = data.email;

        let userInfoElement = document.querySelector('#user');
        userInfoElement.innerHTML = `Successfully logged in as: ${username}`;
    })
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
        let id = data.id;
        let username = data.username;
        let firstName = data.firstName;
        let lastName = data.lastName;
        let userEmail = data.email;

        let userInfoElement = document.querySelector('#user');
        userInfoElement.innerHTML = `Successfully logged in as: ${username}`;
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
        // let len = data.length;
        // let item = data.shift();
        // let id = item.id;
        // let newLength = data.length;

        // let ticketsElement = document.querySelector('#tickets');
        // ticketsElement.innerHTML = `${len}<br>Item ${id} has been retrieved<br>Now the length is: ${newLength}`;

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
        submitted.innerHTML = reimb.submitted;
        let resolved = row.insertCell(2);
        resolved.innerHTML = reimb.resolved;
        let description = row.insertCell(3);
        description.innerHTML = reimb.description;
        let status = row.insertCell(4);
        status.innerHTML = reimb.status;
        let type = row.insertCell(5);
        type.innerHTML = reimb.type;
        let resolver = row.insertCell(6);
        resolver.innerHTML = reimb.resolver;
        let receipt = row.insertCell(7);
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