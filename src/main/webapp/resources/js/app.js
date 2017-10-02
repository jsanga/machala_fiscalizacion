
window.mapManager = null;
window.coordinates = null;
window.PrimeFaces;

$(document).ready(function () {

    var defaultLoadParameters = {
        service: 'WFS',
        version: '1.1.0',
        request: 'getFeature',
        srsname: Constants.map.srsname,
        outputFormat: 'text/javascript'
    };
    var map = new L.Map('map', {
        center: [-2.23725443384787, -80.9373380726774],
        zoom: 18
    });


    var ortofoto = L.tileLayer.wms("http://localhost:8081/geoserver/Salinas/wms", {
        layers: 'Salinas:ortofoto',
        format: 'image/png',
        transparent: true,
        version: '1.1.0',
        attribution: "Ortofoto Salinas"
    });
    ortofoto.setZIndex(0);
    ortofoto.addTo(map);

    var defaultStyle = {color: "#002962", weight: 2, opacity: 1, fillOpacity: 0.1, fillColor: "#002962"};
    var highlightStyle = {color: '#002962', weight: 3, opacity: 0.6, fillOpacity: 0.65, fillColor: '#002962'};

    var predios = new L.geoJson(null, {
        onEachFeature: function (feature, layer) {
            // Create a self-invoking function that passes in the layer
            // and the properties associated with this particular record.
            (function (layer, properties) {
                // Create a mouseover event
                layer.on("mouseover", function (e) {
                    // Change the style to the highlighted version
                    layer.setStyle(highlightStyle);
                });
                // Create a mouseout event that undoes the mouseover changes
                layer.on("mouseout", function (e) {
                    // Start by reverting the style back
                    layer.setStyle(defaultStyle);
                });
            })(layer, feature.properties);
        },
        style: function (feature) {
            return defaultStyle;
        }
    });

    function cargarPredios(data) {
        predios.addData(data);
    }

    var defaultP = $.extend({}, defaultLoadParameters);

    var parameters = $.extend(defaultP, {
        typeName: Constants.geoserver.workspace + ':predios',
        format_options: 'callback:getJson'
    });

    $.ajax({
        url: Constants.geoserver.url + 'wfs' + L.Util.getParamString(parameters),
        dataType: 'jsonp',
        jsonp: false,
        timeout: 5000,
        success: cargarPredios,
        jsonpCallback: 'getJson',
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.readyState != 4) {
                alert("Error al cargar la capa");
            }

        }
    });
    predios.setZIndex(3);
    predios.addTo(map);
    map.addLayer(predios);

});

