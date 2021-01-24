function renderPaintsSettings() {
    $('.tab-links').removeClass('active');
    $('#settPaints').addClass('active');

    $('#current_settings').empty();
    $('#current_settings').append( $('#paints_placeholder').html() );

    $.ajax({
        url: '/api/paints',
        success:
            function(paints) {
                $(paints).each( function(i, paint) {
                    $('<option>', { value: paint.id, text: paint.name })
                        .appendTo('#paints');
                });
            }
    });

    $('#volume').on('change', function() {
        var volume = $(this).val();
        if(volume <= 0) {
            $('#upload_status').text('Неправильный объем: ' + volume);
        } else {
            $('#upload_status').text('');
        }
    });

    $('#upload_button').on('click', function() {
        var volume = $('#volume').val();
        $.ajax({
            url: '/api/formula_upload?paintId=' + $('#paints').val() + '&volume=' + volume,
            type: 'POST',
            data: new FormData($('#upload_form')[0]),
            cache: false,
            contentType: false,
            processData: false,
            async: false
        });
    });
}

function clearEditUserWindow() {
    $('#edit_user_caption').text('Новый пользователь');
    $('#edit_user_user_email').text('').attr('disabled', false);
    $('#edit_user_role_i').prop('checked', false);
    $('#edit_user_role_f').prop('checked', false);
    $('#edit_user_role_a').prop('checked', false);
    $('#edit_user_paint_prices').addClass('hidden');
    $('#edit_user_paint_prices_facade').addClass('hidden');
}

function displayUserInEditUserWindow(user) {
    $('#edit_user_caption').text('Настройки пользователя ' + user.email);
    $('#edit_user_user_email').text(user.email).attr('disabled', true);
    // TODO
}

function addEditUserWindow(user) {
    // Adding Open/Close handlers
    $('#edit_user_window').css('display', 'block');
    $('#edit_user_close').click(function() {
        $('#edit_user_window').css('display', 'none');
    });
    // Reset common edit/new fields
    $('#edit_user_password').text('');
    $('#edit_user_status').addClass('hidden');
    $('#edit_user_errors').addClass('hidden');

    // Getting data
    // Paint prices
    $.ajax({
        url: '/api/paint_prices',
        success:
            function(prices) {
                prices.forEach( function(price) {
                    $('<option>', { value: price.id, text: price.name })
                        .appendTo('#edit_user_paint_prices_select');
                });
            }
    });
    $.ajax({
        url: '/api/paint_prices_facade',
        success:
            function(prices) {
                prices.forEach( function(price) {
                    $('<option>', { value: price.id, text: price.name })
                        .appendTo('#edit_user_paint_prices_facade_select');
                });
            }
    });

    // Setting initial values
    if(user == null) {
        clearEditUserWindow();
    }
    else {
        displayUserInEditUserWindow(user);
    }

    // Setting handlers for common fields
    $('#edit_user_role_i').click(function() {
        if($('#edit_user_role_i').is(':checked')) {
            $('#edit_user_paint_prices').removeClass('hidden');
        }
        else {
            $('#edit_user_paint_prices').addClass('hidden');
        }
    });

    $('#edit_user_role_f').click(function() {
        if($('#edit_user_role_f').is(':checked')) {
            $('#edit_user_paint_prices_facade').removeClass('hidden');
        }
        else {
            $('#edit_user_paint_prices_facade').addClass('hidden');
        }
    });

    // Gather entered parameters and submit
    $('#edit_user_submit').click(function() {
        new_user = {};
        if(user != null) {
            new_user['id'] = user.id;
        }
        else {
            email = $('#edit_user_user_email').val().trim();
            if(email.length == 0 || !email.includes('@')) {
                $('#edit_user_errors').text('Неправильный E-mail!');
                $('#edit_user_errors').removeClass('hidden')
                return;
            }
            new_user['email'] = email;
        }
        new_user['roles'] = [];
        new_user['profile'] = {};
        if($('#edit_user_role_i').is(':checked')) {
            new_user['roles'].push({id: 2, name: "REGULAR"}); // TODO: from AJAX
            new_user['profile']['paintPrice'] = {id: $('#edit_user_paint_prices_select').val()};
        }
        if($('#edit_user_role_f').is(':checked')) {
            new_user['roles'].push({id: 3, name: "FACADE"}); // TODO: from AJAX
            new_user['profile']['paintPriceFacade'] = {id: $('#edit_user_paint_prices_select').val()};
        }
        if($('#edit_user_role_a').is(':checked')) {
            new_user['roles'].push({id: 1, name: "ADMIN"}); // TODO: from AJAX
        }
        password = $('#edit_user_password').val().trim();
        if(password.length > 0) {
            new_user['password'] = password;
        }
        new_user['profile']['fee'] = $('#edit_user_fee').val();

        $.ajax({
            url: '/api/users',
            type: 'POST',
            data: JSON.stringify(new_user),
            contentType: "application/json; charset=utf-8",
            dataType:"json",
            cache: false,
            async: false,

            success: function() {
                $('#edit_user_status').text('Пользователь создан');
                $('#edit_user_status').removeClass('hidden');
            },
            error: function(xhr) {
                if(xhr.responseJSON.message.includes('already exists')) {
                    $('#edit_user_errors').text('Пользователь уже существует');
                }
                else {
                    $('#edit_user_errors').text(xhr.responseJSON.message);
                }
                $('#edit_user_errors').removeClass('hidden');
            }
        });
    });
}

