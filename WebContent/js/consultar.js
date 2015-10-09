function formataData(val){
   	var pass = val.value;
	var expr = /[0123456789]/;
		
	for(i=0; i<pass.length; i++){
		// charAt -> retorna o caractere posicionado no ?ndice especificado
		var lchar = val.value.charAt(i);
		var nchar = val.value.charAt(i+1);
	
		if(i==0){
		   // search -> retorna um valor inteiro, indicando a posi??o do inicio da primeira
		   // ocorr?ncia de expReg dentro de instStr. Se nenhuma ocorrencia for encontrada o m?todo retornara -1
		   // instStr.search(expReg);
		   if ((lchar.search(expr) != 0) || (lchar>3)){
			  val.value = "";
		   }
		}else if(i==1){	
			if(lchar.search(expr) != 0){
				  // substring(indice1,indice2)
				  // indice1, indice2 -> ser? usado para delimitar a string
				  var tst1 = val.value.substring(0,(i));
				  val.value = tst1;				
 				  continue;			
			   }
			   
			   if ((nchar != '/') && (nchar != '')){
				 	var tst1 = val.value.substring(0, (i)+1);			
					if(nchar.search(expr) != 0) 
						var tst2 = val.value.substring(i+2, pass.length);
					else
						var tst2 = val.value.substring(i+1, pass.length);
	
					val.value = tst1 + '/' + tst2;
			   }
		 }else if(i==4){			
				if(lchar.search(expr) != 0){
					var tst1 = val.value.substring(0, (i));
					val.value = tst1;
					continue;			
				}
		
				if	((nchar != '/') && (nchar != '')){
					var tst1 = val.value.substring(0, (i)+1);
					if(nchar.search(expr) != 0) 
						var tst2 = val.value.substring(i+2, pass.length);
					else
						var tst2 = val.value.substring(i+1, pass.length);
	
					val.value = tst1 + '/' + tst2;
				}
   		  }	
		  if(i>=6){
			  if(lchar.search(expr) != 0) {
					var tst1 = val.value.substring(0, (i));
					val.value = tst1;			
			  }
		  }
		}
     if(pass.length>10)
		val.value = val.value.substring(0, 10);
   return true;
}
   
   
function alteraCiclo(nomeCicloSelecionado){
	document.getElementById("nomeCiclo").value = nomeCicloSelecionado;
}
   
function validaCampos(){
	var nomeciclo = document.getElementById("nomeCiclo").value;
	var nomeIdeia = document.getElementById("nomeIdeia").value;
	var dataInicio = document.getElementById("dataInicio").value; 		 		
	var dataFim = document.getElementById("dataFim").value;
	
	var isNomeCiclo = false;
	var isNomeIdeia = false;
	var isDataInicio = false;
	var isDataFim = false;
	
	if(nomeciclo != "" && nomeciclo != "-- Selecione --"){
		isNomeCiclo = true;
	}
	
	if(nomeIdeia != "") isNomeIdeia = true;
	if(dataInicio != "") isDataInicio = true;
	if(dataFim != "") isDataFim = true;
	
	if(!isNomeCiclo && !isNomeIdeia && !isDataInicio && !isDataFim){
		alert("Favor escolher algum critério de pesquisa")
		return false;
	}
	
	if((!isDataInicio && isDataFim) || (isDataInicio && !isDataFim) ){
		alert("Favor preencher os dois campos de data ou não preencher nenhum.")
		return false;
	}else if(isDataInicio && isDataFim){  		
		
		//VERIFICA SE As DATAs SÃO VALIDAS
		/*if(isDataInvalida(dataInicio)){
			alert("Data Início inválida");
			return false;
		}
		if(isDataInvalida(dataFim)){
			alert("Data Fim inválida");
			return false;
		}*/		
		
		//VERIFICA SE A DATA FINAL É MAIOR QUE A DATA INICIAL
		var dataInicial = parseInt(dataInicio.split("/")[2].toString() + dataInicio.split("/")[1].toString() + dataInicio.split("/")[0].toString());
		var dataFinal = parseInt(dataFim.split("/")[2].toString() + dataFim.split("/")[1].toString() + dataFim.split("/")[0].toString());
		if (dataInicial > dataFinal) {
		   alert("Data de Início não pode ser maior que a Data Fim");
		   return false;
		} 			
	}
	
	return true;
}

function isDataInvalida(data){
	splitData = data.split('/');	
	
	dia = parseInt(splitData[0],10);
    mes = parseInt(splitData[1],10)-1; //O JavaScript representa o mês de 0 a 11 (0->janeiro, 1->fevereiro... 11->dezembro)
    ano = parseInt(splitData[2],10);
    
    var novaData = new Date(ano, mes, dia);
    
    if ((novaData.getDate() != dia) || (novaData.getMonth() != mes) || (novaData.getFullYear() != ano)){
        return true;
    }
     return false;  
}

//VERIFICA SE As DATAs SÃO VALIDAS
function tamanhoData(obj){
	data = obj.value;
	if(data !=null && data != ""){
		if(data.length != 10){
			alert("O formato da data deve ser dd/mm/aaaa");
			obj.value = "";
			obj.focus();
		}else if(isDataInvalida(data)){
			alert("Data inválida");
			obj.value = "";
			obj.focus();
		}
	}		
}

function limparCampos(){
	document.getElementById("nomeCiclo").value = "";
	document.getElementById("nomeIdeia").value="";
	document.getElementById("dataInicio").value=""; 		 		
	document.getElementById("dataFim").value="";	
	document.getElementById("idCicloSelecionado").value = "-- Selecione --";
}