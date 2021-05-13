document.querySelector('#logout').addEventListener('click', logout);
document.querySelector('#status').addEventListener('change', renderTickets);

const btnApprove = 'class="btn btn-default btn-sm btn-success"><span class="glyphicon glyphicon-ok"></span></button>';
const btnDeny = 'class="btn btn-default btn-sm btn-danger"><span class="glyphicon glyphicon-remove"></span></button>';


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
    let url = 'http://localhost:7000/user_reimbursements'
    let statusElement = document.querySelector('#status');
    let statusValue = statusElement.value;
    
    if (statusValue !== "none"){
        let query = "?status=" + statusValue;
        url += query;
    }

    console.log(url);

    fetch(url, {
        method: 'GET',
        credentials: 'include'
    }).then((response) => {
        if (response.status === 400) {
            //alert(response.json().message);
        }

        return response.json();
    }).then((data) => {
        clearTable();
        populateTable(data);
    })
}

function populateTable(rows){
    const table = document.getElementById("reimbursements");
    let rowCount = 0;

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
        if (reimb.status !== "PENDING"){
            status.innerHTML = reimb.status;
        } else {
            // status.innerHTML = `<button type="button" onclick="approve(${reimb.id}, ${rowCount})">Approve</button><button type="button" onclick="deny(${reimb.id}, ${rowCount})">Deny</button>`;
            status.innerHTML = `<button type="button" onclick="approve(${reimb.id}, ${rowCount})" ${btnApprove}<button type="button" onclick="deny(${reimb.id}, ${rowCount})" ${btnDeny}`;
        }
        let type = row.insertCell(5);
        type.innerHTML = reimb.type;
        let author = row.insertCell(6);
        author.innerHTML = reimb.author;
        let resolver = row.insertCell(7);
        resolver.innerHTML = reimb.resolver;
        let receipt = row.insertCell(8);
        receipt.innerHTML = reimb.receipt;

        rowCount++;
    });
}

function clearTable() {
    let table = document.getElementById("reimbursements");
    table.innerHTML = '';
}

function update(element, row, myStatus) {

    let reimbursement = {
        id: element,
        amount: null,
        submitted: null,
        resolved: null,
        description: null,
        receipt: null,
        author: null,
        resolver: null,
        status: myStatus,
        type: null
    }

    fetch('http://localhost:7000/update_reimbursement', {
        method: 'PUT',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reimbursement)
    }).then((response) => {
        // Handle bad status codes here
    })

    document.getElementById("reimbursements").rows[row].cells[4].innerHTML = myStatus.toUpperCase();
    //document.getElementById("myTable").rows[2].cells[1].innerHTML = 'edited';
}

function approve(element, row) {
    update(element, row, "approved");
}

function deny(element, row) {
    update(element, row, "denied");
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