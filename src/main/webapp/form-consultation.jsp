<!DOCTYPE html>
<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>Cadastro de Consulta</title>
</head>
<body>
    <%@include file="nav-menu.jsp"%>
    
    <div id="container" class="container-fluid">
        <h3 class="page-header">${not empty consultation ? 'Atualizar' : 'Adicionar'} Consulta</h3>
        <form action="${pageContext.request.contextPath}/consultation/${action}" method="POST">
            <input type="hidden" value="${consultation.id}" name="consultationId">

            <div class="row">
                <div class="form-group col-md-6">
                    <label for="dataHora">Data e Hora</label>
                    <input type="datetime-local" class="form-control" id="dataHora" name="dataHora"
                        required
                        value="${not empty consultation ? consultation.dataHoraLocal : ''}" />
                </div>
                <div class="form-group col-md-6">
                    <label for="motivo">Motivo</label>
                    <input type="text" class="form-control" id="motivo" name="motivo"
                        maxlength="15"
                        required
                        placeholder="Motivo da consulta"
                        value="${consultation.motivo}" />
                </div>
            </div>
            
            <div class="row">
                <div class="form-group col-md-6">
                    <label>Urgência</label><br>
                    <label class="radio-inline">
                        <input type="radio" name="urgencia" value="Alta"
                            ${consultation.urgencia == 'Alta' ? 'checked' : ''} required> Alta
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="urgencia" value="Normal"
                            ${consultation.urgencia == 'Normal' ? 'checked' : ''}> Normal
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="urgencia" value="Baixa"
                            ${consultation.urgencia == 'Baixa' ? 'checked' : ''}> Baixa
                    </label>
                </div>
                <div class="form-group col-md-6">
                    <label for="animal">Animal</label>
                    <select id="animal" class="form-control" name="animalId" required>
                        <option value="">Selecione um animal</option>
                        <c:forEach var="animal" items="${animals}">
                            <option value="${animal.idAnimal}"
                                ${not empty consultation && consultation.animal.idAnimal == animal.idAnimal ? 'selected' : ''}>
                                ${animal.idAnimal} - ${animal.nomeAnimal}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <hr />
            <div id="actions" class="row pull-right">
                <div class="col-md-12">
                    <a href="${pageContext.request.contextPath}/consultations" class="btn btn-default">Cancelar</a>
                    <button type="submit" class="btn btn-primary">
                        ${not empty consultation ? 'Atualizar' : 'Cadastrar'} Consulta
                    </button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>