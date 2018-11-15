const btnAdicionarBus = document.getElementById('adicionarOnibus')
const btnEditarBus = document.getElementById('editarOnibus')

btnAdicionarBus.addEventListener('click', (e) => {
	e.preventDefault();
	let numeroPlaca = document.getElementById('addNumeroPlaca');
	let descricaoOnibus = document.getElementById('addDescricaoOnibus');

	let post = database.ref('Bus')
		post.push({ placa: numeroPlaca.value, descricao: descricaoOnibus.value })
		.then(res => {
			document.getElementById('mensagemParaUsuario').className = 'alert alert-success'
			document.getElementById('mensagemParaUsuario').roleName = 'alert'
			document.getElementById('mensagemParaUsuario').innerHTML = 'Ônibus inserido com sucesso!'
			numeroPlaca.value = ''
			descricaoOnibus.value = ''
		}, err => {
			document.getElementById('mensagemParaUsuario').className = 'alert alert-danger'
			document.getElementById('mensagemParaUsuario').roleName = 'alert'
			document.getElementById('mensagemParaUsuario').innerHTML = 'Ônibus não inserido com sucesso!'
		})
});	

document.getElementById('buscarOnibus').addEventListener('click', (e) => {
	e.preventDefault();
	let numeroPlaca = document.getElementById('buscarNumeroPlaca');
	
	let starCountRef = firebase.database().ref('Bus');
	starCountRef.on('value', function(snapshot) {
		
		let teste = Object.entries(snapshot.val()).filter(valor => {
			return valor[1].placa.indexOf(numeroPlaca.value) >= 0
		})
		.reduce((reduced, [key, value]) => {
			reduced[key] = value;
			return reduced;
		  }, {})
		
		popularTabela(teste)
		numeroPlaca.value = ''
	});
});	

let starCountRef = firebase.database().ref('Bus');
starCountRef.on('value', function(snapshot) {
	popularTabela(snapshot.val())
});

function popularTabela(valores){
	document.getElementById('tabelaOnibus').innerHTML = ''
	Object.entries(valores).map(valor => {
		let tr = document.createElement('tr')
		let td = [
			document.createElement('td'),
			document.createElement('td'),
			document.createElement('td'),
			document.createElement('td'),					
		]
		
		let tdPlaca = document.createTextNode(valor[1].placa)
		let tdDescricao = document.createTextNode(valor[1].descricao)
		
		td[0].appendChild(tdPlaca)
		td[1].appendChild(tdDescricao)
		
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
		
		td[2].appendChild(botaoRemover)
		td[3].appendChild(botaoEditar)
		
		tr.appendChild(td[0])
		tr.appendChild(td[1])
		tr.appendChild(td[2])
		tr.appendChild(td[3])
		
		
		document.getElementById('tabelaOnibus').appendChild(tr)
	})	
}

function remover(id){
	database.ref('Bus').child(id).set({})
	.then(res => {
		document.getElementById('mensagemParaUsuario').className = 'alert alert-success'
		document.getElementById('mensagemParaUsuario').roleName = 'alert'
		document.getElementById('mensagemParaUsuario').innerHTML = 'Ônibus removido com sucesso!'
	})
}

let idParaEditar = '';
function editar(id){
	idParaEditar = id;
	firebase.database().ref('Bus').child(id).once('value', (res) => {
		btnAdicionarBus.style.display = 'none'
		btnEditarBus.style.display = 'block'
		
		let numeroPlaca = document.getElementById('addNumeroPlaca');
		let descricaoOnibus = document.getElementById('addDescricaoOnibus');
		
		numeroPlaca.value = res.val().placa;
		descricaoOnibus.value = res.val().descricao;		
	})
}

btnEditarBus.addEventListener('click', (e) => {
	e.preventDefault();
	let numeroPlaca = document.getElementById('addNumeroPlaca');
	let descricaoOnibus = document.getElementById('addDescricaoOnibus');
		
	firebase.database().ref('Bus').child(idParaEditar).set({
		placa: numeroPlaca.value,
		descricao: descricaoOnibus.value
	})
	.then(res => {
		document.getElementById('mensagemParaUsuario').className = 'alert alert-success'
		document.getElementById('mensagemParaUsuario').roleName = 'alert'
		document.getElementById('mensagemParaUsuario').innerHTML = 'Ônibus editado com sucesso!'
		numeroPlaca.value = ''
		descricaoOnibus.value = ''
		btnAdicionarBus.style.display = 'block'
		btnEditarBus.style.display = 'none'
		}, err => {
			document.getElementById('mensagemParaUsuario').className = 'alert alert-danger'
			document.getElementById('mensagemParaUsuario').roleName = 'alert'
			document.getElementById('mensagemParaUsuario').innerHTML = 'Ônibus não inserido com sucesso!'
		})
})
