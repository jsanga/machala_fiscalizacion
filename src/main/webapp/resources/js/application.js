
function inicializarFormularioBusquedaPredio() {
    $('form#crearPredioForm input.ui-inputtext').each(function () {
        $(this).val('');
    });
    $('form#crearPredioForm input#zona').focus();
}

function validateIntegerValueAndFocusNext(event, next) {
    var keyCode = event.which || event.keyCode;
    //console.log(keyCode);
    if (keyCode === 13) {
        console.log("Id del input antes: " + next);
        var arr = next.split('\\');
        console.log("Con split pos 0: " + arr[0]);
        next = PrimeFaces.escapeClientId(next);
        console.log("Id del input despues: " + next);

        var element = $(next);
        if (element)
        {
            element.focus();
            element.select();

        }
    }

    if (keyCode < 48) {
        if (keyCode !== 8 && keyCode !== 9 && keyCode !== 37 && keyCode !== 39) {
            event.preventDefault();
            return false;
        }

    }
    if (keyCode > 57) {
        event.preventDefault();
        return false;
    }
    
    return true;
}

function validateIntegerValueAndFocusNextComponent(event, next) {
    var keyCode = event.which || event.keyCode;
    //console.log(keyCode);
    if (keyCode === 13) {
        console.log("Id del input antes: " + next);
        var arr = next.split('\\');
        console.log("Con split pos 0: " + arr[0]);
        console.log("Con split pos 1: " + arr[1]);
        var identificador = '#' + arr[0] + arr[1];
        console.log("ID nuevo: " + identificador);
        var element = $(identificador);
        if (element)
        {
            element.focus();
            element.select();

        }
    }

    if (keyCode < 48) {
        if (keyCode !== 8 && keyCode !== 46 && keyCode !== 9 && keyCode !== 37 && keyCode !== 39) {
            event.preventDefault();
            return false;
        }

    }
    if (keyCode > 57) {
        event.preventDefault();
        return false;
    }

    return true;
}

function validateFloatValueAndFocusNext(event, next) {
    var keyCode = event.which || event.keyCode;
    if (keyCode === 13) {
        next = PrimeFaces.escapeClientId(next);
        var element = $(next);
        if (element)
        {
            element.focus();
            element.select();

        }
    }

    if (keyCode > 57) {
        event.preventDefault();
        return false;
    }
    if (keyCode < 48) {
        /*. 46  ,44*/
        if (keyCode !== 8 && keyCode !== 46 && keyCode !== 9 && keyCode !== 37 && keyCode !== 39 && keyCode !== 44) {
            event.preventDefault();
            return false;
        }
    }

    return true;
}

function focusNextOnEnter(event, next) {
    var keyCode = event.which || event.keyCode;
    if (keyCode === 13) {
        next = PrimeFaces.escapeClientId(next);
        var element = $(next);
        if (element)
        {
            element.focus();
            element.select();
            event.preventDefault();
        }
    }
    return true;
}

function focusElement(next) {
    next = PrimeFaces.escapeClientId(next);
    var element = $(next);
    if (element)
    {
        element.focus();
        element.select();

    }

}

function validateFloatKeyPressAndCheckNumber(event, numberTyped) {
    var keyCode = event.which || event.keyCode;

    console.log(keyCode + " " + numberTyped);

    if (keyCode > 57) {
        event.preventDefault();
        return false;
    }
    if (keyCode < 48) {
        /*. 46  ,44*/
        if ((keyCode === 46 || keyCode === 44) && (numberTyped.indexOf(".") > -1 || numberTyped.indexOf(",") > -1)) {
            event.preventDefault();
            return false;
        }
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
        if (keyCode !== 8 && keyCode !== 9 && keyCode !== 46 && keyCode !== 37 && keyCode !== 39 && keyCode !== 44) {
            event.preventDefault();
            return false;
        }
    }
    return true;
}

function validateIntegerKeyPress(event) {
    var keyCode = event.which || event.keyCode;

    if (keyCode < 48) {
        if (keyCode !== 8 && keyCode !== 37 && keyCode !== 39 && keyCode !== 9 && keyCode !== 13) {
            event.preventDefault();
            return false;
        }

    }
    if (keyCode > 57) {
        event.preventDefault();
        return false;
    }
    return true;
}

function transformarTitulosWizard() {
    $('ul.ui-wizard-step-titles>li.ui-wizard-step-title').each(function (e) {

        $(this).removeClass('ui-wizard-step-title ui-corner-all ui-state-default');
        $(this).addClass('app-wizard-step-title ');


        console.log($(this).html());
        $(this).html('<a href="#">' + $(this).html() + '</a>');

    });
}

