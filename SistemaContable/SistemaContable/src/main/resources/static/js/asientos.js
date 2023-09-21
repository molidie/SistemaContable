// Declara las variables para acumular los valores
var totalDebe = 0;
var totalHaber = 0;

// Variable para almacenar los valores antes de eliminar la fila
var valoresFilaEliminada = { debe: 0, haber: 0 };

// Función para agregar un nuevo registro a la tabla
function agregarValorATabla() {
    // Obtener los valores ingresados por el usuario
    var cuentaSelect = document.getElementById("cuenta");
    var cuentaNombre = cuentaSelect.options[cuentaSelect.selectedIndex].text;
    var monto = parseFloat(document.getElementById("monto").value); // Convertir a número
    var debeRadio = document.getElementById("debe").checked;
    var haberRadio = document.getElementById("haber").checked;

    // Verificar si se seleccionó "Debe" o "Haber"
    var debe, haber;
    if (debeRadio) {
        debe = monto;
        haber = 0;
        // Agregar el monto a totalDebe
        totalDebe += monto;
    } else if (haberRadio) {
        debe = 0;
        haber = monto;
        // Agregar el monto a totalHaber
        totalHaber += monto;
    } else {
        alert("Por favor, seleccione 'Debe' o 'Haber'.");
        return;
    }

    // Llama a la función para habilitar o deshabilitar el botón de guardar
    habilitarGuardar();

    // Crear una nueva fila en la tabla
    var fila = document.createElement("tr");
    fila.className = "fila-asiento";

    // Crear celdas para cada columna
    var columnaCuenta = document.createElement("td");
    columnaCuenta.textContent = cuentaNombre;
    if (haberRadio) {
        // Si es "Haber," alinea el texto a la derecha
        columnaCuenta.style.textAlign = "right";
    }

    var columnaDebe = document.createElement("td");
    columnaDebe.textContent = debe;

    var columnaHaber = document.createElement("td");
    columnaHaber.textContent = haber;

    var columnaEditar = document.createElement("td");
    var botonEditar = document.createElement("button");
    botonEditar.textContent = "Editar";
    botonEditar.className = "btn btn-primary";
    botonEditar.onclick = function () {
        // Lógica para mostrar campos de entrada y ocultar texto
        editarFila(fila);
        alert("Mientras editas no presiones 'Enter' o perderas tus datos");
    };

    var columnaEliminar = document.createElement("td");
    var botonEliminar = document.createElement("button");
    botonEliminar.textContent = "Eliminar";
    botonEliminar.className = "btn btn-danger";
    botonEliminar.onclick = function () {
        // Eliminar la fila al hacer clic en el botón "Eliminar"
        eliminarFila(fila);
    };

    columnaEditar.appendChild(botonEditar);
    columnaEliminar.appendChild(botonEliminar);

    // Agregar celdas a la fila
    fila.appendChild(columnaCuenta);
    fila.appendChild(columnaDebe);
    fila.appendChild(columnaHaber);
    fila.appendChild(columnaEditar);
    fila.appendChild(columnaEliminar);

    // Agregar la fila a la tabla
    var tabla = document.querySelector("table tbody");
    tabla.appendChild(fila);

    // Limpiar los valores del formulario después de agregar
    document.getElementById("cuenta").value = "Seleccionar cuenta";
    document.getElementById("monto").value = "";
    document.getElementById("debe").checked = false;
    document.getElementById("haber").checked = false;
}

// Función para habilitar o deshabilitar el botón de guardar
function habilitarGuardar() {
    // Calcula la diferencia entre totalDebe y totalHaber
    var diferencia = totalDebe - totalHaber;

    // Obtén el botón de guardar
    var botonGuardar = document.getElementById("guardarAsiento");

    // Verifica si la diferencia es igual a cero
    if (diferencia === 0) {
        // Habilita el botón si la diferencia es cero
        botonGuardar.disabled = false;
    } else {
        // Deshabilita el botón si la diferencia no es cero
        botonGuardar.disabled = true;
    }
}

// Función para eliminar una fila
function eliminarFila(fila) {
    // Obtén los montos de las celdas antes de eliminar la fila
    var celdaDebe = parseFloat(fila.querySelector("td:nth-child(2)").textContent) || 0;
    var celdaHaber = parseFloat(fila.querySelector("td:nth-child(3)").textContent) || 0;

    // Guarda los valores antes de eliminar la fila
    valoresFilaEliminada.debe = celdaDebe;
    valoresFilaEliminada.haber = celdaHaber;

    // Resta los montos de la fila eliminada de las variables totales
    totalDebe -= celdaDebe;
    totalHaber -= celdaHaber;

    // Llama a la función para habilitar o deshabilitar el botón de guardar
    habilitarGuardar();

    // Elimina solo la fila en cuestión
    fila.remove();
}
// ...

