<%@page import="Datos.Conexion"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="PartialView/Header.jsp"></jsp:include>
    </head>
    <body>
        <%
            Conexion.probarConexion(application);
        %>
        <!-- /#sidebar-wrapper -->
        <div id="page-content-wrapper">
            <button type="button" class="hamburger animated fadeInLeft is-closed" data-toggle="offcanvas">
                <span class="hamb-top"></span>
    		<span class="hamb-middle"></span>
		<span class="hamb-bottom"></span>
            </button>
        <div class="row m-0 p-0">
            <div class="col-md-12 py-2">
                <h1>Mantenimiento Cliente</h1>
            </div>
            <div class="col-md-12">
                <button class="btn btn-success btn-sm" id="btnAddCliente">
                    <i class="fa-solid fa-plus"></i>
                </button>
            </div>
            <div class="col-md-9 table-responsive mt-2">
                <table class="table table-sm table-hover table-bordered">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>Email</th>
                            <th>Telefono</th>
                            <th>Saldo</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="bodyTable">

                    </tbody>
                </table>
            </div>
            <div class="col-md-3 mt-2">
                <div class="card text-center bg-info text-white">
                    <div class="card-body">
                        <h5 class="card-title">Total Clientes</h5>
                        <p name="conteo" id="totalclientes" class="card-text"></p>
                    </div>
                </div>
                <div class="card text-center bg-success text-white mt-2">
                    <div class="card-body">
                        <h5 class="card-title">Total Saldo</h5>
                        <p class="card-text" id="totalSaldo"></p>
                    </div>
                </div>
                <div class="card text-center bg-warning text-white mt-2">
                    <div class="card-body">
                        <h5 class="card-title">Total Clientes con teléfono</h5>
                        <p class="card-text" id="totalCantTelefono"></p>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <jsp:include page="Modal/ClientsModals.jsp"></jsp:include>
        <jsp:include page="PartialView/Footer.jsp"></jsp:include>
        <script src="Scripts/ClienteFunctions.js"></script>
       <jsp:include page="PartialView/Sidebar.jsp"></jsp:include>
    </body>
</html>
