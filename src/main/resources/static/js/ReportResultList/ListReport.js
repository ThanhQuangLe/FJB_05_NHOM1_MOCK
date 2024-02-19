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
	addActive();
}

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
	let pageSizeData = document.getElementById("entriesDropdown").value;
	let searchData = document.getElementById("searchTable").value;
	let paggingHTML = document.querySelector(".pagination");
	let html = ``;

	$.ajax({
		url: '/vaccineResult-paging',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			"pageNum": pageNumData,
			"pageSize": pageSizeData,
			"input": searchData
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
				window.location.href = "http://localhost:8080/vaccineResult-list";
			}
			if (pageNumber === -1) {pageNumber = 0}

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
				for (let i = 0; i < injectionResultListDTOs.length; i++) {
					let row = injectionResultListDTOs[i];
					template += `
                <tr>
                    <td scope="row" class="text-center mb-4"><input type="checkbox"  value="${row.id}"></td>
                    <td>${row.customer}</td>
                    <td>${row.vaccineName}</td>
                    <td>${row.prevention}</td>
                    <td>${row.numberOfInjection}</td>
                    <td>${row.injectionDate}</td>
                    <td>${row.nextInjectionDate}</td>
                </tr>
            `;
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

const ctx = document.getElementById('myBarChart').getContext('2d');

// Dữ liệu của biểu đồ
const data = {
    labels: ['January', 'February', 'March','April','May','June','July','August','September','October','November','December',],
    datasets: [{

        data: [10, 20, 30,10, 20, 30,10, 20, 30,10, 20], // Giá trị của cột tương ứng với mỗi nhãn
        backgroundColor: [
            'rgba(127, 221, 233, 1)', // Màu nền của cột 1
           
        ]
    }]
};

// Cấu hình biểu đồ
const options = {
    scales: {
        y: {
            beginAtZero: true
        }
    },
    plugins:{
      legend: {
        display: false // Ẩn hộp mô tả (legend)
    }
    }
};

// Tạo biểu đồ dạng bar
const myBarChart = new Chart(ctx, {
    type: 'bar',
    data: data,
    options: options
});