// Función para editar una fila
function editarFila(fila) {
    // Obtén los elementos de texto existentes en la fila
    var textoCuenta = fila.querySelector("td:nth-child(1)");
    var textoDebe = fila.querySelector("td:nth-child(2)");
    var textoHaber = fila.querySelector("td:nth-child(3)");

    // Crea campos de entrada para editar
    var inputCuenta = document.createElement("input");
    inputCuenta.type = "text";
    inputCuenta.value = textoCuenta.textContent;

    var inputDebe = document.createElement("input");
    inputDebe.type = "number";
    inputDebe.value = textoDebe.textContent;

    var inputHaber = document.createElement("input");
    inputHaber.type = "number";
    inputHaber.value = textoHaber.textContent;

    // Reemplaza elementos de texto por campos de entrada
    textoCuenta.innerHTML = '';
    textoCuenta.appendChild(inputCuenta);

    textoDebe.innerHTML = '';
    textoDebe.appendChild(inputDebe);

    textoHaber.innerHTML = '';
    textoHaber.appendChild(inputHaber);

    // Agrega un botón "Guardar" en lugar del botón "Editar"
    var columnaEditar = fila.querySelector("td:nth-child(4)");
    columnaEditar.innerHTML = '';
    var botonGuardar = document.createElement("button");
    botonGuardar.textContent = "Guardar";
    botonGuardar.className = "btn btn-success";
    botonGuardar.onclick = function () {
        guardarFila(fila, inputDebe, inputHaber); // Pasa los campos inputDebe e inputHaber como argumentos
    };
    columnaEditar.appendChild(botonGuardar);
}

// Declara las variables para acumular los valores
var totalDebe = 0;
var totalHaber = 0;

// Variable para almacenar los valores antes de eliminar la fila
var valoresFilaEliminada = { debe: 0, haber: 0 };

// Función para agregar un nuevo registro a la tabla
function agregarValorATabla() {
    // Obtener los valores ingresados por el usuario
    var cuentaSelect = document.getElementById("cuenta");
    var cuentaNombre = cuentaSelect.options[cuentaSelect.selectedIndex].text;
    var monto = parseFloat(document.getElementById("monto").value); // Convertir a número
    var debeRadio = document.getElementById("debe").checked;
    var haberRadio = document.getElementById("haber").checked;

    // Verificar si se seleccionó "Debe" o "Haber"
    var debe, haber;
    if (debeRadio) {
        debe = monto;
        haber = 0;
        // Agregar el monto a totalDebe
        totalDebe += monto;
    } else if (haberRadio) {
        debe = 0;
        haber = monto;
        // Agregar el monto a totalHaber
        totalHaber += monto;
    } else {
        alert("Por favor, seleccione 'Debe' o 'Haber'.");
        return;
    }

    // Llama a la función para habilitar o deshabilitar el botón de guardar
    habilitarGuardar();

    // Crear una nueva fila en la tabla
    var fila = document.createElement("tr");
    fila.className = "fila-asiento";

    // Crear celdas para cada columna
    var columnaCuenta = document.createElement("td");
    columnaCuenta.textContent = cuentaNombre;
    if (haberRadio) {
        // Si es "Haber," alinea el texto a la derecha
        columnaCuenta.style.textAlign = "right";
    }

    var columnaDebe = document.createElement("td");
    columnaDebe.textContent = debe;

    var columnaHaber = document.createElement("td");
    columnaHaber.textContent = haber;

    var columnaEditar = document.createElement("td");
    var botonEditar = document.createElement("button");
    botonEditar.textContent = "Editar";
    botonEditar.className = "btn btn-primary";
    botonEditar.onclick = function () {
        // Lógica para mostrar campos de entrada y ocultar texto
        editarFila(fila);
        alert("Mientras editas no presiones 'Enter' o perderas tus datos");
    };

    var columnaEliminar = document.createElement("td");
    var botonEliminar = document.createElement("button");
    botonEliminar.textContent = "Eliminar";
    botonEliminar.className = "btn btn-danger";
    botonEliminar.onclick = function () {
        // Eliminar la fila al hacer clic en el botón "Eliminar"
        eliminarFila(fila);
    };

    columnaEditar.appendChild(botonEditar);
    columnaEliminar.appendChild(botonEliminar);

    // Agregar celdas a la fila
    fila.appendChild(columnaCuenta);
    fila.appendChild(columnaDebe);
    fila.appendChild(columnaHaber);
    fila.appendChild(columnaEditar);
    fila.appendChild(columnaEliminar);

    // Agregar la fila a la tabla
    var tabla = document.querySelector("table tbody");
    tabla.appendChild(fila);

    // Limpiar los valores del formulario después de agregar
    document.getElementById("cuenta").value = "Seleccionar cuenta";
    document.getElementById("monto").value = "";
    document.getElementById("debe").checked = false;
    document.getElementById("haber").checked = false;
}

