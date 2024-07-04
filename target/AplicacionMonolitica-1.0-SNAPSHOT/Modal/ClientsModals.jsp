<!-- Modal add Cliente-->
<div class="modal fade" id="modalAddCliente" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Agregar Cliente</h5>
                <button type="button" class="btn-close cerrarmodal" data-mdb-ripple-init data-mdb-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="frmCliente">
                    <div class="row m-0 p-0">
                        <div class="col-md-12">
                            <input type="text" style="display:none" class="form-control form-control-sm" name="type" id="type" value="2"/>
                            <label>Nombre</label>
                            <input type="text" class="form-control form-control-sm" name="descnombre" id="txtNombre"/>
                        </div>
                        <div class="col-md-12">
                            <label>Apellido</label>
                            <input type="text" class="form-control form-control-sm" name="descapellido" id="txtApellido"/>
                        </div>
                        <div class="col-md-12">
                            <label>Email</label>
                            <input type="email" class="form-control form-control-sm" name="descemail" id="txtEmail"/>
                        </div>
                        <div class="col-md-12">
                            <label>Telefono</label>
                            <input type="tel" class="form-control form-control-sm" name="numtelefono" id="txtTelefono"/>
                        </div>
                         <div class="col-md-12">
                            <label>Saldo</label>
                            <input type="number" class="form-control form-control-sm" name="mntsaldo" id="mtnSaldo" required/>
                        </div>
                    </div> 
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary cerrarmodal" data-mdb-ripple-init data-mdb-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-primary" id="btnAgregarCliente" data-mdb-ripple-init>Guardar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal update Cliente-->
<div class="modal fade" id="modalUpdateCliente" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Actualizar Cliente</h5>
                <button type="button" class="btn-close cerrarmodal" data-mdb-ripple-init data-mdb-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="frmClienteUpdate">
                    <div class="row m-0 p-0">
                        <input type="hidden" class="form-control form-control-sm" name="type" value="4"/>
                        <input type="hidden" class="form-control form-control-sm" name="idcliente" id="idcliente"/>
                        <div class="col-md-12">
                            <label>Nombre</label>
                            <input type="text" class="form-control form-control-sm" name="descnombre" id="descnombre" required placeholder="Nuevo Nombre"/>
                        </div>
                        <div class="col-md-12">
                            <label>Apellido</label>
                            <input type="text" class="form-control form-control-sm" name="descapellido" id="descapellido" required placeholder="Nuevo Apellido"/>
                        </div>
                        <div class="col-md-12">
                            <label>Email</label>
                            <input type="email" class="form-control form-control-sm" name="descemail" id="descemail" required placeholder="Nuevo Email"/>
                        </div>
                        <div class="col-md-12">
                            <label>Telefono</label>
                            <input type="tel" class="form-control form-control-sm" name="numtelefono" id="numtelefono" required placeholder="Nuevo telefono"/>
                        </div>
                        <div class="col-md-12">
                            <label>Saldo</label>
                            <input type="number" class="form-control form-control-sm" name="mntsaldo" id="mntsaldo" required placeholder="Nuevo saldo"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary cerrarmodal" data-mdb-ripple-init data-mdb-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-primary" data-mdb-ripple-init id="btnActualizarCliente">Guardar</button>
            </div>
        </div>
    </div>
</div>
