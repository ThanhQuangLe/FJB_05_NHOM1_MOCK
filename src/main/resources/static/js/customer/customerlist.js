
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
        event.preventDefault(); // Ngăn chặn hành động mặc định của Enter

        document.getElementById("searchForm").submit();
    }
});


//khi thay đổi số lượng showing
var entriesDropdown = document.getElementById("entriesDropdown");
entriesDropdown.addEventListener("change", function (event) {
    event.preventDefault();
    document.getElementById("searchForm").submit();
});

//xử lý update
document.getElementById("updatecustomer").addEventListener("click", function () {
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
        alert("No data to update !")
    }
    if(count >1){
        alert("only update 1 customer- please recheck your selects")
    }
    if (id && count ==1 ) {
        window.location.href = "/customer-update?id=" + id;
    }

});

                // xử lý delete

document.getElementById("deletecustomer").addEventListener("click", function () {

    let id;
    let count = 0;
    let arrId = [];


    let checkList = document.querySelectorAll(".checkUp");

    checkList.forEach(function (c) {
        if ( c.checked) {
            count++;
            id = c.getAttribute('data-id');
            arrId.push(id);
        }
    });

    if (arrId.length > 0 ) {

        let confirmation = confirm("Are you sure to delete?");

        if (confirmation) {
            //thực hiện thay đổi khi  xác nhận

            $.ajax({
                url: "http://localhost:8080/customer-delete",
                method: 'delete',
                contentType: 'application/json',
                data: JSON.stringify(arrId),
                success: function (response) {
                    if(response > 0){
                        alert("Customers deleted");

                        window.location.reload();
                    }
                    // window.location.href = "http://localhost:8080/vaccine-list?pageNumber=" + pageNowValue + "&pageSize=" +pageSize +"&searchTerm="+ searchTerm;
                },
                error: function (error) {

                    alert("Database connect error");
                }
            })
        }
    }else{
        alert("No data deleted!")
    }
});



