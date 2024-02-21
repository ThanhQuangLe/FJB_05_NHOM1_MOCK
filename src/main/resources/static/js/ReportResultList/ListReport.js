/*Xử lý nút search */
var searchInput = document.getElementById("filterButton");

searchInput.addEventListener("click", function(event) {
	pagging();
});

/*Xử lý nút reset */
var searchInput = document.getElementById("resetButton");
searchInput.addEventListener("click", function(event) {
	document.getElementById("InjectionDate").value = "";
	document.getElementById("nextInjectionDate").value = "";
	document.getElementById("prevention").value = "";
	document.getElementById("vaccineName").value = "";
});


//Xử lý khi chọn năm hiển thị biểu đồ
let chartSelect = document.getElementById("selectYear");
chartSelect.addEventListener("change", function(event) {
	let year = chartSelect.value;
	$.ajax({
		url: '/VaccineResult-ReportYear',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(year),
		success: function(data) {
			var listByYear = data.listByYear;
			chartShow(listByYear);
		}
	});
});



//xử lý biểu đồ
let myBarChart = null;

function chartShow(listByYear) {
	if (!Array.isArray(listByYear) || listByYear.length === 0) {
		console.log("List is empty or not an array");
		return;
	}

	if (!myBarChart) {
		// Tạo biểu đồ ban đầu nếu chưa tồn tại
		const ctx = document.getElementById('myBarChart').getContext('2d');

		const data = {
			labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
			datasets: [{
				data: listByYear,
				backgroundColor: ['rgba(127, 221, 233, 1)'],
			}]
		};

		const options = {
			scales: {
				y: {
					beginAtZero: true,
				},
			},
			plugins: {
				legend: {
					display: false,
				},
			},
		};

		myBarChart = new Chart(ctx, {
			type: 'bar',
			data: data,
			options: options,
		});
	} else {
		// Cập nhật dữ liệu của biểu đồ nếu đã tồn tại
		myBarChart.data.datasets[0].data = listByYear;
		myBarChart.update();
	}
}

//xử lý khi chọn trang

window.onload = function() {
	//let noti = document.getElementById("notification").value;
	//	if (noti !== "") {
	//		alert(noti);
	//	}
	document.getElementById("chart").style.display = "none";
	document.getElementById("chart2").style.display = "none";
	let checkList = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
	chartShow(checkList);
	addActive();
}

//Xử lý display type
var radio1 = document.getElementById("radio1");
radio1.addEventListener("change", function() {
	if (radio1.checked) {
		document.getElementById("report1").style.display = "flex";
		document.getElementById("report2").style.display = "table-header-group";
		document.getElementById("chart").style.display = "none";
		document.getElementById("chart2").style.display = "none";
	}
});

var radio2 = document.getElementById("radio2");
radio2.addEventListener("change", function() {
	if (radio2.checked) {
		document.getElementById("chart").style.display = "block";
		document.getElementById("chart2").style.display = "block";
		document.getElementById("report1").style.display = "none";
		document.getElementById("report2").style.display = "none";
	}
});


function addActive() {
	var paggingHTML = document.querySelector(".pagination");
	var links = paggingHTML.querySelectorAll('li > a');
	let pageNumData = parseInt(document.querySelector(".pagination a.page-link.active").innerText);

	for (var i = 0; i < links.length; i++) {
		var link = links[i];
		link.addEventListener('click', function(event) {

			event.preventDefault();
			for (var j = 0; j < links.length; j++) {
				links[j].classList.remove('active');
			}
			this.classList.add('active');

			if (this.innerText == ">>") {
				this.classList.remove('active');
				for (var j = 1; j < links.length - 1; j++) {
					if (links[j].innerText == pageNumData + 1) {
						links[j].classList.add('active');
					}
				}
			}

			if (this.innerText == "<<") {
				this.classList.remove('active');
				for (var j = 1; j < links.length - 1; j++) {
					if (links[j].innerText == pageNumData - 1) {
						links[j].classList.add('active');
					}
				}
			}
			pagging();
		});
	}
}

