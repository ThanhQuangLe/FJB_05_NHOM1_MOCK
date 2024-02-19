
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

//     var pageNowInput = document.getElementById("pageNow");
//     let pageSize = document.getElementById("pageSize").value;
//     let searchTerm = document.getElementById("searchTable").value;
//
// // Kiểm tra xem phần tử có tồn tại không
//     if (pageNowInput) {
//
//         var pageNowValue = pageNowInput.value;
//         console.log(pageNowValue);
//     }

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

// var searchInput = document.getElementById('searchTable');
// searchInput.addEventListener("keypress", function (event) {
//
//     if (event.key === "Enter") {
//         console.log("enter");
//         pagging();
//     }
// });
//
//
// let entriesDropdown = document.getElementById("entriesDropdown");
// entriesDropdown.addEventListener("change", function (event) {
//     console.log("change");
//     pagging();
// });
//
// function pagging() {
//     let pageNumData = document.querySelector("a.page-link.active").innerText;
//     let pageSizeData = document.getElementById("entriesDropdown").value;
//     let searchData = document.getElementById("searchTable").value;
//     console.log("pageNumData" + pageNumData);
//     console.log("pageSizeData" + pageSizeData);
//     console.log("searchData" + searchData);
//
//
//     let requestData= {
//         "pageNumber": pageNumData,
//         "pageSize": pageSizeData,
//         "searchInput": searchData
//     };
//     let paggingHTML = document.querySelector(".pagination");
//
//     let html = ``;
//
//     $.ajax({
//         url: '/customer-paging',
//         type: 'POST',
//         contentType: 'application/json',
//         data: JSON.stringify(requestData),
//         success: function(data) {
//             var list = data.list;
//             var customerList = data.customerList;
//             var pageNumber = data.pageNumber;
//             var hasPrevious = data.hasPrevious;
//             var hasNext = data.hasNext;
//             var pageSize = data.pageSize;
//             var total = data.total;
//
//
//
//             //xử lý phân trang
//
//             let hasPreviousPage = ``;
//             let hasNextPage = ``;
//             let pagging = ``;
//
//             if (hasPrevious === true) {
//                 hasPreviousPage = `
// 			<li class="page-item">
//                <a class="page-link" href="http://localhost:8080/customer-list" data-value="${pageNumber}">Previous</a>
//             </li>`;
//             }
//
//             if (hasNext === true) {
//                 hasNextPage = `
// 			<li class="page-item">
//                <a class="page-link" href="http://localhost:8080/customer-list" data-value="${pageNumber + 1}">Next</a>
//             </li>`;
//             }
//
//             for (var i = 0; i < list.length; i++) {
//                 pagging += `<li class="page-item">
//                             <a class="page-link" href="http://localhost:8080/customer-list" >${list[i]}</a>
//                         </li>`;
//             }
//
//             paggingHTML.innerHTML = hasPreviousPage + pagging + hasNextPage;
//             console.log( "paggingHTML");
//             console.log( paggingHTML);
//
//             let links = paggingHTML.querySelectorAll('li > a');
//             for (var i = 0; i < links.length; i++) {
//                 var link = links[i];
//                 if (link.innerText == pageNumber+1) {
//                     link.classList.add('active');
//                 }
//             }
//
//             //xử lý dữ liệu in ra
//             if (customerList === 0) {
//                 alert('No data found!');
//                 window.location.href = "http://localhost:8080/customer-list";
//             } else {
//                 console.log(customerList);
//
//                 let total = '';
//
//                 customerList.content.forEach(function (c) {
//                     let template = `<tr>
//                         <td class="text-center">
//                             <input class="checkUp" data-id="${c.id}" type="checkbox" style="transform: scale(1.2)">
//                         </td>
//                         <td class="col-2">
//                             <a href="http://localhost:8080/customer-update?id=${c.id}">${c.fullName}</a>
//                         </td>
//                         <td class="col-2">${c.dateOfBirth}</td>
//                         <td class="col-2">${c.gender == true ? 'Male' : 'Female'}</td>
//                         <td class="col-2" >${c.address}</td>
//                         <td class="col-2" >${c.identityCard}</td>
//                         <td class="col-1">${c.phone}</td>
//                     </tr>`;
//                     total += template;
//                 });
//
//                 $('tbody').html(total);
//
//             }
//
//             //xử lý số phần tử hiển thị
//             let pageSizeShow = document.getElementById("entriesDropdown");
//             var options = pageSizeShow.options;
//
//             for (var i = 0; i < options.length; i++) {
//                 var option = options[i];
//
//                 if (option.value == pageSize) {
//                     option.selected = true;
//                 }
//             }
//
//             var maxRecord = (pageNumber+1)*pageSize;
//             if(maxRecord>total){
//                 maxRecord=total;
//             }
//             var inforUpdate = `
//             Showing ${(pageNumber+1)*pageSize-pageSize+1} to ${maxRecord} of ${total} entries`;
//
//             document.getElementById("infor").innerText=inforUpdate;
//
//             // addActive();
//         },
//         error: function(xhr, textStatus, error) {
//             console.log(error);
//         }
//     });
// }

