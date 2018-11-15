const database = firebase.database()

function verificarSeUsuarioLogado() {
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // User is signed in.
            console.log(user.email + " logado.");
        } else {
            // No user is signed in.
            console.log("não deveria estar aqui.");
            window.location.href = "login.html";
        }
    });
}

window.onload = function() {
    verificarSeUsuarioLogado();
}

document.getElementById('logout').addEventListener('click', (e) => {
	e.preventDefault();
	firebase.auth().signOut().then(function() {
        // Sign-out successful.
        window.location.href = "login.html"
    }).catch(function(error) {
        // An error happened.
    });
});
