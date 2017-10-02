
function inicializarFormularioBusquedaPredio() {
    $('form#crearPredioForm input.ui-inputtext').each(function () {
        $(this).val('');
    });
    $('form#crearPredioForm input#parroquia').focus();
}


function validateIntegerValueAndFocusNext(event, next) {
    var keyCode = event.which || event.keyCode;
    if (keyCode === 13) {

        var element = document.getElementById(next);
        if (element)
        {
            element.focus();

        }
    }

    if (keyCode < 48 || keyCode > 57) {
        event.preventDefault();
        return false;
    }

    return true;
}

function validateFloatKeyPress(event) {
    var keyCode = event.which || event.keyCode;
    if (keyCode > 57) {
        event.preventDefault();
        return false;
    }
    if (keyCode < 48) {
        /*. 46  ,44*/
        if (keyCode !== 46 && keyCode !== 44) {
            event.preventDefault();
            return false;
        }
    }

    return true;
}

function justFocusNext(event, next) {
    var keyCode = event.which || event.keyCode;
    if (keyCode === 13) {

        var element = document.getElementById(next);
        if (element)
        {
            element.focus();

        }
    }

    return true;
}

$(document).ready(function () {

    $('form').bind("keypress", function (e) {
        if (e.keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });

    $(document).on('keypress', 'input.app-input-float', function (event) {
        validateFloatKeyPress(event);
    });
});