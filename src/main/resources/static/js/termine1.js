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


//TODO: add chart or delete methods
/* CHART */

function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(data);
    });
    chart.update();
}

function removeData(chart) {
    chart.data.labels.pop();
    chart.data.datasets.forEach((dataset) => {
        dataset.data.pop();
    });
    chart.update();
}

function updateTitle(chart, title) {
    chart.options.title.text = title;
    chart.update();
}