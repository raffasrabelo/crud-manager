<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>CRUD Manager - Consultas</title>
</head>
<body>
    <%@include file="modal.html"%>
    <%@include file="nav-menu.jsp"%>

    <div id="container" class="container-fluid">
        <div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${message}
        </div>

        <div id="top" class="row">
            <div class="col-md-3">
                <h3>Consultas</h3>
            </div>
            <div class="col-md-6">
                <div class="input-group h2">
                    <input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar consultas">
                    <span class="input-group-btn">
                        <button class="btn btn-danger" type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                </div>
            </div>
            <div class="col-md-3">
                <a href="/crud-manager/consultation/form" class="btn btn-danger pull-right h2">
                    <span class="glyphicon glyphicon-plus"></span>&nbspAdicionar Consulta
                </a>
            </div>
        </div>

        <hr />

        <div id="list" class="row">
            <div class="table-responsive col-md-12">
                <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
                    <thead>
                        <tr>
                            <th>Data/Hora</th>
                            <th>Motivo</th>
                            <th>Urgência</th>
                            <th>Animal</th>
                            <th>Tutor</th>
                            <th>Editar</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cons" items="${consultas}">
                            <tr>
                                <td>${cons.dataHoraFormatada}</td>
                                <td>${cons.motivo}</td>
                                <td>${cons.urgencia}</td>
                                <td>${cons.animal.nomeAnimal}</td>
                                <td>${cons.animal.tutor}</td>
                                <td class="actions">
                                    <a class="btn btn-info btn-xs"
                                       href="${pageContext.request.contextPath}/consultation/update?id=${cons.id}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                    
                                </td>
                                <td class="actions">
                                    <a class="btn btn-danger btn-xs modal-remove"
                                        consultation-id="${cons.id}"
                                        consultation-motivo="${cons.motivo}"
                                        consultation-animal="${cons.animal.nomeAnimal}"
                                        data-toggle="modal"
                                        data-target="#delete-modal" href="#">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            setTimeout(function() {
                $("#alert").slideUp(500);
            }, 3000);

            $(".modal-remove").click(function () {
                var motivo = $(this).attr('consultation-motivo');
                var animal = $(this).attr('consultation-animal');
                var id = $(this).attr('consultation-id');
                $(".modal-body #hiddenValue").text("a consulta '" + motivo + "' marcada para o animal '" + animal + "'");
                $("#id").attr("value", id);
                $("#form").attr("action", "consultation/delete");
            });
        });
    </script>
</body>
</html>