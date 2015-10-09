/**
 * Submete o formul·rio.
 */
if (parent.document.getElementById('GB_frame')) parent.document.getElementById('GB_frame').style.display='';
if (parent.document.getElementById('loading')) parent.document.getElementById('loading').style.display='none';

function submit(form, action, target){		
	if (parent.document.getElementById('GB_frame')) parent.document.getElementById('GB_frame').style.display='none';
	if (parent.document.getElementById('loading')) parent.document.getElementById('loading').style.display='';

	if( target != null ) {
		form.target = target;
	}else {
		form.target = "_self";
	}
	form.action = action;
	form.submit();		
}

/**
 * Apresenta a janela padronizada para visualizaÁ„o de relatÛrio
 * Este mÈtodo submete o formul·rio.
 */
function showReport(form,nameWindow,acaoReport,menubar){
	//chama a funcao para abrir uma nova janela
	if( menubar == null ) {menubar = "no";}
	resizable = "yes";	
	showWindow('',nameWindow,760,500,resizable,menubar);
	
	//submete o formulario
	form.target = nameWindow;
	form.action = acaoReport;
	form.submit();
}

/**
 * Apresenta uma nova janela e submete o formul·rio
 */
function showWindowSubmit(form,nameWindow,acao,width,height,resizable,menubar){
	if( width == null ) {width = 780;}
	if( height == null ) {height = 580;}
	if( resizable == null ) {resizable = "yes";}	
	if( menubar == null ) {menubar = "yes";}
	
	//chama a funcao para abrir uma nova janela
	showWindow('',nameWindow,width,height,resizable,menubar);
	
	//submete o formulario
	form.target = nameWindow;
	form.action = acao;
	form.submit();	
}
	
/**
 * Abre uma nova janela.
 * Se os n„o forem passados os par‚metros width e height a
 * janela ser· aberta com o tamanho de 780 X 580
 * Se o par‚metro resizable n„o for passado, a janela ser· redimension·vel.
 */
function showWindow(url,nameWindow,width,height,resizable,menubar)
{
	if(width == null ) {
		width = 780;
	}
	if( height == null ) {
		height = 580;			
	}
	if( resizable == null ) {
		resizable = "yes";
	}	
	if( menubar == null ) {
		menubar = "no";
	}
		
	window.open(url,nameWindow,'width=' +width+ ',height=' +height+ ',top=50,left=50,resizable=' +resizable+ ',menubar=' +menubar+ ',scrollbars=yes');
}


/** Retorna Codigo Char da Tecla */
function getTecla(e){return (e.which) ? e.which : e.keyCode;}
/** Retorna o Valor da Tecla */
function getCharTecla(e){return String.fromCharCode(getTecla(e));}

function somenteLetra(e){
	var tecla = getTecla(e);
	if(tecla == 32){ // tecla de espaÁo
		return true;
	}else if(tecla >= 65 && tecla <= 90){ // LETRAS MAIUSCULAS
		return true;
	}else{
		if(tecla >= 97 && tecla <= 122){ // LETRAS MINUSCULAS
			return true;
		}
		else{
			if (tecla != 8){ // backspace
				return false;
			}
			else{
				return true;
			}
		}
	}
}

