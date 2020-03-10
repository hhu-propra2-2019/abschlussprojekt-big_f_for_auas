let count = 1;

$(document).ready(addChoiceOption());

function addChoiceOption(){
    $('#terminChoiceOptions .form-group').last().append(`<div class="form-group">
                <input class="form-control" type="date">
            </div>`);
    $('#scheduleOptionCount').text(count);
}