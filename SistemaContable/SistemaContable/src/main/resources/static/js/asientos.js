window.onload = function () {
    document.getElementById('campoDebe').value = '';
    document.getElementById('campoHaber').value = '';
};
document.getElementById("miFormulario").addEventListener("submit", function (event) {
    var debeInput = document.getElementById("debeInput");
    var haberInput = document.getElementById("haberInput");

    // Verificar si los campos están en blanco y establecer "00" como valor predeterminado
    if (debeInput.value.trim() === "") {
        debeInput.value = "00";
    }

    if (haberInput.value.trim() === "") {
        haberInput.value = "00";
    }
});


// Obtén los elementos de los campos "Debe" y "Haber" por su ID
var campoDebe = document.getElementById("campoDebe");
var campoHaber = document.getElementById("campoHaber");

// Función para desactivar el campo opuesto
function desactivarCampoOpuesto(campoActivo) {
    if (campoActivo === campoDebe) {
        campoHaber.disabled = true;
    } else if (campoActivo === campoHaber) {
        campoDebe.disabled = true;
    }
}

// Función para activar ambos campos
function activarAmbosCampos() {
    campoDebe.disabled = false;
    campoHaber.disabled = false;
}

// Agrega escuchadores de eventos para el evento "input" a ambos campos
campoDebe.addEventListener("input", function () {
    desactivarCampoOpuesto(campoDebe);
});

campoHaber.addEventListener("input", function () {
    desactivarCampoOpuesto(campoHaber);
});

// Agrega escuchadores de eventos para el evento "blur" a ambos campos
campoDebe.addEventListener("blur", function () {
    if (campoDebe.value.trim() === "") {
        activarAmbosCampos();
    }
});

campoHaber.addEventListener("blur", function () {
    if (campoHaber.value.trim() === "") {
        activarAmbosCampos();
    }
});


// Obtén una referencia al botón "Guardar" por su ID
var guardarButton = document.getElementById('guardarButton');

// Agrega un controlador de eventos para el clic en el botón
guardarButton.addEventListener('click', function () {
    // Realiza la redirección a "/admin/home"
    window.location.href = '/admin/home';
});