function validaData(str) { 
	dia = (str.value.substring(0,2)); 
    mes = (str.value.substring(3,5)); 
	ano = (str.value.substring(6,10)); 

	cons = true; 
	
	// verifica se foram digitados n˙meros
	if (isNaN(dia) || isNaN(mes) || isNaN(ano)){
		alert("Preencha a data somente com n˙meros."); 
		str.value = "";
		str.focus(); 
		return false;
	}
		
    // verifica o dia valido para cada mes 
    if ((dia < 1)||(dia < 1 || dia > 30) && 
		(mes == 4 || mes == 6 || 
		 mes == 9 || mes == 11 ) || 
		 dia > 31) { 
    	cons = false; 
	} 

	// verifica se o mes e valido 
	if (mes < 1 || mes > 12 ) { 
		cons = false; 
	} 

	// verifica se e ano bissexto 
	if (mes == 2 && ( dia < 1 || dia > 29 || 
	   ( dia > 28 && 
	   (parseInt(ano / 4) != ano / 4)))) { 
		cons = false; 
	} 
    
	if (cons == false) { 
		alert("A data inserida n„o È v·lida: " + str.value); 
		str.value = "";
		str.focus(); 
		return false;
	} 
}

 function validaDat(campo,valor) {
	var date=valor;
	var ardt=new Array;
	var ExpReg=new RegExp("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}");
	ardt=date.split("/");
	erro=false;
	if ( date.search(ExpReg)==-1){
		erro = true;
		}
	else if (((ardt[1]==4)||(ardt[1]==6)||(ardt[1]==9)||(ardt[1]==11))&&(ardt[0]>30))
		erro = true;
	else if ( ardt[1]==2) {
		if ((ardt[0]>28)&&((ardt[2]%4)!=0))
			erro = true;
		if ((ardt[0]>29)&&((ardt[2]%4)==0))
			erro = true;
	}
	if (erro) {
		alert("\"" + valor + "\" n„o È uma data v·lida!!!");
		campo.focus();
		campo.value = "";
		return false;
	}
	return true;
}

