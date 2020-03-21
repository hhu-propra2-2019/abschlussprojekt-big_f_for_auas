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