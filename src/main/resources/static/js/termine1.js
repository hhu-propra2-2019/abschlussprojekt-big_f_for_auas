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

function collapseSection(id, icon){
    let iconSelector = $('#'+icon);
    $('#'+id).collapse('toggle');
    if ( iconSelector.hasClass('fa-caret-down') ) {
        iconSelector.removeClass('fa-caret-down');
        iconSelector.addClass('fa-caret-up');
    }
    else {
        iconSelector.removeClass('fa-caret-up');
        iconSelector.addClass('fa-caret-down');
    }
}
