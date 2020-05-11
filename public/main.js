function formatFloat(f, places)
{
    var mult = Math.pow(10, places);
    return Math.floor(f * mult) / mult;
}

function renderFormula(formula) {
    // Render formula's base
    $("#base_place").text(formula["base"]);

    // Rendering volumes for each colorant: value * volume
    var col_vol = $('#coloring_volume').val();
    var prices = $('body').data('price');
    var final_price = 0;
    for(k in formula["formula"]) {
        var col_cons = formula["formula"][k];   // Formula colorant's consumption
        var actual_cons = col_cons * col_vol;   // Actual consumption: desired volume * formula consumption
        $("#"+k).text(formatFloat(actual_cons, 4));

        final_price += actual_cons * prices[k];
    }

    var fee = $('body').data('fee');
    if(fee) {
        final_price *= 1 + fee / 100;
    }
    var user_fee = $('body').data('user_fee');
    if(user_fee) {
        final_price *= 1 + user_fee / 100;
    }

    $('#coloring_price').text(formatFloat(final_price, 2));
}

function renderCurrentPaint() {
    var cur = $(this).find('option:selected');

    // Render current paint's packing name
    var pack_name = cur.data('pack') == 1 ? 'л' : 'кг';
    $('#pack_place').text(pack_name);

    // Rendering the formula for current paint and palette
    if(cur.val()) {
        var url = colorFormulasUrl() + '?paletteId=' + $('#palette_select').val() + '&paintId=' + cur.val();
        $.ajax({
            url: url,
            success:
            function(formula) {
                cur.data('formula', formula);
                renderFormula(formula);
            }
        });
    }
}

function renderCurrentPalette() {
    var cur = $(this).find('option:selected');

    // Render palette's color field
    if(cur.data('rgb') != null) {
        if(! $('#color_place').length) {
            $('<tr>').append($('<td>', {id: 'color_place', class: 'param_val'})).
                insertAfter($(this).parent().parent());
        }
        $('#color_place').css('background', '#'+cur.data('rgb'));
        $('#color_place').text('');
    } else {
        $('#color_place').remove();
    }

    // Save previously selected paint and reselect id after palette is changed
    var prevPaint = $("#paint_select option:checked").val();

    $('#paint_select').empty();
    $("#paint_select").trigger("chosen:updated");

    // Building select box with paints for current palette
    $.ajax({
        url:
            paintsForPaletteUrl(cur.val()),
        success:
            function(paints) {
                $(paints).each( function(i, paint) {
                    var opt = $('<option>', { value: paint.id, text: paint.name });
                    opt.data('pack', paint.packing.id);
                    opt.appendTo('#paint_select');

                    if(paint.id == prevPaint) {
                        $("#paint_select").val(paint.id);
                    }
                });
                $("#paint_select").trigger("chosen:updated");
                $("#paint_select").change();
            }
    });
}

function renderPalettes(event, paletteNick, paletteShortName) {
    $('#palette-type').text(paletteShortName);
    $('.tab-links').removeClass('active');
    $('#pt' + paletteNick).addClass('active');

    $('#palette_select').empty();
    $("#palette_select").trigger("chosen:updated");

    $.ajax({
        url: '/api/palettes?type=' + paletteNick,
        success:
            function(palettes) {
                $(palettes).each( function(i, palette) {
                    // Adding palette to the box.
                    // If a color is provided for the palette, save it to option's data
                    var opt = $('<option>', { value: palette.id, text: palette.name });
                    if(palette.rgb != null) {
                        opt.data('rgb', palette.rgb);
                    }
                    opt.appendTo('#palette_select');
                });
                $("#palette_select").trigger("chosen:updated");
                $("#palette_select").change();
            }
    });
}

function refreshFormula() {
    var curPaint = $('#paint_select').find('option:selected');
    var formula = curPaint.data('formula');
    renderFormula(formula);
}

function volumeChanged() {
    var $volume = parseFloat($(this).val().replace(",", "."));
    if(isNaN($volume) || $volume <= 0)
        $volume = 1;

    $(this).val($volume);

    refreshFormula();
}

function feeChanged() {
    $('body').data('fee', $(this).val());
    refreshFormula();
}

function renderNavigation(user_info) {
    $('#login_place').text(user_info.email);

    var links = [];
    // We could just loop through user's permissions array but we care about the order of links
    $.map(['REGULAR', 'FACADE', 'ADMIN'], function(page) {
        if(page != currentPage()) {
            // Are we permitted?
            var matched = $.grep(user_info.roles, function(role) {
                return role['name'] == page;
            });
            if(matched.length > 0) {
                links.push(page);
            }
        }
    });

    if(links.length > 0) {
        $('<div>', {id: 'nav_links'}).appendTo('#nav');

        var page_link = {'REGULAR' : ['Основные краски',   'index.html'   ],
                         'FACADE'  : ['Фасадные краски',   'facade.html'  ],
                         'ADMIN'   : ['Настройки системы', 'settings.html']
                        };

        $.map(links, function(link) {
            $('<a>', { text: page_link[link][0],
                       href: page_link[link][1]
                     }
             ).appendTo('#nav_links');
            $('#nav_links').append(' ');
        });
    }
}

$(document).ready(function() {
    $('#palette_select').chosen();
    $('#palette_select').change(renderCurrentPalette);

    $('#paint_select').chosen();
    $('#paint_select').change(renderCurrentPaint);

    $('#coloring_volume').change(volumeChanged);

    $('#coloring_fee').change(feeChanged);
    $("#set_fee").click(function() {
        $("#fee_block").remove();
    });

    $.ajax({
        url: '/api/user_info',
        success: function(user_info) {
            renderNavigation(user_info);
            $('#login_place').text(user_info.email);
            $('body').data('user_fee', user_info.profile.fee);
        }
    });

    $.ajax({
        url: paintPriceUrl(),
        success: function(paint_price) {
            $('body').data('price', paint_price);
        }
    });

    $.ajax({
        url: '/api/palette_types',
        success:
            function(data) {
                palettes = palettesPreprocess(data);
                $(palettes).each(function(i, p) {
                    $('<button>', {
                        id: 'pt' + p.nick,
                        class: 'tab-links',
                        text: p.shortName,
                        onClick: "renderPalettes(event, '" + p.nick +"', '" + p.shortName + "')"
                    }).appendTo('#palettes');
                });
                renderPalettes(event, data[0].nick, data[0].shortName);
            }
    });
});
