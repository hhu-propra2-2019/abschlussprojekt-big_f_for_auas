$(document).ready(function() {
    $('#collapsePriorities').collapse('show');
    if($('#publication-accessibility').hasClass("checked")){
        setOpen(true);
    } else {
        setOpen(false);
    }
    });


function toggleGroup(elem){
    $(elem).siblings('input[type="checkbox"]').not(this).prop('checked', false);
}

function showPriorities(){
    if( $("#multiple-choice:checked").val() === "false" ){
        $('#collapsePriorities').collapse('show');
    }
    else{
        $('#collapsePriorities').collapse('hide');
    }
}

function showGroups() {
    if($('#publication-accessibility').is(":checked")) {
        $('#groupSection').collapse('hide');
    }
    else {
        $('#groupSection').collapse('show');
    }
}

function setOpen(state) {
    $('#publication-accessibility').prop("checked", state);
}

function collapseSection(id, icon){
    let iconSelector = $('#'+icon);
    $('#'+id).collapse('toggle');
    if ( iconSelector.hasClass('fa-caret-down') ) {
        iconSelector.removeClass('fa-caret-down');
        iconSelector.addClass('fa-caret-right');
    }
    else {
        iconSelector.removeClass('fa-caret-right');
        iconSelector.addClass('fa-caret-down');
    }
}
