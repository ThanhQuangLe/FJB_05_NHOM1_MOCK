


/*Xử lý nút update*/
document.getElementById("UpdateResult").addEventListener("click", function() {
	let checkboxes = document.querySelectorAll("table tbody input[type='checkbox']");

	var checkedCount = 0;

	checkboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			checkedCount++;
		}
	});

	if (checkedCount === 1) {
		checkboxes.forEach(function(checkbox) {
			if (checkbox.checked) {
				var id = checkbox.value;

				window.location.href = "/vaccineResult-update/" + id;
			} else {
				checkbox.checked = false;
			}
		});
	} else if (checkedCount === 0) {
		alert("Chưa chọn đối tượng update");
	} else {
		alert("Chỉ được chọn 1 checkbox.");
		checkboxes.forEach(function(checkbox) {
			if (checkbox.checked) {
				checkbox.checked = false;
			}
		});
	}
});

/*Xử lý nút delete*/
document.getElementById("DeleteResult").addEventListener("click", function() {
	let checkboxes = document.querySelectorAll("table tbody input[type='checkbox']:checked");

	var idList = [];

	checkboxes.forEach(function(checkbox) {
		var id = checkbox.value;
		idList.push(id);
	});

	// Kiểm tra xem có ít nhất một checkbox được chọn
	if (idList.length > 0) {
		if (confirm("Do you want to delete!")) {
			$.ajax({
				url: "http://localhost:8080/vaccineResult-delete",
				type: "POST",
				data: JSON.stringify(idList),
				contentType: "application/json",
				success: function(response) {
					alert(response + " records have bean deleted!")
					window.location.href = "http://localhost:8080/vaccineResult-list";
				}
			});
		}

	} else {
		alert("Chưa chọn đối tượng delete")
	}


});

/*Xử lý nút search */
var searchInput = document.getElementById("searchTable");

searchInput.addEventListener("keypress", function(event) {
	if (event.key === "Enter") {
		pagging();
	}
});


//xử lý khi chọn pagesize
let selectElement = document.getElementById('entriesDropdown');
selectElement.addEventListener('change', function() {
	pagging();
});
//xử lý khi chọn trang

window.onload = function() {
	let noti = document.getElementById("notification").value;
	if(noti!==""){
		alert(noti);
	}
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
					if (links[j].innerText == pageNumData +1) {
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



			//xử lý phân trang

			let hasPreviousPage = ``;
			let hasNextPage = ``;
			let pagging = ``;

			if (hasPrevious === true) {
				hasPreviousPage = ` 
			<li class="page-item">
               <a class="page-link" href="http://localhost:8080/vaccineResult-list" data-value="${pageNumber}">Previous</a>
            </li>`;
			}

			if (hasNext === true) {
				hasNextPage = ` 
			<li class="page-item">
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
				if (link.innerText == pageNumber+1) {
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
                    <th scope="row" class="text-center"><input type="checkbox" style="transform: scale(1.2)" value="${row.id}"></th>
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
			
			var maxRecord = (pageNumber+1)*pageSize;
			if(maxRecord>total){
				maxRecord=total;
			}
			var inforUpdate = `
Showing ${(pageNumber+1)*pageSize-pageSize+1} to ${maxRecord} of ${total} entries`;
			
			document.getElementById("infor").innerText=inforUpdate;
			
			addActive();
		},
		error: function(xhr, textStatus, error) {
			console.log(error);
		}
	});
}









