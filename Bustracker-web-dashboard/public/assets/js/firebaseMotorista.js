function mascara(t, mask) {
	var i = t.value.length;
	var saida = mask.substring(1,0);
	var texto = mask.substring(i)
	if(texto.substring(0,1) != saida) {
		t.value += texto.substring(0,1);
	}
}

const btnAdicionarMotorista = document.getElementById('adicionarMotorista')
const btnEditarMotorista = document.getElementById('editarMotorista')

btnAdicionarMotorista.addEventListener('click', (e) => {
	e.preventDefault();
	let nomeMotorista = document.getElementById('addNomeMotorista');
	let numeroCnh = document.getElementById('addCnhMotorista');
	let telefoneMotorista = document.getElementById('addTelefoneMotorista');
	
	let post = database.ref('Drivers')
		post.push({ nome: nomeMotorista.value, cnh: numeroCnh.value, telefone: telefoneMotorista.value })
		.then(res => {
			document.getElementById('mensagemParaUsuario').className = 'alert alert-success'
			document.getElementById('mensagemParaUsuario').roleName = 'alert'
			document.getElementById('mensagemParaUsuario').innerHTML = 'Motorista inserido com sucesso!'
			nomeMotorista.value = ''
			numeroCnh.value = ''
			telefoneMotorista.value = ''
		}, err => {
			document.getElementById('mensagemParaUsuario').className = 'alert alert-danger'
			document.getElementById('mensagemParaUsuario').roleName = 'alert'
			document.getElementById('mensagemParaUsuario').innerHTML = 'Motorista não inserido com sucesso!'
		})
});	

document.getElementById('buscarMotorista').addEventListener('click', (e) => {
	e.preventDefault();
	let numeroCnh = document.getElementById('buscarCnhMotorista');
	
	let starCountRef = firebase.database().ref('Drivers');
	starCountRef.on('value', function(snapshot) {
		
		let teste = Object.entries(snapshot.val()).filter(valor => {
			return valor[1].cnh.indexOf(numeroCnh.value) >= 0
		})
		.reduce((reduced, [key, value]) => {
			reduced[key] = value;
			return reduced;
		  }, {})
		
		popularTabela(teste)
		numeroCnh.value = ''
	});
});	

let starCountRef = firebase.database().ref('Drivers');
starCountRef.on('value', function(snapshot) {
	popularTabela(snapshot.val())
});

function popularTabela(valores){
	document.getElementById('tabelaMotorista').innerHTML = ''
	Object.entries(valores).map(valor => {
		let tr = document.createElement('tr')
		let td = [
			document.createElement('td'),
			document.createElement('td'),
			document.createElement('td'),
			document.createElement('td'),
			document.createElement('td'),					
		]
		
		let tdNome = document.createTextNode(valor[1].nome)
		let tdCNH = document.createTextNode(valor[1].cnh)
		let tdTelefone = document.createTextNode(valor[1].telefone)
		
		td[0].appendChild(tdNome)
		td[1].appendChild(tdTelefone)
		td[2].appendChild(tdCNH)

		let botaoRemover = document.createElement('button')
		botaoRemover.appendChild(document.createTextNode('Remover'))
		botaoRemover.className = 'btn btn-info btn-fill btn-danger m-5'
		botaoRemover.onclick = () => {
			remover(valor[0])
			return false;
		}
		
		let botaoEditar = document.createElement('button')
		botaoEditar.appendChild(document.createTextNode('Editar'))
		botaoEditar.className = 'btn btn-info btn-fill'
		botaoEditar.onclick = () => {
			editar(valor[0])
			return false;
		}
		
		td[3].appendChild(botaoRemover)
		td[4].appendChild(botaoEditar)
		
		tr.appendChild(td[0])
		tr.appendChild(td[1])
		tr.appendChild(td[2])
		tr.appendChild(td[3])
		tr.appendChild(td[4])
		
		document.getElementById('tabelaMotorista').appendChild(tr)
	})
	
}

function remover(id){
	database.ref('Drivers').child(id).set({})
	.then(res => {
		document.getElementById('mensagemParaUsuario').className = 'alert alert-success'
		document.getElementById('mensagemParaUsuario').roleName = 'alert'
		document.getElementById('mensagemParaUsuario').innerHTML = 'Motorista removido com sucesso!'
	})
}

let idParaEditar = '';
function editar(id){
	idParaEditar = id;
	firebase.database().ref('Drivers').child(id).once('value', (res) => {
		btnAdicionarMotorista.style.display = 'none'
		btnEditarMotorista.style.display = 'block'

		let nomeMotorista = document.getElementById('addNomeMotorista');
		let numeroCnh = document.getElementById('addCnhMotorista');
		let telefoneMotorista = document.getElementById('addTelefoneMotorista');
		
		numeroCnh.value = res.val().cnh;
		nomeMotorista.value = res.val().nome;
		telefoneMotorista.value = res.val().telefone;
	})
}

btnEditarMotorista.addEventListener('click', (e) => {
	e.preventDefault();
	let nomeMotorista = document.getElementById('addNomeMotorista');
	let numeroCnh = document.getElementById('addCnhMotorista');
	let telefoneMotorista = document.getElementById('addTelefoneMotorista');
		
	firebase.database().ref('Drivers').child(idParaEditar).set({
		cnh: numeroCnh.value,
		nome: nomeMotorista.value,
		telefone: telefoneMotorista.value
	})
	.then(res => {
		document.getElementById('mensagemParaUsuario').className = 'alert alert-success'
		document.getElementById('mensagemParaUsuario').roleName = 'alert'
		document.getElementById('mensagemParaUsuario').innerHTML = 'Motorista editado com sucesso!'
		numeroCnh.value = ''
		nomeMotorista.value = ''
		telefoneMotorista.value = ''
		btnAdicionarMotorista.style.display = 'block'
		btnEditarMotorista.style.display = 'none'
		}, err => {
			document.getElementById('mensagemParaUsuario').className = 'alert alert-danger'
			document.getElementById('mensagemParaUsuario').roleName = 'alert'
			document.getElementById('mensagemParaUsuario').innerHTML = 'Motorista não inserido com sucesso!'
		})
})