/* xử lý phân trang*/
function pagging() {
	let pageNumData = document.querySelector(".pagination a.page-link.active").innerText;
	let paggingHTML = document.querySelector(".pagination");

	let injectionDate = document.getElementById("InjectionDate").value;
	let nextInjectionDate = document.getElementById("nextInjectionDate").value;
	let prevention = document.getElementById("prevention").value;
	let vaccineName = document.getElementById("vaccineName").value;
	let year = document.getElementById("selectYear").value;


	var dateFormat = "DD/MM/YYYY";

	var parsedDateInjection = moment(injectionDate, dateFormat);
	var parsedNextDateInjection = moment(nextInjectionDate, dateFormat);
	if (injectionDate !== "") {
		if (!/^\d{2}\/\d{2}\/\d{4}$/.test(injectionDate) || !parsedDateInjection.isValid()) {
			alert("Định dạng ngày tháng phải ở dạng dd/mm/yyyy");
			return
		}
	}
	if (nextInjectionDate !== "") {
		if (!/^\d{2}\/\d{2}\/\d{4}$/.test(nextInjectionDate) || !parsedNextDateInjection.isValid()) {
			alert("Định dạng ngày tháng phải ở dạng dd/mm/yyyy");
			return
		}
	}








	let html = ``;

	$.ajax({
		url: '/VaccineResult-Report',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			"injectionDate": injectionDate,
			"nextInjectionDate": nextInjectionDate,
			"vaccineName": vaccineName,
			"prevention": prevention,
			"pageNumData": pageNumData,
			"year": year,
		}),
		success: function(data) {
			var list = data.list;
			var injectionResultListDTOs = data.injectionResultListDTOs;
			var pageNumber = data.pageNumber;
			var hasPrevious = data.hasPrevious;
			var hasNext = data.hasNext;
			var pageSize = data.pageSize;
			var total = data.total;
			var listByYear = data.listByYear;

			chartShow(listByYear);


			if (pageNumber === -1 && injectionResultListDTOs.length === 0 || list.length === 0) {
				alert("No records have bean found");
				window.location.href = "http://localhost:8080/VaccineResult-Report";
			}
			if (pageNumber === -1) { pageNumber = 0 }

			//xử lý phân trang

			let hasPreviousPage = ``;
			let hasNextPage = ``;
			let pagging = ``;

			if (hasPrevious === true) {
				hasPreviousPage = ` <li class="page-item">
               							<a class="page-link" href="aloha" data-value="${pageNumber}">&lt;&lt;</a>
            						</li>`;
			}

			if (hasNext === true) {
				hasNextPage = `<li class="page-item">
             					  <a class="page-link" href="a" data-value="${pageNumber + 1}">&gt;&gt;</a>
            					</li>`;
			}

			for (var i = 0; i < list.length; i++) {
				pagging += `<li class="page-item">
                           	 	<a class="page-link" href="http://localhost:8080/vaccineResult-list" >${list[i]}</a>
                        	</li>`;
			}

			paggingHTML.innerHTML = hasPreviousPage + pagging + hasNextPage;
			let links = paggingHTML.querySelectorAll('li > a');
			for (var i = 0; i < links.length; i++) {
				var link = links[i];
				if (link.innerText == pageNumber + 1) {
					link.classList.add('active');
				}
			}

			//xử lý dữ liệu in ra
			if (injectionResultListDTOs === 0) {
				alert('No data found!');
				window.location.href = "http://localhost:8080/vaccineResult-list";
			} else {
				let template = '';
				let index = (pageNumber + 1) * pageSize - pageSize + 1;
				for (let i = 0; i < injectionResultListDTOs.length; i++) {
					let row = injectionResultListDTOs[i];
					template += `<tr>
								<th>${index}</th>
								<td>${row.vaccineName}</td>
								<td>${row.prevention}</td>
								<td>${row.place}</td>
								<td>${row.dateOfInjection}</td>
								<td>${row.numberOfInjection}</td>
							</tr>`;
					index += 1;
				}
				document.querySelector("tbody").innerHTML = template;
			}

			//xử lý số phần tử hiển thị


			var maxRecord = (pageNumber + 1) * pageSize;
			if (maxRecord > total) {
				maxRecord = total;
			}
			var inforUpdate = `Showing ${(pageNumber + 1) * pageSize - pageSize + 1} to ${maxRecord} of ${total} entries`;

			document.getElementById("infor").innerText = inforUpdate;

			addActive();
		},
		error: function(xhr, textStatus, error) {
			console.log(error);
		}
	});
}














