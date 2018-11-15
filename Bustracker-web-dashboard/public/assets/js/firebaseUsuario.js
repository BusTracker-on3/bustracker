document.getElementById('buscarAluno').addEventListener('click', (e) => {
	e.preventDefault();
    let numeroMatricula = document.getElementById('buscarNumeroMatricula');
    
	let starCountRef = firebase.database().ref('Users');
	starCountRef.on('value', function(snapshot) {
        
        let teste = Object.entries(snapshot.val()).filter(valor => {
			return (valor[1].registration.toString().indexOf(numeroMatricula.value)) >= 0
		})
		.reduce((reduced, [key, value]) => {
			reduced[key] = value;
			return reduced;
		  }, {})
		
		popularTabela(teste)
		numeroMatricula.value = ''
	});
});	

let starCountRef = firebase.database().ref('Users');
starCountRef.on('value', function(snapshot) {
	popularTabela(snapshot.val())
});

function popularTabela(valores){
	document.getElementById('tabelaAluno').innerHTML = ''
	Object.entries(valores).map(valor => {
		let tr = document.createElement('tr')
		let td = [
			document.createElement('td'),
			document.createElement('td'),
			document.createElement('td'),
            document.createElement('td'),
		]
		
        let tdMatricula = document.createTextNode(valor[1].registration)
        let tdNome = document.createTextNode(valor[1].name)
        let tdEmail = document.createTextNode(valor[1].email)
        
        td[0].appendChild(tdMatricula)
        td[1].appendChild(tdNome)
        td[2].appendChild(tdEmail)
		
		let botaoRemover = document.createElement('button')
		botaoRemover.appendChild(document.createTextNode('Remover'))
		botaoRemover.className = 'btn btn-info btn-fill btn-danger m-5'
		botaoRemover.onclick = () => {
			remover(valor[0])
			return false;
		}
		
        td[3].appendChild(botaoRemover)
        
		tr.appendChild(td[0])
		tr.appendChild(td[1])
		tr.appendChild(td[2])
        tr.appendChild(td[3])
		
		document.getElementById('tabelaAluno').appendChild(tr)
	})
}

function remover(id){
	database.ref('Users').child(id).set({})
	.then(res => {
		document.getElementById('mensagemParaUsuario').className = 'alert alert-success'
		document.getElementById('mensagemParaUsuario').roleName = 'alert'
		document.getElementById('mensagemParaUsuario').innerHTML = 'Usuário removido com sucesso!'
	})
}