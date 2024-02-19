/*Xử lý nút search */
var searchInput = document.getElementById("filterButton");

searchInput.addEventListener("click", function(event) {
	pagging();
});

//xử lý khi chọn trang

window.onload = function() {
	//let noti = document.getElementById("notification").value;
	//	if (noti !== "") {
	//		alert(noti);
	//	}
	document.getElementById("chart").style.display = "none";
	document.getElementById("chart2").style.display = "none";
	chartShow()
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

			if (this.innerText == "Next") {
				this.classList.remove('active');
				for (var j = 1; j < links.length - 1; j++) {
					if (links[j].innerText == pageNumData + 1) {
						links[j].classList.add('active');
					}
				}
			}

			if (this.innerText == "Previous") {
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

			if (pageNumber === -1 && injectionResultListDTOs.length === 0) {
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
               							<a class="page-link" href="http://localhost:8080/vaccineResult-list" data-value="${pageNumber}">Previous</a>
            						</li>`;
			}

			if (hasNext === true) {
				hasNextPage = `<li class="page-item">
             					  <a class="page-link" href="http://localhost:8080/vaccineResult-list" data-value="${pageNumber + 1}">Next</a>
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
								<th th:text="${index}"></th>
								<td th:text="${row.vaccineName}"></td>
								<td th:text="${row.prevention}"></td>
								<td th:text="${row.place}"></td>
								<td th:text="${row.dateOfInjection}"></td>
								<td th:text="${row.numberOfInjection}"></td>
							</tr>`;
							index+=1;
				}
				document.querySelector("tbody").innerHTML = template;
			}

			//xử lý số phần tử hiển thị
			let pageSizeShow = document.getElementById("entriesDropdown");
			var options = pageSizeShow.options;

			for (var i = 0; i < options.length; i++) {
				var option = options[i];

				if (option.value == pageSize) {
					option.selected = true;
				}
			}

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


//xử lý biểu đồ
function chartShow() {

	const ctx = document.getElementById('myBarChart').getContext('2d');

	const data = {
		labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December',],
		datasets: [{

			data: [10, 20, 30, 10, 20, 30, 10, 20, 30, 10, 20],
			backgroundColor: [
				'rgba(127, 221, 233, 1)',

			]
		}]
	};

	const options = {
		scales: {
			y: {
				beginAtZero: true
			}
		},
		plugins: {
			legend: {
				display: false
			}
		}
	};

	const myBarChart = new Chart(ctx, {
		type: 'bar',
		data: data,
		options: options
	});

}








