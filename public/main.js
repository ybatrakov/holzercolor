function renderPalette(event, paletteNick, paletteShortName) {
    $('#palette-type').text(paletteShortName);
    $('.tab-links').removeClass('active');
    $('#pt' + paletteNick).addClass('active');

    $.ajax({
        url: '/api/palettes?type=' + paletteNick
    }).then(function(palettes){
        palettes.forEach(function(palette) {
            jQuery('<option>', {
                value: palette.id,
                text: palette.name
            }).appendTo('#palette_select');
        });
        $('#palette_select').chosen();
    });
}

$(document).ready(function() {
    $.ajax({
        url: '/api/palette_types'
    }).then(function(data) {
        data.forEach(function(p) {
            jQuery('<button>', {
                id: 'pt' + p.nick,
                class: 'tab-links',
                text: p.shortName,
                onClick: "renderPalette(event, '" + p.nick +"', '" + p.shortName + "')"
            }).appendTo('#palettes');
        });
        renderPalette(event, data[0].nick, data[0].shortName)
    });
});