function renderUsersSettings() {
    $('.tab-links').removeClass('active');
    $('#settUsers').addClass('active');

    $('#current_settings').empty();
    $('#current_settings').append( $('#users_placeholder').html() );

    $('#new_user_button').click(function() { addEditUserWindow(null); });

    $.ajax({
        url: '/api/users',
        success:
            function(users) {
                $(users).each( function(i, user) {
                    row = $('<tr>').appendTo('#users');
                    mail_td = $('<td>', {class: 'bordered'}).appendTo(row);
                    $('<button>', {text: user.email,
                                   class: 'user-links'})
// TODO                      .click(function() { addEditUserWindow(user); })
                        .appendTo(mail_td);

                    roles = $('<td>', {class: 'bordered'}).appendTo(row);
                    user.roles.forEach(function(role) {
                        txt = null;
                        comment = null;
                        switch(role.name) {
                        case 'REGULAR':
                            txt = 'И';
                            comment = 'Интерьерные краски';
                            break;
                        case 'FACADE':
                            txt = 'Ф';
                            comment = 'Фасадные краски';
                            break;
                        case 'ADMIN':
                            txt = 'А';
                            comment = 'Администратор';
                            break;
                        }
                        if(txt) {
                            $('<span>', {text: txt, title: comment, class: 'spaced'})
                                .appendTo(roles);
                        }
                    });

                    prices = $('<td>', {class: 'bordered'}).appendTo(row);
                    if(user.profile.paintPrice != null) {
                        $('<div>', {text: 'И: ' + user.profile.paintPrice.name}).appendTo(prices);
                    }
                    if(user.profile.paintPriceFacade != null) {
                        $('<div>', {text: 'Ф: ' + user.profile.paintPriceFacade.name}).appendTo(prices);
                    }
                    $('<td>', {class: 'bordered', text: user.profile.fee}).appendTo(row);
                });
            }
    });
}

function renderSettings(params) {
    $.each(params['pages'], function(key, value) {
        $('<button>', {
            id: 'sett' + key,
            class: 'tab-links',
            text: value['name'],
            onClick: value['render']
        }).appendTo('#settings_pages')
    });

    $('#sett' + params.deflt).click();
}

$(document).ready(function() {
    renderSettings({
        pages: {
            'Users':  { name: 'Пользователи', render: "renderUsersSettings()" },
            'Paints': { name: 'Краски и формулы', render: "renderPaintsSettings()" }
        },
        deflt: 'Users'
    });
});
                 
