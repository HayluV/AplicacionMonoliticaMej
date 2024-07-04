$("document").ready(function () {
    
    $.ajax({
        type: "GET",
        data:{type: "1"},
        url: "ServletCliente",
        beforeSend: function () {

        }, success: function (arg) {
            console.log(arg);
            var content = "";
            for (var i = 0; i < arg.body.length; i++) {
                content += `<tr id="${arg.body[i].idcliente}">`;
                content += `<td>${arg.body[i].idcliente}</td>`;
                content += `<td>${arg.body[i].descnombre}</td>`;
                content += `<td>${arg.body[i].descapellido}</td>`;
                content += `<td>${arg.body[i].descemail}</td>`;
                content += `<td>${arg.body[i].numtelefono}</td>`;
                content += `<td>${arg.body[i].mntsaldo}</td>`;
                content += `<td>
                            <button class="btn btn-sm btn-warning btnActualizarCliente btnActualizar" 
                                    data-idcliente="${arg.body[i].idcliente}" 
                                    data-descnombre="${arg.body[i].descnombre}" 
                                    data-descapellido="${arg.body[i].descapellido}" 
                                    data-descemail="${arg.body[i].descemail}" 
                                    data-numtelefono="${arg.body[i].numtelefono}" 
                                    data-mntsaldo="${arg.body[i].mntsaldo}">
                                    <i class="fa-solid fa-marker"></i>
                                </button>
                            <button class="btn btn-sm btn-danger btnEliminar" data-client="${arg.body[i].idcliente}">
                                <i class="fa-solid fa-trash-can"></i>
                            </button>
                        </td>`;
                content += `</tr>`;
            }
            $("#bodyTable").html(content);
        }
    });

    // AGREGAR CLIENTE
    $("#btnAgregarCliente").on("click", function () {
        const content = $("#frmCliente").serialize();
        const parametros = new URLSearchParams(content);
        const datos = Object.fromEntries(parametros.entries());
        console.log(content);
        $.ajax({
            type: "POST",
            data: content,
            url: "ServletCliente",
            beforeSend: function () {

            }, success: function (response) {
                if (response.success) {
                    socket.emit("crearCliente", datos);
                    alert("Cliente agregado correctamente");
                    $(".cerrarmodal").click();
                    $("#frmCliente")[0].reset();
                } else {
                    alert("Error al agregar el cliente: " + response.message);
                }
                cerrarYLimpiarModal("frmCliente");
            }, error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error in AJAX request: ", textStatus, errorThrown);
            }
        });
    });

    // LLENAR FORMULARIO
    $(document).on('click', '.btnActualizar', function () {
        var idcliente = $(this).data('idcliente');
        var descnombre = $(this).data('descnombre');
        var descapellido = $(this).data('descapellido');
        var descemail = $(this).data('descemail');
        var numtelefono = $(this).data('numtelefono');
        var mntsaldo = $(this).data('mntsaldo');

        $("#idcliente").val(idcliente);
        $("#descnombre").val(descnombre);
        $("#descapellido").val(descapellido);
        $("#descemail").val(descemail);
        $("#numtelefono").val(numtelefono);
        $("#mntsaldo").val(mntsaldo);
    });

    // ACTUALIZAR CLIENTE
    $("#btnActualizarCliente").click(function () {
        var data = {
            type: "4",
            idcliente: $("#idcliente").val(),
            descnombre: $("#descnombre").val(),
            descapellido: $("#descapellido").val(),
            descemail: $("#descemail").val(),
            numtelefono: $("#numtelefono").val(),
            mntsaldo: $("#mntsaldo").val()
        };
        $.ajax({
            type: "POST",
            data: data,
            url: "ServletCliente",
            beforeSend: function () {},
            success: function (response) {
                if (response.success) {
                    socket.emit("actualizarCliente", data);
                    alert("Cliente actualizado correctamente");
                    $(".cerrarmodal").click();
                } else {
                    alert("Error al actualizar el cliente: " + response.message);
                }
            }
        });
    });

    //ELIMINAR CLIENTE
    $(document).on("click", ".btnEliminar", function () {
        var data = {
            type: "3",
            idcliente: $(this).data("client")
        };
        var confirmation = confirm("¿Estás seguro de que desea eliminar este cliente?");
        if (confirmation) {
            $.ajax({
                type: "POST",
                data: data,
                url: "ServletCliente",
                beforeSend: function () {
                },
                success: function (response) {
                    if (response.success) {
                        socket.emit("eliminarCliente", data);
                        alert("Cliente eliminado correctamente");
                    } else {
                        alert("Error al eliminar el cliente: " + response.message);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
                }
            });
        }
    });

    // CONEXION SOCKET
    var socket = io.connect("ws://Localhost:1881", {forceNew: true});
    socket.on("connect", () => {
        console.log("Socket conectado: " + socket.connected + " Socket id: " + socket.id);
        //Para el servidor
        socket.emit("socket_user", "Se conectó un usuario");
        totalClientes();
        totalmntSaldo();
        totalcantTelefono();
        totalClientesEliminados();totalClientes();
        totalmntSaldo();
        totalcantTelefono();
        totalClientesEliminados();
    });

    socket.on("clienteCreado", (arg) => {
        
        arg.type = 5;
        $.ajax({
            type: "GET",
            data: arg,
            url: "ServletCliente",
            beforeSend: function () {
            },
            success: function (response) {
                content = `<tr id="${response.body}">`;
                content += `<td>${response.body}</td>`;
                content += `<td>${arg.descnombre}</td>`;
                content += `<td>${arg.descapellido}</td>`;
                content += `<td>${arg.descemail}</td>`;
                content += `<td>${arg.numtelefono}</td>`;
                content += `<td>${arg.mntsaldo}</td>`;
                content += `<td>
                            <button class="btn btn-sm btn-warning btnActualizarCliente btnActualizar" 
                                    data-idcliente="${response.body}" 
                                    data-descnombre="${arg.descnombre}" 
                                    data-descapellido="${arg.descapellido}" 
                                    data-descemail="${arg.descemail}" 
                                    data-numtelefono="${arg.numtelefono}" 
                                    data-mntsaldo="${arg.mntsaldo}">
                                    <i class="fa-solid fa-marker"></i>
                                </button>
                            <button class="btn btn-sm btn-danger btnEliminar" data-client="${response.body}">
                                <i class="fa-solid fa-trash-can"></i>
                            </button>
                        </td>`;
                content += `</tr>`;
                $("#bodyTable").append(content);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    });
    
    socket.on("clienteActualizado", (arg) => {
        totalClientes();
        totalmntSaldo();
        totalcantTelefono();
        totalClientesEliminados();
        content = `<tr id="${arg.idcliente}">`;
        content += `<td>${arg.idcliente}</td>`;
        content += `<td>${arg.descnombre}</td>`;
        content += `<td>${arg.descapellido}</td>`;
        content += `<td>${arg.descemail}</td>`;
        content += `<td>${arg.numtelefono}</td>`;
        content += `<td>${arg.mntsaldo}</td>`;
        content += `<td>
                    <button class="btn btn-sm btn-warning btnActualizarCliente btnActualizar" 
                            data-idcliente="${arg.idcliente}" 
                            data-descnombre="${arg.descnombre}" 
                            data-descapellido="${arg.descapellido}" 
                            data-descemail="${arg.descemail}" 
                            data-numtelefono="${arg.numtelefono}" 
                            data-mntsaldo="${arg.mntsaldo}">
                            <i class="fa-solid fa-marker"></i>
                        </button>
                    <button class="btn btn-sm btn-danger btnEliminar" data-client="${arg.idcliente}">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                </td>`;
        content += `</tr>`;
        $("#" + arg.idcliente).replaceWith(content);
    });

    socket.on("clienteEliminado", (arg) => {
        totalClientes();
        totalmntSaldo();
        totalcantTelefono();
        totalClientesEliminados();
        $("#" + arg.idcliente).remove();
    });
    function totalClientes(){
        $.ajax({
            type: "GET",
            data: {
                type: "6"
            },
            url: "ServletCliente",
            beforeSend: function () {
            },
            success: function (response) {
                console.log('contenido cantidad', response);
                var content = "";
                var content2 = "";
                content += `<p name="conteo" class="card-text">${response.body}</p>`;
                content2 += `<p class="card-text text-center" id="cantidadClientes">${response.body}</p>`;
                $("#totalclientes").html(content);
                $("#cantidadClientes").html(content2);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    };
    
    function totalmntSaldo(){
        $.ajax({
            type: "GET",
            data: {
                type: "7"
            },
            url: "ServletCliente",
            beforeSend: function () {
            },
            success: function (response) {
                console.log('cantidad saldo: ', response);
                var content = "";
                content += `<p class="card-text">s/.${response.body}</p>`;
                
                $("#totalSaldo").html(content);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    };
    function totalcantTelefono(){
        $.ajax({
            type: "GET",
            data: {
                type: "8"
            },
            url: "ServletCliente",
            beforeSend: function () {
            },
            success: function (response) {
                console.log('cantidad número teléfono: ', response);
                var content = "";
                content += `<p class="card-text">${response.body}</p>`;
                
                $("#totalCantTelefono").html(content);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    };
    function totalClientesEliminados(){
        $.ajax({
            type: "GET",
            data: {
                type: "9"
            },
            url: "ServletCliente",
            beforeSend: function () {
            },
            success: function (response) {
                console.log('contenido cantidad', response);
                var content = "";
                content += `<p class="card-text text-center" id= "cantidadClientesEliminados">${response.body}</p>`;
                $("#cantidadClientesEliminados").html(content);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    };
    
    $(document).on("click", ".btnActualizarCliente", function () {
        $("#modalUpdateCliente").modal("show");
    });

    $("#btnAddCliente").on("click", () => {
        $("#modalAddCliente").modal("show");
    });

    $(".cerrarmodal").on("click", function () {
        $("#modalAddCliente").modal("hide");
        $("#modalUpdateCliente").modal("hide");
    });

});