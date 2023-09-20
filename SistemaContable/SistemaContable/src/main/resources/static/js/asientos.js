// Declara las variables para acumular los valores
var totalDebe = 0;
var totalHaber = 0;
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
        //agregar el monto a totalDebe
        totalDebe += monto;
        totalHaber += 0; // No suma al totalHaber en este caso
    } else if (haberRadio) {
        debe = 0;
        haber = monto;
        // Agregar el monto a totalHaber
        totalHaber += monto;
        totalDebe += 0; // No suma al totalDebe en este caso
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

    var columnaEliminar = document.createElement("td");
    var botonEliminar = document.createElement("button");
    botonEliminar.textContent = "Eliminar";
    botonEliminar.className = "btn btn-danger";
    botonEliminar.onclick = function () {
        // Eliminar la fila al hacer clic en el botón "Eliminar"
        fila.remove();
    };

    columnaEliminar.appendChild(botonEliminar);

    // Agregar celdas a la fila
    fila.appendChild(columnaCuenta);
    fila.appendChild(columnaDebe);
    fila.appendChild(columnaHaber);
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

