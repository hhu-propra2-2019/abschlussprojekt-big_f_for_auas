function toggleGroup(elem){
    $(elem).siblings('input[type="checkbox"]').not(this).prop('checked', false);
}

function showPriorities(){
    if( $("input[name=single-choice]:checked").val() === "false" ){
        $('#collapsePriorities').collapse('show');
    }
    else{
        $('#collapsePriorities').collapse('hide');
    }
}
function showMutable(){
    if( $("input[name=choice-mutable]").is(':checked') ){
        $("input[name=choice-addNew]").prop('disabled', false)
    }
    else{
        $("input[name=choice-addNew]").prop('checked', false).prop('disabled', true);
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
