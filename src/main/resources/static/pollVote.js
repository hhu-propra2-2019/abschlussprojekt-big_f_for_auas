function toggleGroup(elem){
    $(elem).siblings('input[type="checkbox"]').not(this).prop('checked', false);
}
