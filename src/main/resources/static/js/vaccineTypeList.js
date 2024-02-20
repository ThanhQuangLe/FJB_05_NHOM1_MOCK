//xử lý khi ấn vào nút Make In-active
document.getElementById("makeinactive").addEventListener("click", function () {

    let id;
    let found = false;

    let checkList = document.querySelectorAll(".checkUp");

    checkList.forEach(function (c) {
        if (!found && c.checked) {

            //tìm dom của status
            let statusCell = c.closest("tr").querySelector(".status");

            if ("In-Active" == statusCell.textContent) {
                alert("Invalid data - please recheck your selects")
                found = true;
            } else {
                id = c.getAttribute('data-id');
                found = true;
            }
        }
    });

    if (id != null) {

        let confirmation = confirm("Are you sure you want to make it inactive?");

        if (confirmation) {
            //hực hiện thay đổi khi  xác nhận
            $.ajax({
                url: "http://localhost:8080/vaccine-type-updatestatus?id=" + `${id}`,
                method: 'get',
                contentType: 'application/json',
                success: function (response) {
                    alert("Inactive status updated");
                    // Điều hướng về trang vaccine-list
                    window.location.href = "http://localhost:8080/vaccine-type-list";
                }
            })
        }
    }
    if (found == false) {
        alert("No data to make inactive!")
    }
});

/*Xử lý nút search */
var searchInput = document.getElementById("searchTable");

searchInput.addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        pagging();
    }
});

//xử lý khi chọn pagesize
let selectElement = document.getElementById('entriesDropdown');
selectElement.addEventListener('change', function () {
    pagging();
});

//xử lý khi chọn trang

window.onload = function () {
    addActive();
}

function addActive() {
    var paggingHTML = document.querySelector(".pagination");
    var links = paggingHTML.querySelectorAll('li > a');
    let pageNumData = parseInt(document.querySelector(".pagination a.page-link.active").innerText);

    for (var i = 0; i < links.length; i++) {
        var link = links[i];
        link.addEventListener('click', function (event) {

            event.preventDefault();
            for (var j = 0; j < links.length; j++) {
                links[j].classList.remove('active');
            }
            this.classList.add('active');

            if (this.innerText == "next") {
                this.classList.remove('active');
                for (var j = 1; j < links.length - 1; j++) {
                    if (links[j].innerText == pageNumData + 1) {
                        links[j].classList.add('active');
                    }
                }
            }

            if (this.innerText == "previous") {
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
        url: '/vaccine-type-paging',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            "pageNum": pageNumData,
            "pageSize": pageSizeData,
            "input": searchData
        }),
        success: function (data) {
            var list = data.lists;
            var vaccineTypeList = data.vaccineTypeList;
            var pageNumber = data.pageNumber;
            var hasPrevious = data.hasPrevious;
            var hasNext = data.hasNext;
            var pageSize = data.pageSize;
            var total = data.totals;

            if (pageNumber===-1 && vaccineTypeList.length===0) {
                alert("No records have bean found")
                window.location.href = "http://localhost:8080/vaccine-type-list";
            }
            if(pageNumber===-1){
                pageNumber=0
            }

            //xử lý phân trang

            let hasPreviousPage = ``;
            let hasNextPage = ``;
            let pagging = ``;

            if (hasPrevious === true) {
                hasPreviousPage = ` 
			<li class="page-item">
               <a class="page-link" href="http://localhost:8080/vaccine-type-list" data-value="${pageNumber}"><<</a>
            </li>`;
            }

            if (hasNext === true) {
                hasNextPage = ` 
			<li class="page-item">
               <a class="page-link" href="http://localhost:8080/vaccine-type-list" data-value="${pageNumber + 1}">>></a>
            </li>`;
            }

            for (var i = 0; i < list.length; i++) {
                pagging += `<li class="page-item">
                            <a class="page-link" href="http://localhost:8080/vaccine-type-list" >${list[i]}</a>
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
            if (vaccineTypeList === 0) {
                alert('No data found!');
                window.location.href = "http://localhost:8080/vaccine-type-list";
            } else {
                let template = '';
                for (let i = 0; i < vaccineTypeList.length; i++) {
                    let row = vaccineTypeList[i];
                    template += `
                <tr>
                    <td class="text-center">
                    <input class="checkUp" type="checkbox" style="transform: scale(1.2)"
                                               data-id="${row.id}">
                    </td>
                    <td><a href="/vaccine-type-update?id=${row.id}"> ${row.id}</a></td>
                    <td>${row.vaccineTypeName}</td>
                    <td>${row.description}</td>
                    <td  class="status">${row.status == true ? 'Active' : 'In-Active'}</td>
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
            document.getElementById("infor").innerHTML = '';
            document.getElementById("infor").innerText = inforUpdate;

            addActive();
        },
        error: function (xhr, textStatus, error) {
            console.log(error);
        }
    });
}