function showPriorities(){
    if( $("input[name=choice-amount]:checked").val() === "multiple-choice" ){
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