let ctx = document.getElementById("resultChart").getContext('2d');
let resultChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['08.03.2020, 10:00 Uhr', '09.03.2020, 12:00 Uhr', '10.03.2020, 11:00 Uhr'],
        datasets: [{
            data: [12, 13, 14],
            label: 'Anzahl Stimmen',
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(255, 99, 132, 0.2)',
                'rgba(255, 99, 132, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 99, 132, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});

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