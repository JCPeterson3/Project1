document.querySelector('#login').addEventListener('click', login);

function login() {
    let un = document.querySelector('#username').value;
    let pw = document.querySelector('#password').value;

    let data = {
        username: un,
        password: pw
    };

    fetch('http://localhost:7000/login', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((response) => {
        if (response.status === 200) {
           // window.location.href = '/landing.html';
        } else if (response.status === 401) {
            // displayInvalidLogin();
        }

        return response.json();
    }).then((data) => {
        console.log(data);
        let userRole = data.userRole;

        if (userRole == "EMP") {
            window.location.href = '/employee.html';
        }

        if (userRole == "FM") {
            window.location.href = '/manager.html';
        }

        //alert(data.message);
    })

    function displayInvalidLogin() {
        let bodyElement = document.querySelector('body');

        let pElement = document.createElement('p');
        pElement.style.color = 'red';
        pElement.innerHTML = 'Invalid login!';

        bodyElement.appendChild(pElement);
    }
}