<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!--FONT AWESOME-->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://kit.fontawesome.com/233c0f00ee.js" crossorigin="anonymous"></script>
        <!-- apache echarts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.5.0/echarts.min.js"></script>
        <link href="css/graficos.css" rel="stylesheet" type="text/css"/>
        <jsp:include page="PartialView/Header.jsp"></jsp:include>
    </head>
    <body>
        <div id="page-content-wrapper">
            <button type="button" class="hamburger animated fadeInLeft is-closed" data-toggle="offcanvas">
                <span class="hamb-top"></span>
    		<span class="hamb-middle"></span>
		<span class="hamb-bottom"></span>
            </button>
            
            <div class="row">
                <div class="col-md-4">
                    <div class="card" id="" style="border: 1px solid darkturquoise;">
                        <div class="row g-0">
                            <div class="card-body col-md-8  align-items-center justify-content-center">
                                <h5 class="card-title text-center">Clientes</h5>
                                <p class="card-text text-center" id="cantidadClientes"></p>
                            </div>
                            <div class="card-body col-md-4 d-flex align-items-center justify-content-center">
                              <p class="card-text" id=""><i class="fa-solid fa-users fa-2x"></i></p>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card" id="" style="border: 1px solid darkturquoise;">
                        <div class="row g-0">
                            <div class="card-body col-md-8 align-items-center justify-content-center">
                              <h5 class="card-title text-center">Clientes nuevos</h5>
                              <p class="card-text text-center" id="cantidadClientes"></p>
                            </div>
                            <div class="card-body col-md-4 d-flex align-items-center justify-content-center">
                              <p class="card-text" id=""><i class="fa-solid fa-eye fa-2x"></i></p>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card" id="" style="border: 1px solid darkturquoise;">
                        <div class="row g-0">
                            <div class="card-body col-md-8 align-items-center justify-content-center">
                              <h5 class="card-title text-center">Clientes eliminados</h5>
                              <p class="card-text text-center" id="cantidadClientesEliminados"></p>
                            </div>
                            <div class="card-body col-md-4 d-flex align-items-center justify-content-center">
                              <p class="card-text" id=""><i class="fa-solid fa-trash fa-2x"></i></p>
                            </div>
                        </div>
                    </div>
                </div>
               
            </div>
            <div class="containerGraficos">
                <div class="row">
                    <div clas="col-sm-12 col-md-6 col-lg-6 col-xl-6">
                        <div id="chart1" class="chart"></div>
                    </div>
                </div>
            </div>
            
        </div>
        <jsp:include page="PartialView/Footer.jsp"></jsp:include>
        <jsp:include page="PartialView/Sidebar.jsp"></jsp:include>
        <script src="Scripts/ClienteFunctions.js"></script>
        <script src="Scripts/graficos.js"></script>
    
    </body>
</html>
