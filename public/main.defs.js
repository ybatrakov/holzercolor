function paintPriceUrl() {
    return '/api/paint_price';
}

function colorFormulasUrl() {
    return '/api/color_formulas'
}

function paintsForPaletteUrl(palette) {
    return '/api/paints?paletteId=' + palette;
}

function currentPage() {
    return 'REGULAR';
}
