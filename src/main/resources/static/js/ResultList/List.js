
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
		if(confirm("Do you want to delete!")){
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
		
	}else{
		alert("Chưa chọn đối tượng delete")
	}
	

});

/*Xử lý nút search */
var searchInput = document.getElementById("searchTable");

searchInput.addEventListener("keypress", function(event) {
	if (event.key === "Enter") {
		var input = searchInput.value;
		searchResults(input);
	}
});

function searchResults(input) {
	$.ajax({
		url: '/vaccineResult-search',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(input),
		success: function(data) {
			if (data.length === 0) {
				alert('Không có kết quả');
				window.location.href = "http://localhost:8080/vaccineResult-list";
			} else {
				let template = '';
				for (let i = 0; i < data.length; i++) {
					let row = data[i];
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
		}
	});
}

/* xử lý phân trang*/

function pagging(){
	let pageNumData = document.getElementById("entriesDropdown").value;
	let pageSizeData = document.querySelector("a .page-link.active").innerText;
	$.ajax({
    url: '/vaccineResult-paging',
    type: 'POST',
    dataType: 'json',
    data: {
        pageNum: pageNumData, 
        pageSize: pageSizeData
    },
    success: function(data) {
        // Xử lý dữ liệu nhận được từ server
        console.log(data); // Hiển thị dữ liệu trong console
        // Tiếp tục xử lý dữ liệu theo nhu cầu của bạn
    },
    error: function(xhr, textStatus, error) {
        // Xử lý lỗi nếu có
        console.log(error); // Hiển thị lỗi trong console
    }
});
}







