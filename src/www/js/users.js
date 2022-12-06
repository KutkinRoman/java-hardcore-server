document.addEventListener("DOMContentLoaded", () => {
    sendRequestListUsers()
})

function sendRequest(method, url, body = null) {
    let init = {method: method}
    if (body) {
        init.body = JSON.stringify(body)
        init.headers = {'Content-Type': 'application/json'}
    }
    return fetch(url, init).then(response => {
        return response.json()
    })
}

function sendRequestListUsers() {
    sendRequest("GET", "/api/users")
        .then((data) => createPage(data))
        .catch((err) => console.error(err))
}

function sendRequestDeleteUser(id){
    const url = "delete/user?id=" + id
    sendRequest("DELETE", url)
        .then((data) => removeUserCard(data, id))
        .catch((err) => console.error(err))
}

function sendRequestPutUser(id){
    const value = document.getElementById("user_description_" + id).value
    if (!value){
        alert("Необходимо ввести данные...")
        return
    }
    const url = "put/user/description?id=" + id + "&description=" + value;
    sendRequest("PUT", url)
        .then((data) => alert(data.message))
        .catch((err) => console.error(err))
}

function createPage(data) {
    const root = document.getElementById('root')
    root.innerHTML += `<h3>Список пользователей</h3><hr>`
    if (data.length <= 0){
        root.innerHTML += `<i>Список пуст...</i>`
    } else {
        data.forEach(user => root.innerHTML += createUserCard(user))
    }
}

function createUserCard(user) {
    let description = "";
    if (user.description){
        description = user.description
    }
    return `<div id="user_card_${user.id}">
               <div>${user.lastName}</div>
               <div>${user.firstName}</div>
               <div>${user.email}</div>
               <div>${user.age}</div>
                    <div>
                        <input id="user_description_${user.id}" value="${description}" type="text">
                        <button onclick="sendRequestPutUser(${user.id})">Сохранить описание</button>
                    </div>
               <button onclick="sendRequestDeleteUser(${user.id})">Удалить пользовтаеля</button>
            </div>
            <hr>`
}

function removeUserCard(data, id){
    const html = `<div>${data.message}</div>`
    document.getElementById("user_card_" + id).innerHTML = html
}
