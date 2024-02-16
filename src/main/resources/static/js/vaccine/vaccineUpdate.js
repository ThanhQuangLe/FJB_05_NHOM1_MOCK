
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

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            img.src = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function resetImage() {
    // Lấy nguồn ảnh ban đầu từ thuộc tính dữ liệu
    var originalSrc = document.getElementById('selectedImage').getAttribute('data-original-src');

    // Đặt nguồn ảnh trở lại nguồn ban đầu
    document.getElementById('selectedImage').src = originalSrc;
}

// Đặt nguồn ảnh ban đầu làm thuộc tính dữ liệu khi trang được tải
document.addEventListener('DOMContentLoaded', function() {
    var originalSrc = document.getElementById('selectedImage').src;
    document.getElementById('selectedImage').setAttribute('data-original-src', originalSrc);
});


