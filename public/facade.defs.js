function palettesPreprocess(palettes) {
    // Reorder initial array according to pattern. Note that there's no NCS at the moment
    var display = ['nova', 'ral1', 'ral2', 'ncs2', 'holzer'];
    var ret = [];
    $(display).each(function(i, d) {
        var found = $.grep(palettes, function(p){ return p.nick == d; });
        if(found.length == 1) {
            ret.push(found[0]);
        }
    });
    return ret;
}


function paintPriceUrl() {
    return '/api/paint_price_facade';
}

function colorFormulasUrl() {
    return '/api/color_formulas_facade'
}

function paintsForPaletteUrl(palette) {
    return '/api/paints?facade=true&paletteId=' + palette;
}

function currentPage() {
    return 'FACADE';
}