// colocar no evento onKeyUp passando o objeto como parametro
function formataData(val)
{
   	var pass = val.value;
	var expr = /[0123456789]/;
		
	for(i=0; i<pass.length; i++){
		// charAt -> retorna o caractere posicionado no Ìndice especificado
		var lchar = val.value.charAt(i);
		var nchar = val.value.charAt(i+1);
	
		if(i==0){
		   // search -> retorna um valor inteiro, indicando a posiÁ„o do inicio da primeira
		   // ocorrÍncia de expReg dentro de instStr. Se nenhuma ocorrencia for encontrada o mÈtodo retornara -1
		   // instStr.search(expReg);
		   if ((lchar.search(expr) != 0) || (lchar>3)){
			  val.value = "";
		   }
		}else if(i==1){			   
			   if(lchar.search(expr) != 0){
				  // substring(indice1,indice2)
				  // indice1, indice2 -> ser· usado para delimitar a string
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

function formataHora_bkp(edit){
	var tecla = getTecla(edit);/*pega qual tecla foi pressionada*/
	alert(tecla);
    if (tecla == 8) { alert('oi'); return true; }/*retorna verdadeiro se foi delete, backspace...*/
    if (tecla == 0) {
    	alert(edit.value);
    	if(edit.value.length==2) {
    		alert(edit.value);
    		edit.value = edit.value.substring(0,1);
    		return true;
    	}
    }
    if (tecla > 31 && (tecla < 48 || tecla > 57)){
        return false;
    }
    if(edit.value.length==2){
      edit.value+=":";
    }
}

function formataHora(val) {
	var pass = val.value;
	var expr = /[0123456789]/;
		
	for(i=0; i<pass.length; i++){
		// charAt -> retorna o caractere posicionado no Ìndice especificado
		var lchar = val.value.charAt(i);
		var nchar = val.value.charAt(i+1);
	
		if(i==0){
		   // search -> retorna um valor inteiro, indicando a posiÁ„o do inicio da primeira
		   // ocorrÍncia de expReg dentro de instStr. Se nenhuma ocorrencia for encontrada o mÈtodo retornara -1
		   // instStr.search(expReg);
		   if ((lchar.search(expr) != 0) || (lchar>3)){
			  val.value = "";
		   }
		}else if(i==1){			   
			   if(lchar.search(expr) != 0){
				  // substring(indice1,indice2)
				  // indice1, indice2 -> ser· usado para delimitar a string
				  var tst1 = val.value.substring(0,(i));
				  val.value = tst1;				
 				  continue;			
			   }
			   
			   if ((nchar != ':') && (nchar != '')){
				 	var tst1 = val.value.substring(0, (i)+1);			
					if(nchar.search(expr) != 0) 
						var tst2 = val.value.substring(i+2, pass.length);
					else
						var tst2 = val.value.substring(i+1, pass.length);
	
					val.value = tst1 + ':' + tst2;
			   }
		 }else if(i==4){			
				if(lchar.search(expr) != 0){
					var tst1 = val.value.substring(0, (i));
					val.value = tst1;
					continue;			
				}
		
				if	((nchar != ':') && (nchar != '')){
					var tst1 = val.value.substring(0, (i)+1);
					if(nchar.search(expr) != 0) 
						var tst2 = val.value.substring(i+2, pass.length);
					else
						var tst2 = val.value.substring(i+1, pass.length);
	
					val.value = tst1 + ':' + tst2;
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

//colocar no evento onKeyUp passando o objeto como parametro
function formataDataHora(val)
{
   	var pass = val.value;
	var expr = /[0123456789]/;
		
	for(i=0; i<pass.length; i++){
		// charAt -> retorna o caractere posicionado no Ìndice especificado
		var lchar = val.value.charAt(i);
		var nchar = val.value.charAt(i+1);
	
		if(i==0){
		   // search -> retorna um valor inteiro, indicando a posiÁ„o do inicio da primeira
		   // ocorrÍncia de expReg dentro de instStr. Se nenhuma ocorrencia for encontrada o mÈtodo retornara -1
		   // instStr.search(expReg);
		   if ((lchar.search(expr) != 0) || (lchar>3)){
			  val.value = "";
		   }
		}else if(i==1){			   
			   if(lchar.search(expr) != 0){
				  // substring(indice1,indice2)
				  // indice1, indice2 -> ser· usado para delimitar a string
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
   		  }else if(i==9){
	   			if(lchar.search(expr) != 0){
					var tst1 = val.value.substring(0, (i));
					val.value = tst1;
					continue;			
				}
	   			
	   			if	((nchar != ' ') && (nchar != '')){
					var tst1 = val.value.substring(0, (i)+1);
					if(nchar.search(expr) != 0) 
						var tst2 = val.value.substring(i+2, pass.length);
					else
						var tst2 = val.value.substring(i+1, pass.length);
	
					val.value = tst1 + ' ' + tst2;
				}
   		  } else if(i==12){			   
			   if(lchar.search(expr) != 0){
					  // substring(indice1,indice2)
					  // indice1, indice2 -> ser· usado para delimitar a string
					  var tst1 = val.value.substring(0,(i));
					  val.value = tst1;				
	 				  continue;			
				   }
				   
				   if ((nchar != ':') && (nchar != '')){
					 	var tst1 = val.value.substring(0, (i)+1);			
						if(nchar.search(expr) != 0) 
							var tst2 = val.value.substring(i+2, pass.length);
						else
							var tst2 = val.value.substring(i+1, pass.length);
		
						val.value = tst1 + ':' + tst2;
				   }
			 }else if(i==15){			
					if(lchar.search(expr) != 0){
						var tst1 = val.value.substring(0, (i));
						val.value = tst1;
						continue;
					}
			
					if	((nchar != ':') && (nchar != '')){
						var tst1 = val.value.substring(0, (i)+1);
						if(nchar.search(expr) != 0) 
							var tst2 = val.value.substring(i+2, pass.length);
						else
							var tst2 = val.value.substring(i+1, pass.length);
		
						val.value = tst1 + ':' + tst2;
					}
	   		  }	
		  if(i>=16){
			  if(lchar.search(expr) != 0) {
					var tst1 = val.value.substring(0, (i));
					val.value = tst1;			
			  }
		  }
	 }
     if(pass.length>16)
		val.value = val.value.substring(0, 16);
	 	return true;
}

function somenteNumero(e){
    var charCode = getTecla(e);
    if (charCode > 31 && (charCode < 48 || charCode > 57)){
          return false;
    }    
    return true;
}

function converteUpper(campo) {
    campo.value = campo.value.toUpperCase();
}

function limpaString(campo) {
	var acLetraA = new String("¬¡¿√ƒ");
	var acLetraE = new String(" …»À");
	var acLetraI = new String("ÕÃŒœ");
	var acLetraO = new String("”“’‘÷");
	var acLetraU = new String("⁄Ÿ€‹");
	var letras = new RegExp("[A-Z]");
	var valor = '';
	
	if(campo != null) {
		campo.value = campo.value.toUpperCase();
		for (var i = 0; i < campo.value.length; i++) {
			var letra = new String(campo.value.charAt(i));
			
			if (letra == " " || letras.test(letra)) {
				valor = valor + letra; 
			}
			else if (acLetraA.indexOf(letra) != -1) {
				valor = valor + "A"; //substitui p/ a letra A		
			}	
			else if (acLetraE.indexOf(letra) != -1) {
				valor = valor + "E"; //substitui p/ a letra E		
			}	
			else if (acLetraI.indexOf(letra) != -1) {
				valor = valor + "I"; //substitui p/ a letra I		
			}	
			else if (acLetraO.indexOf(letra) != -1) {
				valor = valor + "O"; //substitui p/ a letra O	
			}	
			else if (acLetraU.indexOf(letra) != -1) {
				valor = valor + "U"; //substitui p/ a letra U
			}	
			else if ("«" == letra) {
				valor = valor + "C"; 
			}	
			else if ("—" == letra) {
				valor = valor + "N"; 
			}	
		}
		campo.value = valor.toUpperCase();
	}
}

function somenteLetraENumero(e)
{
	var tecla = getTecla(e);
	if(tecla == 32){ // tecla de espaÁo
		return true;
	}else if(tecla >= 48 && tecla <= 57){ // Numeros de 0 a 9
		return true;
	}else if(tecla >= 65 && tecla <= 90){ // LETRAS MAIUSCULAS
		return true;
	}else{
		if(tecla >= 97 && tecla <= 122){ // LETRAS MINUSCULAS
			return true;
		}
		else{
			if (tecla != 8){ // backspace
				return false;
			}
			else{
				return true;
			}
		}
	}
}


/**
 * Formata um valor float para apresentar um determinado n˙mero de casas decimais.
 * 
 * valor  = Valor numerico a ser formatado.
 * casas  = N˙mero de casas decimais a ser considerada na formataÁ„o.
 * tamNum = Tamanho a ser considerado na formataÁ„o para o valor decimal antes da virgula. 
 */	
function formatValueFloat(valor,casas,tamNum){
	if( casas == null ) {
		casas = 2; //default
	}

	var valor_str = valor.toString();
	if( valor_str == "" )
		return valor_str;
		
	//se digitou virgula como separador, substitui por ponto
	var indexVirgula = valor_str.indexOf('.');
	if( indexVirgula != -1 ) {
		valor_str = valor_str.replace('.',',');
	}

	var indexSeparator = valor_str.indexOf(',');
	if( indexSeparator != -1) {
		var valorp1 = valor_str.substring(0,indexSeparator);
		var valorp2 = valor_str.substring(indexSeparator+1);
		if( tamNum != null ){
		  valorp1 = valorp1.substring(0,tamNum);
		}
		
		var casasZero = "";
		if( valorp2.length < casas ) {
			var numeroCasas = casas;
			while( numeroCasas > valorp2.length ) {
				casasZero += "0";
				numeroCasas--;
			}
			valorp2 = valorp2 + casasZero;		
		} else {
			valorp2 = valorp2.substring(0,casas);
		}
		return valorp1 + "," + valorp2;
	} else {
		var valorp2 = "00";
		while( valorp2.length < casas )	{
			valorp2 += "0";
		} 
		if( tamNum == null ){
			return valor + "," + valorp2;
		}else{
			return valor.substring(0,tamNum) + "," + valorp2 ;
		}
	}
}

/**
 * Preenche o campo com o valor formatado.
 * 
 * valor  = Valor numerico a ser formatado.
 * casas  = N˙mero de casas decimais a ser considerada na formataÁ„o.
 * tamNum = Tamanho a ser considerado na formataÁ„o para o valor decimal antes da virgula. 
 */
function formatValueFloatField(campo,casas,tamNum)
{
	if( casas == null ) {
		casas = 2;
	}
	campo.value = formatValueFloat(campo.value,casas,tamNum);
}	

/**
 * Limita a digitaÁ„o de TextArea
 */
function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
        alert('Este campo pode conter no m·ximo ' + limitNum + ' caracteres.');
    }
}

function isStrNum(dado) {
	return !isNaN(dado);
}

/**
 * Retorna True se a String dada for uma data v·lida
 *
 * @param string dado Data no formato DD/MM/AAAA
 * @return boolean true se Data Ok
 */
function isStrData(dado){
  
  var diasMes = new Array(31,29,31,30,31,30,31,31,30,31,30,31);
  var dia=0, mes=0, ano=0;
  var result = false;

  // PrÈ-analisa o String:
  if (dado != ""){
    if ((dado.length == 10) && (dado.substr(2,1) == "/") && (dado.substr(5,1) == "/")){
      // Levanta Campos:
      if (isStrNum(dado.substr(0,2),0)) dia = dado.substr(0,2);
      if (isStrNum(dado.substr(3,2),0)) mes = dado.substr(3,2);
      if (isStrNum(dado.substr(6,4),0)) ano = dado.substr(6,4);

      // Analisa Ano e MÍs:
      if ((ano > 0) && (mes >= 1) && (mes <= 12)){
        // Analisa Dia:
        if ((dia >= 1) && (dia <= diasMes[mes - 1])){
          // Analisa os casos n„o-bissextos:
          if ((mes == 2) && ((ano%4 != 0) || (ano%100 == 0) && (ano%400 != 0))){
            if (dia <= 28) result = true;
          } else {
            result = true;
          }
        }
      }
    }
  }       
  return result;
}   

/**
 * Retorna True se a String dada for uma data v·lida
 *
 * @param string dado Hora nos formatos HH:MM ou HH:MM:SS
 * @return boolean true se Hora Ok
 */
function isStrHora(dado){
  
  var hor, min, seg;
  var result = false;

  // PrÈ-analisa o String:
  if (dado != ""){
    if (((dado.length == 5) || (dado.length == 8)) && (dado.substr(2,1) == ":")){
      // Levanta Campos:
      if (isStrNum(dado.substr(0,2),0)) hor = dado.substr(0,2); else hor = -1;
      if (isStrNum(dado.substr(3,2),0)) min = dado.substr(3,2); else min = -1;

      // Analisa a Hora:
      if ((hor >= 0) && (hor <= 23)){
        // Analisa o Minuto:
        if ((min >= 0) && (min <= 59)){
          // Verifica se tem segundo:
          if (dado.length == 8){
            // PrÈ-analisa:
            if (dado.substr(5,1) == ":"){
              // Levanta e verifica segundos:
              if (isStrNum(dado.substr(6,2),0)) seg = dado.substr(6,2); else seg = -1;
              if ((seg >= 0) && (seg <= 59)){
                result = true;
              }
            }
          } else {
            result = true;
          }       
        }
      }
    }
  }
  return result;
}

/**
 * Retorna True se a String dada for uma DataHora
 * nos formatos "DD/MM/AAAA" ou "DD/MM/AAAA HH:MM"
 * ou "DD/MM/AAAA HH:MM:SS"
 * @param string dado Data/Hora no formato DD/MM/AAAA,
 *                    DD/MM/AAAA HH:MM ou DD/MM/AAAA HH:MM:SS
 * @return boolean true se Data/Hora Ok
 */
function isStrDataHora(dado){
  
  var result = false;

  // PrÈ-Analisa o String:
  if (dado != ""){
    // Analisa a Data:
    if (dado.length >= 10){
      if (isStrData(dado.substr(0,10))){
        // Analisa a Hora, se disponÌvel:
        if (dado.length > 10){
          if ((dado.substr(10,1) == " ") && isStrHora(dado.substr(11))){
            result = true;
          }
        } else {
          result = true;
        }
      }
    }
  }
  return result;
}

function isDt1GreaterDt2 (dt1, dt2) {
	var dt1Date = new Date(dt1.substr(6,4), dt1.substr(3,2)-1, dt1.substr(0,2), dt1.substr(11,2), dt1.substr(14,2), 0, 0);
	var dt2Date = new Date(dt2.substr(6,4), dt2.substr(3,2)-1, dt2.substr(0,2), dt2.substr(11,2), dt2.substr(14,2), 0, 0);
	if (dt1Date > dt2Date)
		return true;
	return false;
}
