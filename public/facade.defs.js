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
