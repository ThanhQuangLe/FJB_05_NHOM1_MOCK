

var searchInput = document.getElementById("searchTable");

searchInput.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault(); // Ngăn chặn hành động mặc định của Enter (ví dụ: submit form)

        var form = document.getElementById("searchForm");
        form.submit(); // Gửi form đi
    }
});


//update vaccine khi ấn vào nút update
document.getElementById("makevaccine").addEventListener("click", function() {
    let id;
    let found = false;
    let checkList = document.querySelectorAll(".checkUp");

    checkList.forEach(function(c) {
        if (!found && c.checked) {
            id = c.getAttribute('data-id');
           found = true;
        }
    });

    if (id) {
        window.location.href = "/vaccine-update?id=" + id;
    }
});


//xử lý khi ấn vào nút Make In-active
document.getElementById("makeinactive").addEventListener("click", function() {


    let id;
    let check = true;
    let count = 0;
    let arrId = [];

    let checkList = document.querySelectorAll(".checkUp");

    checkList.forEach(function(c) {
        if (check == true && c.checked) {
            count++;

            //tìm dom của status
         let statusCell = c.closest("tr").querySelector(".status");

             if("In-Active" == statusCell.textContent){
                 alert("Invalid data - please recheck your selects")
                 check = false;
             }else {
                 id = c.getAttribute('data-id');
                arrId.push(id);
             }
        }

    });

  if(check == true){


      let confirmation = confirm("Are you sure you want to make it inactive?");

      if (confirmation) {
       //hực hiện thay đổi khi  xác nhận


          $.ajax({
              url :"http://localhost:8080/vaccine-updatestatus",
              method: 'post',
              contentType : 'application/json',
              data: JSON.stringify( arrId ),
              success: function (response){
                  alert("Inactive status updated");
                  // Điều hướng về trang vaccine-list
                  window.location.href = "http://localhost:8080/vaccine-list";
              }
          })


      }
  }
    if(count == 0){
        alert("No data to make inactive!")
    }
});