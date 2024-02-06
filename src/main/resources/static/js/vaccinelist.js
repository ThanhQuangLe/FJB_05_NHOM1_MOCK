

var searchInput = document.getElementById("searchTable");

searchInput.addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault(); // Ngăn chặn hành động mặc định của Enter (ví dụ: submit form)

        document.getElementById("searchForm").submit();
    }
});

// Lấy tham chiếu đến phần tử dropdown
// var entriesDropdown = document.getElementById("entriesDropdown");
//
// // Thêm sự kiện change
// entriesDropdown.addEventListener("change", function () {
//     // Lấy giá trị của dropdown khi thay đổi
//     var selectedValue = entriesDropdown.value;
//
//     // In giá trị ra console để kiểm tra
//     console.log("Đã chọn: " + selectedValue);
//
//     // Thiết lập giá trị cho trường ẩn pageSize
//     document.getElementById("pageSize").value = selectedValue;
//
//     // Submit form
//     document.getElementById("searchForm").submit();
// });


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

// Kiểm tra xem phần tử có tồn tại không
    if (pageNowInput) {

        var pageNowValue = pageNowInput.value;
        console.log(pageNowValue);
    }

    let checkList = document.querySelectorAll(".checkUp");

    checkList.forEach(function (c) {
        if (check == true && c.checked) {
            count++;

            //tìm dom của status
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