$(document).ready(function(){
    initDatepicker();
});

function initDatepicker(){
    let today = new Date().toISOString().slice(0, 16);
    let day = today.split("T")[0];
    document.getElementById("termin-startDate").value = day;
    document.getElementById("termin-startTime").value = "12:00";
    document.getElementById("termin-startDate").min = day;
    document.getElementById("termin-endDate").min = day;
    document.getElementById("termin-endTime").value = "12:00";
    document.getElementById("termin-endDate").value = addOneMonth(today);
}

function addOneMonth(dateString){
    let date = new Date(dateString);
    date.setMonth(date.getMonth()+1);
    return date.toISOString().split("T")[0];
}
