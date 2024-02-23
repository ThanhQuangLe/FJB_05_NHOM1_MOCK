window.onload = function () {
    showReport();
}

// Lấy năm hiện tại
var currentYear = new Date().getFullYear();

var yearSelect = document.getElementById("yearSelect");

// Tạo các option từ năm 2010 đến năm hiện tại
for (var year = 2010; year <= currentYear; year++) {
    var option = document.createElement("option");
    option.value = year;
    option.text = year;
    yearSelect.add(option);
}


document.getElementById("reset").onclick = function () {
    showReport();
    document.getElementById("beginDate").value = '';
    document.getElementById("endDate").value = '';
    document.getElementById("vaccineType").value = '';
    document.getElementById("origin").value = '';
}

document.getElementById("filter").onclick = function () {
    showReport();
}

document.getElementById("report").onclick = function () {
    showReport();
}

document.getElementById("chart").onclick = function () {
    showChart();
}


let chart = document.getElementById('chart-vaccine');

function showReport() {
    document.getElementById("reportInput").classList.remove("d-none");
    document.getElementById('chartInput').style.display = 'none';
    document.getElementById("reportResult").style.display = 'block';

    chart.style.display = 'none';

    let beginDate = document.getElementById("beginDate");
    let endDate = document.getElementById("endDate");
    let vaccineType = document.getElementById("vaccineType");
    let origin = document.getElementById("origin");

    if (document.getElementById("nodata")) {
        if (beginDate.value !== '' || endDate.value !== '' || vaccineType.value !== '' || origin.value !== '') {
            document.getElementById("nodata").style.display = 'block';
        } else {
            document.getElementById("nodata").style.display = 'none';
        }
    }
}

function showChart() {
    document.getElementById("reportInput").classList.add("d-none");
    document.getElementById('chartInput').style.display = 'block';
    document.getElementById("reportResult").style.display = 'none';

    chart.style.display = 'block';
    if (yearSelect.value == 0) {
        chart.style.display = 'none';
    }
}


function showReportVaccine(element) {
    showReport();

    let pageNumber = element.getAttribute("value");
    document.getElementById("pageNumber").value = pageNumber;
    document.getElementById("searchForm").submit();
}


function showChartResult() {
    let year = document.getElementById("yearSelect").value;
    $.ajax({
        url: "http://localhost:8080/reportVaccine",
        method: 'post',
        contentType: 'application/json',
        data: JSON.stringify(year),
        success: function (response) {
            console.log(response);

            const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
            const values = response;

            // Vẽ biểu đồ sử dụng Chart.js

            const canvas = document.getElementById('monthlyChart');
            const ctx = canvas.getContext('2d');

            if (window.myBarChart) {
                // Phá hủy biểu đồ cũ
                window.myBarChart.destroy();
            }

            window.myBarChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: months,
                    datasets: [{
                        label: 'Monthly Data',
                        data: values,
                        backgroundColor: 'rgba(75, 192, 192, 0.7)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    },
                    barPercentage: 0.7,
                    plugins: {
                        legend: {
                            display: false
                        }
                    }
                }
            });

        }, error: function (error) {

            alert("Database connect error");
        }
    })
}

//khi thay đổi năm
yearSelect.addEventListener("change", function () {
    showChart()
    showChartResult();
});