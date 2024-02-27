
// hiển thị giá trị của option đúng với giá trị đã chọn
document.addEventListener("DOMContentLoaded", function () {

    let pageSizeValue = document.getElementById("pageSizeData").value;

    let options = document.getElementById("entriesDropdown").getElementsByTagName("option");

    for (let i = 0; i < options.length; i++) {
        if (options[i].value === pageSizeValue) {
            options[i].selected = true;
            break;
        }
    }
});


//khi ấn nút tìm kiếm

var searchInput = document.getElementById("searchTable");

searchInput.addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault(); // Ngăn chặn hành động mặc định của Enter (ví dụ: submit form)

        document.getElementById("searchForm").submit();
    }
});


//khi thay đổi số lượng showing
var entriesDropdown = document.getElementById("entriesDropdown");
entriesDropdown.addEventListener("change", function (event) {
    event.preventDefault();
    document.getElementById("searchForm").submit();
});



//update vaccine khi ấn vào nút update
document.getElementById("makevaccine").addEventListener("click", function () {
    let id;

    let checkList = document.querySelectorAll(".checkUp");
    let count = 0;

    checkList.forEach(function (c) {
        if ( c.checked) {
            id = c.getAttribute('data-id');
            count ++;
        }
    });


    if(count == 0){
        alert("No data to make vaccine!")
    }
    if(count >1){
        alert("only update 1 vaccine- please recheck your selects")
    }
    if (id && count ==1 ) {
        window.location.href = "/vaccine-update?id=" + id;
    }

});


//xử lý khi ấn vào nút Make In-active
document.getElementById("makeinactive").addEventListener("click", function () {
    let id;
    let check = true;
    let count = 0;
    let arrId = [];

    var pageNowInput = document.getElementById("pageNow");
    let pageSize = document.getElementById("pageSize").value;
    let searchTerm = document.getElementById("searchTable").value;
    if (pageNowInput) {
        var pageNowValue = pageNowInput.value;
        console.log(pageNowValue);
    }
    let checkList = document.querySelectorAll(".checkUp");
    checkList.forEach(function (c) {
        if (check == true && c.checked) {
            count++;
            let statusCell = c.closest("tr").querySelector(".status");
            if ("In-Active" == statusCell.textContent) {
                alert("Invalid data - please recheck your selects")
                check = false;
            } else {
                id = c.getAttribute('data-id');
                arrId.push(id);
            }
        }

    });

    if (arrId.length > 0 && check == true) {


        let confirmation = confirm("Are you sure you want to make it inactive?");

        if (confirmation) {
            //hực hiện thay đổi khi  xác nhận


            $.ajax({
                url: "http://localhost:8080/vaccine-updatestatus",
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(arrId),
                success: function (response) {
                    alert("Inactive status updated");
                    // Điều hướng về trang vaccine-list
                    window.location.href = "http://localhost:8080/vaccine-list?pageNumber=" + pageNowValue + "&pageSize=" +pageSize +"&searchTerm="+ searchTerm;
                }
            })

        }
    }
    if (arrId.length == 0 && check == true) {
        alert("No data to make inactive!")
    }
});