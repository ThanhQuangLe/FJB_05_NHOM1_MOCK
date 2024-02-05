
//validate time
document.getElementById("savevaccine").onclick = function(e) {
    e.preventDefault();
    let check = false;

    let timebegin = document.getElementById("timeSNextInjection").value;
    let timeend = document.getElementById("timeENextInjection").value;

    if (timebegin > timeend) {
        document.getElementById("timeError").textContent = "Time to start next vaccination must be less than end time";
        check = true;
    } else {
        document.getElementById("timeError").textContent = "";
    }

    if (check == false) {
        document.getElementById("form-save").submit();
    }
};

//update vaccine khi ấn vào nút update
// document.getElementById("makevaccine").addEventListener("click", function() {
//     let id;
//     let found = false;
//     let checkList = document.querySelectorAll(".checkUp");
//
//     checkList.forEach(function(c) {
//         if (!found && c.checked) {
//             id = c.getAttribute('data-id');
//            found = true;
//         }
//     });
//
//     if (id) {
//         window.location.href = "/vaccine-update?id=" + id;
//     }
// });
//
//
// //xử lý khi ấn vào nút Make In-active
// document.getElementById("makeinactive").addEventListener("click", function() {
//
//
//     let id;
//     let found = false;
//
//     let checkList = document.querySelectorAll(".checkUp");
//
//     checkList.forEach(function(c) {
//         if (!found && c.checked) {
//
//             //tìm dom của status
//          let statusCell = c.closest("tr").querySelector(".status");
//
//              if("In-Active" == statusCell.textContent){
//                  alert("Invalid data - please recheck your selects")
//                  found = true;
//              }else {
//                  id = c.getAttribute('data-id');
//                  found = true;
//              }
//         }
//
//     });
//
//   if(id != null){
//       let confirmation = confirm("Are you sure you want to make it inactive?");
//
//       if (confirmation) {
//        //hực hiện thay đổi khi  xác nhận
//
//           $.ajax({
//               url :"http://localhost:8080/vaccine-updatestatus?id="+`${id}`,
//               method: 'get',
//               contentType : 'application/json',
//               success: function (response){
//                  alert("Inactive status updated");
//                   // Điều hướng về trang vaccine-list
//                   window.location.href = "http://localhost:8080/vaccine-list";
//               }
//           })
//       } else {
//           // Không thực hiện thay đổi khi không xác nhận
//          alert("No changes made");
//
//       }
//   }
//     if(found == false){
//         alert("No data to make inactive!")
//     }
// });