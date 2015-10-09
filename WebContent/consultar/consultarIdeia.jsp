<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>   
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<sx:head/>
<sj:head locale="de" jquerytheme="lightness"/>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/consultar.js"></script>
		
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Vitrine de Ideias</title>

<script language="JavaScript" type="text/javascript">
   
</script>


</head>
<body>


<div class="container">
<div class="menu">
	<a href="Enter.do?action=general"><img src="./images/unimed-logo.png" alt="UNIMED" class="logo"/></a>
</div>
	<div class="content">	
		<div class="birthday">		
			<form  method="post" action="pesquisarIdeias">  
				<div class="cabecalho">		
					<h2> Vitrine de ideias </h2>
					<div class="cabecalho"></div>							
				</div> 	
				<div class="birthday-form">
					<s:hidden name="nomeCiclo" id="nomeCiclo"/>
					<p>
						<label>Ciclo do Programa:</label>						
						<s:select  name="idCicloSelecionado" list="ciclos" listKey="nome" listValue="nome" onchange="alteraCiclo(this.value)" >
						</s:select>
					</p>
					
					<p>
						<label>Título da ideia:</label>
						<s:textfield name="nomeIdeia" id="nomeIdeia" />
					</p>														
					
					<div class="calendario">						
						<label>Data Início:</label>									
						<s:date id="dataInicioId" name="dataInicio" format="dd/MM/yyyy"/>
						<s:textfield id="dataInicio" name="dataInicio" 
							value="%{dataInicioId}" maxlength="10"  size="11"
							onkeyup="formataData(this);" onblur="tamanhoData(this)" title="dd/mm/aaaa" cssClass="sxdatetimepicker" />
						&nbsp;&nbsp; 
						<label>Data Fim:</label>
						<s:date id="dataFimId" name="dataFim" format="dd/MM/yyyy"/>
						<s:textfield id="dataFim" name="dataFim" 
							value="%{dataFimId}" maxlength="10" size="11"
							onkeyup="formataData(this);" onblur="tamanhoData(this)" title="dd/mm/aaaa" cssClass="sxdatetimepicker" />
								
																						
					</div> 					
					
					<p class="buttons">
						<a class="reset" href="#" onclick="javascript:limparCampos();">Limpar</a>
						<s:submit  type="button" value=""  action="consultaIdeias.pesquisar"  cssClass="botaoLocalizar" onclick="return validaCampos();"  />	
					</p>
						
									
				</div>	
			     	  				  		    	 
		   </form> 
		</div>				
	</div>	
	
	<div class="birthday-result">				    		
		<s:if test="ideias != null && !ideias.empty">
			<table class="table2" >
				<thead>						
					<tr>
					    <td width="10%" class="sortable">Nome do ciclo</td>
						<td width="10%" class="sortable">Título da ideia</td>		
						<td width="30%" class="sortable">Descrição da ideia</td>
						<td width="10%" class="sortable">A qual setor se aplica</td>	
						<td width="20%" class="sortable">Benefícios</td>
						<td width="10%" class="sortable">Responsável pela sugestão</td>
						<td width="10%" class="sortable">Integrante(s)</td>			
					</tr>
				</thead>
			</table>
			<div class="birthday-body-result" >
			<table class="table" >
				<tbody>			
					<s:iterator value="ideias" status="rowstatus">		
						<tr class="<s:if test="#rowstatus.odd == true ">odd</s:if><s:else>even</s:else>">
							<td width="10%"><s:property value="nomeCiclo"/></td>									
							<td width="10%"><s:property value="nome"/></td>
							<td width="30%" ><s:property value="descricao"/></td>
							<td width="10%"><s:property value="dptoAplica"/></td>	
							<td width="20%"><s:property value="beneficio"/></td>
							<td width="10%"><s:property value="nomeSolicitante"/></td>	
							<td width="10%"><s:property value="integrantes" escapeHtml="false"/></td>
						</tr>			
					</s:iterator>	
				</tbody>	
			</table> 
			</div>
		</s:if>
		<s:else><center><span>Não foi encontrada nenhuma ideia com este(s) critério(s)</span></center></s:else>
	</div>
</div>		
	   
	   <!-- RODAPÈ -->
	<div class="footer">	
		<div class="footer-inner">
			<p>Vitrine de ideias v1.0</p>
		</div>
	</div>
</body>
</html>