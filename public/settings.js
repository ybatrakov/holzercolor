$(document).ready(function() {
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
});
                 