// Función para habilitar o deshabilitar el botón de guardar
function habilitarGuardar() {
    // Calcula la diferencia entre totalDebe y totalHaber
    var diferencia = totalDebe - totalHaber;

    // Obtén el botón de guardar
    var botonGuardar = document.getElementById("guardarAsiento");

    // Verifica si la diferencia es igual a cero
    if (diferencia === 0) {
        // Habilita el botón si la diferencia es cero
        botonGuardar.disabled = false;
    } else {
        // Deshabilita el botón si la diferencia no es cero
        botonGuardar.disabled = true;
    }
}

// Función para eliminar una fila
function eliminarFila(fila) {
    // Obtén los montos de las celdas antes de eliminar la fila
    var celdaDebe = parseFloat(fila.querySelector("td:nth-child(2)").textContent) || 0;
    var celdaHaber = parseFloat(fila.querySelector("td:nth-child(3)").textContent) || 0;

    // Guarda los valores antes de eliminar la fila
    valoresFilaEliminada.debe = celdaDebe;
    valoresFilaEliminada.haber = celdaHaber;

    // Resta los montos de la fila eliminada de las variables totales
    totalDebe -= celdaDebe;
    totalHaber -= celdaHaber;

    // Llama a la función para habilitar o deshabilitar el botón de guardar
    habilitarGuardar();

    // Elimina solo la fila en cuestión
    fila.remove();
}
// ...

// Variables para almacenar los valores antes de editar
var haberEditar; // Variable para el valor de "Haber" antes de editar
var debeEditar; // Variable para el valor de "Debe" antes de editar

// ...

// Función para editar una fila
function editarFila(fila) {
    // Obtén los elementos de texto existentes en la fila
    var textoCuenta = fila.querySelector("td:nth-child(1)");
    var textoDebe = fila.querySelector("td:nth-child(2)");
    var textoHaber = fila.querySelector("td:nth-child(3)");

    // Crea campos de entrada para editar
    var inputCuenta = document.createElement("input");
    inputCuenta.type = "text";
    inputCuenta.value = textoCuenta.textContent;

    // Obtén los valores de "Debe" y "Haber" antes de editar
    debeEditar = parseFloat(textoDebe.textContent) || 0;
    haberEditar = parseFloat(textoHaber.textContent) || 0;

    var inputDebe = document.createElement("input");
    inputDebe.type = "number";
    inputDebe.value = debeEditar; // Establece el valor anterior

    var inputHaber = document.createElement("input");
    inputHaber.type = "number";
    inputHaber.value = haberEditar; // Establece el valor anterior

    // Reemplaza elementos de texto por campos de entrada
    textoCuenta.innerHTML = '';
    textoCuenta.appendChild(inputCuenta);

    textoDebe.innerHTML = '';
    textoDebe.appendChild(inputDebe);

    textoHaber.innerHTML = '';
    textoHaber.appendChild(inputHaber);

    // Agrega un botón "Guardar" en lugar del botón "Editar"
    var columnaEditar = fila.querySelector("td:nth-child(4)");
    columnaEditar.innerHTML = '';
    var botonGuardar = document.createElement("button");
    botonGuardar.textContent = "Guardar";
    botonGuardar.className = "btn btn-success";
    botonGuardar.onclick = function () {
        guardarFila(fila, inputDebe, inputHaber); // Pasa los campos inputDebe e inputHaber como argumentos
    };
    columnaEditar.appendChild(botonGuardar);
}

// Función para guardar los cambios en una fila editada
function guardarFila(fila, inputDebe, inputHaber) {
    // Obtén los campos de entrada editados
    var inputCuenta = fila.querySelector("input[type='text']");

    // Obtén los elementos de texto existentes en la fila
    var textoCuenta = fila.querySelector("td:nth-child(1)");
    var textoDebe = fila.querySelector("td:nth-child(2)");
    var textoHaber = fila.querySelector("td:nth-child(3)");

    // Actualiza los valores de la fila con los valores editados
    textoCuenta.textContent = inputCuenta.value;
    var nuevoDebe = parseFloat(inputDebe.value);
    var nuevoHaber = parseFloat(inputHaber.value);
    textoDebe.textContent = nuevoDebe;
    textoHaber.textContent = nuevoHaber;

    // Calcula las diferencias entre los valores nuevos y los anteriores
    var diferenciaDebe = nuevoDebe - debeEditar;
    var diferenciaHaber = nuevoHaber - haberEditar;

    // Actualiza las variables totales sumando las diferencias
    totalDebe += diferenciaDebe;
    totalHaber += diferenciaHaber;

    // Restaura el botón "Editar"
    var columnaEditar = fila.querySelector("td:nth-child(4)");
    columnaEditar.innerHTML = '';
    var botonEditar = document.createElement("button");
    botonEditar.textContent = "Editar";
    botonEditar.className = "btn btn-primary";
    botonEditar.onclick = function () {
        editarFila(fila);
    };
    columnaEditar.appendChild(botonEditar);

    // Llama a la función para habilitar o deshabilitar el botón de guardar
    habilitarGuardar();
}
