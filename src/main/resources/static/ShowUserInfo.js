showUserInfo();

function showUserInfo() {
    fetch("http://localhost:8080/user/userList")
        .then(res => res.json())
        .then(user => {
            console.log('userSata', JSON.stringify(user))
            $('#headerUsername').append(user.username);
            $('#headerRole').append(user.role);
            let userList = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.password}</td>
                    <td>${user.role}</td>
                </tr>)`;
            $('#userTable').append(userList);
        })
}