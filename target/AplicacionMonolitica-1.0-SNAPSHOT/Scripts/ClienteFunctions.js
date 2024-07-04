$("document").ready(function () {
    $('#tableClientes').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "ServletCliente",
            "type": "GET",
            "data": function (d) {
                d.type = "1";
            },
            "dataSrc": function (json) {
                console.log(json)
                return json.data;
            }
        },
        "searching": false,
        "columns": [
            {"data": "idcliente"},
            {"data": "descnombre"},
            {"data": "descapellido"},
            {"data": "descemail"},
            {"data": "numtelefono"},
            {"data": "mntsaldo"},
            {
                "data": null,
                "render": function (row) {
                    return `
                        <button class="btn btn-sm btn-warning btnActualizarCliente btnActualizar" 
                                data-idcliente="${row.idcliente}" 
                                data-descnombre="${row.descnombre}" 
                                data-descapellido="${row.descapellido}" 
                                data-descemail="${row.descemail}" 
                                data-numtelefono="${row.numtelefono}" 
                                data-mntsaldo="${row.mntsaldo}">
                                <i class="fa-solid fa-marker"></i>
                            </button>
                        <button class="btn btn-sm btn-danger btnEliminar" data-nombre="${row.descnombre} ${row.descapellido}" data-correo="${row.descemail}" data-client="${row.idcliente}">
                            <i class="fa-solid fa-trash-can"></i>
                        </button>
                    `;
                }
            }
        ],
        "createdRow": function (row, data) {
            $(row).attr('id', data.idcliente);
        },
        "language": {
            "emptyTable": "No se encontraron datos.",
            "info": " ",
            "infoEmpty": " ",
            "infoFiltered": "",
            "lengthMenu": "Mostrar _MENU_ registros",
            "loadingRecords": "Cargando...",
            "processing": "Procesando...",
            "search": "Buscar:",
            "zeroRecords": "No se encontraron registros coincidentes",
            "paginate": {
                "first": "Primero",
                "last": "Ultimo",
                "next": "Siguiente",
                "previous": "Anterior"
            }
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
                    data.pag = $('#tableClientes').DataTable().page.info().page;
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
    });



    function totalClientes() {
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
                content += `<p name="conteo" class="card-text">${response.body}</p>`;

                $("#totalclientes").html(content);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    }
    ;
    socket.on("clienteCreado", () => {
        var table = $('#tableClientes').DataTable();
        info = table.page.info()
        $.ajax({
            type: "GET",
            data: {type: 6},
            url: "ServletCliente",
            success: function (response) {
                if (response.body >= ((info.length * (info.page + 1)) - info.length) &&
                        response.body <= (info.length * (info.page + 1))) {
                    table.page(info.page).draw(false);
                }
                totalClientes();
                totalmntSaldo();
                totalcantTelefono();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
                reject(errorThrown);
            }
        });
    });

    socket.on("clienteActualizado", (arg) => {
        const pag = $('#tableClientes').DataTable().page.info().page;
        if (arg.pag == pag) {
            $('#tableClientes').DataTable().page(pag).draw(false);
        }
        totalClientes();
        totalmntSaldo();
        totalcantTelefono();
    });

    socket.on("clienteEliminado", (arg) => {
        var table = $('#tableClientes').DataTable();
        var row = table.row("#" + arg.idcliente);
        row.remove().draw(false);
        totalClientes();
        totalmntSaldo();
        totalcantTelefono();
    });
    function totalmntSaldo() {
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

    }
    ;
    function totalcantTelefono() {
        $.ajax({
            type: "GET",
            data: {
                type: "8"
            },
            url: "ServletCliente",
            beforeSend: function () {
            },
            success: function (response) {
                console.log('cantidad numero telefono: ', response);
                var content = "";
                content += `<p class="card-text">${response.body}</p>`;

                $("#totalCantTelefono").html(content);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    }
    ;
    function totalClientesEliminados() {
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
                content += `<p name="conteo" class="card-text">${response.body}</p>`;

                $("#totalclientes").html(content);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
            }
        });
    }
    ;

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