var MapManager = function (container_id) {

    this.visibleLayers = {};
    proj4.defs("EPSG:32717", Constants.proj4js.epsg_32717);

    this.defaultLoadParameters = {
        service: 'WFS',
        version: '1.1.0',
        request: 'getFeature',
        srsname: Constants.map.srsname,
        outputFormat: 'text/javascript'
    };

//    var element = document.getElementById("id_hidden_coord");
//    var x = parseFloat(element.attr("data-coord-X"));
//    var y = parseFloat(element.attr("data-coord-Y"));


    this.map = L.map(container_id, {
        zoom: 18,
        center: [-2.23725443384787, -80.9373380726774],
        zoomControl: false,
        attributionControl: true
    });
    var zoomControl = L.control.zoom({
        position: 'topleft'
    });
    this.map.addControl(zoomControl);
};






//<editor-fold defaultstate="collapsed" desc="BBoxString">
MapManager.prototype.BBoxString = function () {
    var bounds = this.map.getBounds();
    return '' + bounds.getSouth() + ',' + bounds.getWest() + ',' + bounds.getNorth() + ',' + bounds.getEast();
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="findLayer">
MapManager.prototype.findLayer = function (title) {
    var a = null;
    for (var key in this.visibleLayers) {
        if (this.visibleLayers.hasOwnProperty(key)) {
            if (key === title) {
                a = this.visibleLayers[key];
                break;
            }
        }
    }
    return a;
};
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="adicionarCapa">
MapManager.prototype.adicionarCapa = function (layer_name, index, color) {
    switch (layer_name) {
        case 'predios':
            this.crearCapaPredios(index);
            if (this.visibleLayers.predios) {
                this.visibleLayers.predios.addTo(this.map);
            }
            break;
        case 'manzanas':
            this.crearCapaManzanas(index);
            if (this.visibleLayers.manzanas) {
                this.visibleLayers.manzanas.addTo(this.map);
            }
            break;
        case 'ortofoto':
            layer = L.tileLayer.wms("http://localhost:8081/geoserver/Salinas/wms", {
                layers: 'Salinas:ortofoto',
                format: 'image/png',
                transparent: true,
                version: '1.1.0',
                attribution: "Ortofoto Salinas"
            });
            layer.setZIndex(index);
            layer.addTo(this.map);
            break;
    }
};
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="removeLayer">
MapManager.prototype.removeLayer = function (layer_name) {
    var layer = this.findLayer(layer_name);
    if (layer) {
        this.map.removeLayer(layer);
    }
};
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="cargarPredios">
MapManager.prototype.cargarPredios = function (response) {
    var layer = this.findLayer('predios');
    if (layer) {
        layer.addData(response);
    }
};
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="crearCapaPredios">
MapManager.prototype.crearCapaPredios = function (index) {
    var defaultStyle = {
        color: "#2262CC",
        weight: 2,
        opacity: 1,
        fillOpacity: 0.1,
        fillColor: "#2262CC"
    };
    var highlightStyle = {
        color: '#2262CC',
        weight: 3,
        opacity: 0.6,
        fillOpacity: 0.65,
        fillColor: '#2262CC'
    };

    var layer = new L.geoJson(null, {
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
    var defaultP = $.extend({}, this.defaultLoadParameters);
    var parameters = $.extend(defaultP, {
        typeName: Constants.geoserver.workspace + ':predios',
        format_options: 'callback:mapManager.cargarPredios',
        bbox: this.BBoxString()
    });
    $.ajax({
        url: Constants.geoserver.url + 'wfs' + L.Util.getParamString(parameters),
        dataType: 'jsonp',
        jsonp: false,
        timeout: 5000,
        beforeSend: function () {

        },
        success: function (data, textStatus, jqXHR) {

        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.readyState != 4) {
                $("#modal-alert-title").html("Error");
                $("#modal-alert-body").html("<p>Error al cargar capa</p>");
                $("#alertModal").modal("show");
            }

        }
    });
    layer.setZIndex(index);
    this.visibleLayers.predios = layer;
};
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="cargarMarzanas">
MapManager.prototype.cargarManzanas = function (response) {
    var layer = this.findLayer('manzanas');
    if (layer) {
        layer.addData(response);
    }
};
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="crearCapaManzanas">
MapManager.prototype.crearCapaManzanas = function (index) {
    var defaultStyle = {
        color: "#f70404",
        weight: 2,
        opacity: 1,
        fillOpacity: 0.1,
        fillColor: "#f70404"
    };
    var highlightStyle = {
        color: '#f70404',
        weight: 3,
        opacity: 0.6,
        fillOpacity: 0.65,
        fillColor: '#f70404'
    };
    var marker = L.AwesomeMarkers.icon({
        icon: 'hotel',
        prefix: 'fa',
        markerColor: 'red',
        iconColor: '#fff'
    });

    var layer = new L.geoJson(null, {
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
        },
        pointToLayer: function (feature, latlng) {
            return L.marker(latlng, {icon: marker});

        }
    });
    var defaultP = $.extend({}, this.defaultLoadParameters);
    var parameters = $.extend(defaultP, {
        typeName: Constants.geoserver.workspace + ':manzanas',
        format_options: 'callback:mapManager.cargarManzanas',
        bbox: this.BBoxString()
    });
    $.ajax({
        url: Constants.geoserver.url + 'wfs' + L.Util.getParamString(parameters),
        dataType: 'jsonp',
        jsonp: false,
        timeout: 5000,
        beforeSend: function () {

        },
        success: function (data, textStatus, jqXHR) {

        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.readyState != 4) {
                $("#modal-alert-title").html("Error");
                $("#modal-alert-body").html("<p>Error al cargar capa</p>");
                $("#alertModal").modal("show");
            }

        }
    });
    layer.setZIndex(index);
    this.visibleLayers.manzanas = layer;
};
//</editor-fold>