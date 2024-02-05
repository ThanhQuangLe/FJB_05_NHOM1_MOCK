//xử lý khi ấn vào nút Make In-active
document.getElementById("makeinactive").addEventListener("click", function() {

    let id;
    let found = false;

    let checkList = document.querySelectorAll(".checkUp");

    checkList.forEach(function(c) {
        if (!found && c.checked) {

            //tìm dom của status
            let statusCell = c.closest("tr").querySelector(".status");

            if("In-Active" == statusCell.textContent){
                alert("Invalid data - please recheck your selects")
                found = true;
            }else {
                id = c.getAttribute('data-id');
                found = true;
            }
        }
    });

    if(id != null){

        let confirmation = confirm("Are you sure you want to make it inactive?");

        if (confirmation) {
            //hực hiện thay đổi khi  xác nhận
            $.ajax({
                url :"http://localhost:8080/vaccine-type-updatestatus?id="+`${id}`,
                method: 'get',
                contentType : 'application/json',
                success: function (response){
                    alert("Inactive status updated");
                    // Điều hướng về trang vaccine-list
                    window.location.href = "http://localhost:8080/vaccine-type-list";
                }
            })
        }
    }
    if(found == false){
        alert("No data to make inactive!")
    }
});
