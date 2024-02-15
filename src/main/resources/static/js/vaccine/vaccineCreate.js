
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

function displayImage() {
    var input = document.getElementById('imageInput');
    var img = document.getElementById('selectedImage');

    // Kiểm tra xem người dùng đã chọn file hay chưa
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            img.src = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
    }
}

