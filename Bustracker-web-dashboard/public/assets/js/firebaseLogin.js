/**
 * Handles the sign in button press.
 */
function toggleSignIn() {
	if (firebase.auth().currentUser) {
		// [START signout]
		firebase.auth().signOut();
		// [END signout]
	} else {
		var email = document.getElementById('email').value;
		var password = document.getElementById('password').value;
		if (email.length < 4) {
			alert('Please enter an email address.');
			return;
		}
		if (password.length < 4) {
			alert('Please enter a password.');
			return;
		}
		// Sign in with email and pass.
		// [START authwithemail]
		firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
			// Handle Errors here.
			var errorCode = error.code;
			var errorMessage = error.message;
			// [START_EXCLUDE]
			if (errorCode === 'auth/wrong-password') {
				alert('Wrong password.');
			} else {
				alert(errorMessage);
			}
			console.log(error);
			//document.getElementById('logar').disabled = false;
			// [END_EXCLUDE]
		});
		// [END authwithemail]
	}
	//console.log("logou");
	document.getElementById('logar').disabled = true;
}

/**
 * initApp handles setting up UI event listeners and registering Firebase auth listeners:
 *  - firebase.auth().onAuthStateChanged: This listener is called when the user is signed in or
 *    out, and that is where we update the UI.
 */
function initApp() {
	// Listening for auth state changes.
	// [START authstatelistener]
	firebase.auth().onAuthStateChanged(function(user) {
		// [START_EXCLUDE silent]
		//document.getElementById('quickstart-verify-email').disabled = true;
		// [END_EXCLUDE]
		if(user) {
			// User is signed in.
			//console.log(user.email + " logado.");
			//window.location.href = "dashboard.html";
	  
			var displayName = user.displayName;
			var emailVerified = user.emailVerified;
			var photoURL = user.photoURL;
			var isAnonymous = user.isAnonymous;
			var uid = user.uid;
			var providerData = user.providerData;

			console.log(user.email + " logado.")
			window.location.href = "dashboard.html";

			//[START_EXCLUDE]
			//document.getElementById('quickstart-sign-in-status').textContent = 'Signed in';
			//document.getElementById('quickstart-sign-in').textContent = 'Sign out';
			//document.getElementById('quickstart-account-details').textContent = JSON.stringify(user, null, '  ');
			//if (!emailVerified) {
				//document.getElementById('quickstart-verify-email').disabled = false;
			//}
			// [END_EXCLUDE]
			//} else {
				// User is signed out.
				//[START_EXCLUDE]
				//document.getElementById('quickstart-sign-in-status').textContent = 'Signed out';
				//document.getElementById('quickstart-sign-in').textContent = 'Sign in';
				//document.getElementById('quickstart-account-details').textContent = 'null';
				//[END_EXCLUDE]
			//}
		}
		// [START_EXCLUDE silent]
		//document.getElementById('logar').disabled = false;
		// [END_EXCLUDE]
	});
	// [END authstatelistener]
	document.getElementById('logar').addEventListener('click', toggleSignIn, false);
	//window.location.href = "login.html";
	//document.getElementById('quickstart-sign-up').addEventListener('click', handleSignUp, false);
	//document.getElementById('quickstart-verify-email').addEventListener('click', sendEmailVerification, false);
	//document.getElementById('quickstart-password-reset').addEventListener('click', sendPasswordReset, false);
}

window.onload = function() {
	initApp();
};