$(document).ready(function () {
    /*
     $('form').bind("keypress", function (e) {
     if (e.keyCode == 13) {
     e.preventDefault();
     return false;
     }
     });*/

    $(document).on('keypress', 'input.app-input-float', function (event) {
        validateFloatKeyPress(event);
    });
    $(document).on('keypress', 'input.app-input-integer', function (event) {
        validateIntegerKeyPress(event);
    });


    /*$('div.varDisContainer').each(function(){
     var input = $(this).find('input.ui-inputtext').first();
     var selectContainer = $(this).find('.varDisSelectContainer').first();
     
     input.on('blur',{},function(){
     console.log($(this).val());
     });
     });*/


    $(document).on('blur', 'div.varDisContainer input.ui-inputtext', function () {
        var value = $(this).val();
        var id = $(this).attr("id");
        var field = $(this).attr("data-field");
        var min = parseInt($(this).attr("data-min-value"));
        var max = parseInt($(this).attr("data-max-value"));
        var defaultValue = $(this).attr("data-default-value");

        var allValues = $(this).attr("data-all-values");
        var values = allValues.split("-");

        var a = values.indexOf(value);

        if(a === -1)
            value = defaultValue;

        $(this).val(parseInt(value));

        var arr = id.split(':');
        var prefIdSelect = '';

        for (var i = 0; i < arr.length - 1; i++) {
            prefIdSelect += arr[i] + '\\:';
        }
        var idSelect = prefIdSelect + field + '-select_input';
        var idLabel = prefIdSelect + field + '-select_label';

        $("#" + idSelect).val(parseInt(value));
        $("#" + idLabel).html($("#" + idSelect).find('option:selected').text());
    });

    $(document).on('change', 'div.varDisContainer select', function () {
        var value = $(this).val();
        var id = $(this).attr("id");
        var field = $(this).attr("data-field");

        var arr = id.split(':');
        var prefIdSelect = '';

        for (var i = 0; i < arr.length - 1; i++) {
            prefIdSelect += arr[i] + '\\:';
        }
        var idInput = prefIdSelect + field + '-input';

        $("#" + idInput).val(value);
//        $("#"+idSelect).val(value);
//         $("#"+idLabel).html($("#"+idSelect).find('option:selected').text());
    });


///////////////////////////////////////////////////
 $(document).on('blur', 'div.varDisContainerUSO input.ui-inputtext', function () {
        var value = $(this).val();
        var id = $(this).attr("id");
        var field = $(this).attr("data-field");
        var min = parseInt($(this).attr("data-min-value"));
        var max = parseInt($(this).attr("data-max-value"));
        var defaultValue = $(this).attr("data-default-value");

        var allValues = $(this).attr("data-all-values");
        var values = allValues.split("-");

        var a = values.indexOf(value);

        if(a === -1)
            value = defaultValue;

        $(this).val(parseInt(value));

console.log(" Entro al chage: " + id);

        $("#uso_suelo-select_input").val(parseInt(value));
        $("#uso_suelo-select_label").html($("#uso_suelo-select_input").find('option:selected').text());
    });

    $(document).on('change', 'div.varDisContainerUSO select', function () {
        var value = $(this).val();
        var id = $(this).attr("id");
        var field = $(this).attr("data-field");
        
        console.log(" Entro al chage: " + id);

        var arr = id.split(':');
        var prefIdSelect = '';

        for (var i = 0; i < arr.length - 1; i++) {
            prefIdSelect += arr[i] + '\\:';
        }
        var idInput = prefIdSelect + field + '-input';
      //  console.log()

        $("#uso_suelo-input").val(value);
//        $("#"+idSelect).val(value);
//         $("#"+idLabel).html($("#"+idSelect).find('option:selected').text());
    });


/////////////////////////////////////////////////


    $(document).on('blur', 'div.varDisContainerCombo input.ui-inputtext', function () {
        var value = $(this).val();
        var id = $(this).attr("id");
        var field = $(this).attr("data-field");
        var min = parseInt($(this).attr("data-min-value"));
        var max = parseInt($(this).attr("data-max-value"));
        var defaultValue = $(this).attr("data-default-value");

        var allValues = $(this).attr("data-all-values");
        var values = allValues.split("-");

        var a = values.indexOf(value);

        if(a === -1)
            value = defaultValue;

        $(this).val(parseInt(value));

        var arr = id.split(':');
        var prefIdSelect = '';

        for (var i = 0; i < arr.length - 1; i++) {
            prefIdSelect += arr[i] + '\\:';
        }
        var idSelect = field + '-select_input';
        var idLabel =  field + '-select_label';

        $("#" + idSelect).val(parseInt(value));
        $("#" + idLabel).html($("#" + idSelect).find('option:selected').text());
    });

    $(document).on('change', 'div.varDisContainerCombo select', function () {
        var value = $(this).val();
        var id = $(this).attr("id");
        var field = $(this).attr("data-field");

        var arr = id.split(':');
        var prefIdSelect = '';

        for (var i = 0; i < arr.length - 1; i++) {
            prefIdSelect += arr[i] + '\\:';
        }
        var idInput = prefIdSelect + field + '-input';

        $("#" + idInput).val(value);
//        $("#"+idSelect).val(value);
//         $("#"+idLabel).html($("#"+idSelect).find('option:selected').text());
    });



    inicializarFormularioBusquedaPredio();
});
