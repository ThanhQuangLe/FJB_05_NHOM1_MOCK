window.onload = function (){
    // Lấy đường dẫn hiện tại
    var currentURL = window.location.href;

// Tạo một đối tượng URL từ đường dẫn
    var urlObject = new URL(currentURL);

// Lấy giá trị của tham số 'id' từ đối tượng URL
    var id = urlObject.searchParams.get('id');

    console.log(id);

    $.ajax({
        url: "http://localhost:8080/customer-get?id=" + id,
        method : "get",
        contentType: "application/json",
        success: function (response) {


            // Lưu giá trị ban đầu vào biến initialValues
            initialValues = {
                        id: response.id,
                        fullName: response.fullName,
                        dateOfBirth: response.dateOfBirth,
                        gender: response.gender,
                        identityCard: response.identityCard,
                        address: response.address,
                        userName: response.userName,
                        password: response.password,
                        email: response.email,
                        phone: response.phone
            };


            document.getElementById("customerId").value = response.id;
          document.getElementById("fullName").value = response.fullName;
          document.getElementById("dateOfBirth").value = response.dateOfBirth;

            let genderInput = document.getElementsByName('gender');
            let genderValue = response.gender == true ? "Male" : "Female";
            genderInput.forEach(function (g){
                if(g.value == genderValue){
                   g.checked = true;
                }
            })

            document.getElementById("identityCard").value = response.identityCard;
             document.getElementById("address").value = response.address;
             document.getElementById("username").value = response.userName;

           document.getElementById("password").value = response.password;
           document.getElementById("repassword").value = response.password;

            document.getElementById("email").value = response.email;
            document.getElementById("phone").value = response.phone;

        },
        error: function (error) {

            alert("Database connect error");
        }
    })

    let code = document.getElementById("code");
    code.value = generateCaptcha();

}

var initialValues = {};

let code = document.getElementById("code");

// Generate a random alphanumeric string for the CAPTCHA
document.getElementById("reloadCode").onclick = function (){

    code.value = generateCaptcha();
}
function generateCaptcha() {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let captcha = '';
    for (let i = 0; i < 6; i++) {
        captcha += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return captcha;
}


let password = document.getElementById("password");
let repassword = document.getElementById("repassword");
let repassErr = document.getElementById("repassError");

password.onkeyup = function (){
    if(password.value != ""){
        document.getElementById("passwordError").innerText = ""
    }
}

password.onblur = function (){
    if(password.value != repassword.value){
        repassErr.innerText = "password is not correct, please re-enter"
    }else {
        repassErr.innerText = "";
    }
}

repassword.onblur = function (){
    if(password.value != repassword.value){
        repassErr.innerText = "password is not correct, please re-enter"
    }else {
        repassErr.innerText = "";
    }
}

repassword.onkeyup = function (){
    if(repassword.value != ""){
        repassErr.innerText = ""
    }
}



let fullName = document.getElementById("fullName");

fullName.onkeyup = function (){
    if(fullName.value != ""){
        document.getElementById("fullNameError").innerText = ""
    }
}

let dateOfBirth = document.getElementById("dateOfBirth");
let dateErr =  document.getElementById("dateOfBirthError");

dateOfBirth.onblur = function (){
    let inputDate = new Date(dateOfBirth.value);
    let currentDate = new Date();

    if(inputDate > currentDate){
        dateErr.innerText = "Date of birth cannot be greater than today"
    }else {
        dateErr.innerText = "";
    }
}

let identityCard = document.getElementById("identityCard");
identityCard.onkeyup = function (){
    if(identityCard.value != ""){
        document.getElementById("identityCardError").innerText = ""
    }
}

let address = document.getElementById("address");
address.onkeyup = function (){
    if(address.value != ""){
        document.getElementById("addressError").innerText = ""
    }
}

let username = document.getElementById("username");
username.onkeyup = function (){
    if(username.value != ""){
        document.getElementById("usernameError").innerText = ""
    }
}

let email = document.getElementById("email");
email.onkeyup = function (){
    if(email.value != ""){
        document.getElementById("emailError").innerText = ""
    }
}

let phone = document.getElementById("phone");
phone.onkeyup = function (){
    if(phone.value != ""){
        document.getElementById("phoneError").innerText = ""
    }
}

let capcha = document.getElementById("capcha");
capcha.onblur = function (){
    if(capcha.value != code.value){
        document.getElementById("capchaError").innerText = "Capcha is not correct, please re-enter"
    }else {
        document.getElementById("capchaError").innerText = ""
    }
}
capcha.onkeyup = function (){
    if(capcha.value != ""){
        document.getElementById("capchaError").innerText = ""
    }
}



document.getElementById("savecustomer").addEventListener("click", function (event){
    event.preventDefault();
    let checkErr = false;

    let id = document.getElementById("customerId").value ;
    console.log("id = " + id);

    if(fullName.value == ""){
        checkErr = true;
        document.getElementById("fullNameError").innerText = "Fullname is not empty"
    }else {
        document.getElementById("fullNameError").innerText = ""
    }


    if(dateOfBirth.value == ""){
        checkErr = true;
        document.getElementById("dateOfBirthError").innerText = "Date of birth is not empty"
    }else {
        document.getElementById("dateOfBirthError").innerText = ""
    }

    let genderInput = document.getElementsByName('gender');
    let genderValue;
    genderInput.forEach(function (g){
        if(g.checked){
            genderValue = g.value;
        }
    })
    let gender = genderValue == "Male" ? true : false;

    if(identityCard.value == ""){
        checkErr = true;
        document.getElementById("identityCardError").innerText = "Identity Card is not empty"
    }else {
        document.getElementById("identityCardError").innerText = ""
    }

    if(address.value == ""){
        checkErr = true;
        document.getElementById("addressError").innerText = "Address is not empty"
    }else {
        document.getElementById("addressError").innerText = ""
    }

    if(username.value == ""){
        checkErr = true;
        document.getElementById("usernameError").innerText = "Username is not empty"
    }else {
        document.getElementById("usernameError").innerText = ""
    }

    if(password.value == ""){
        checkErr = true;
        document.getElementById("passwordError").innerText = "Password is not empty"
    }else {
        document.getElementById("passwordError").innerText = ""
    }

    if(repassword.value == ""){
        checkErr = true;
        repassErr.innerText = "Re-password is not empty"
    }else {
        repassErr.innerText = ""
    }

    if(email.value == ""){
        checkErr = true;
        document.getElementById("emailError").innerText = "Email is not empty"
    }else {
        document.getElementById("emailError").innerText = ""
    }

    if(phone.value == ""){
        checkErr = true;
        document.getElementById("phoneError").innerText = "Phone is not empty"
    }else {
        document.getElementById("phoneError").innerText = ""
    }

    if(capcha.value == ""){
        checkErr = true;
        document.getElementById("capchaError").innerText = "Capcha is not empty"
    }else {
        document.getElementById("capchaError").innerText = ""
    }

    let requestData = {id :  id ,address : address.value, dateOfBirth : dateOfBirth.value , email : email.value , fullName : fullName.value ,
        gender : gender , identityCard : identityCard.value , password : password.value , phone : phone.value , userName : username.value }

    if(checkErr == false) {
        // document.getElementById("form-save").submit();

        $.ajax({
            url: "http://localhost:8080/update-customer",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestData),
            success: function (response) {
                alert("Update successfull");
                window.location.href = "http://localhost:8080/customer-list";
            },
            error: function (error) {
                alert("Database connect error");
            }
        })
    }else {
        alert("You must input information into field (*)")
    }
})

document.getElementById("reset").addEventListener("click", function () {
    console.log("chạy reset")

    // Đặt lại các trường input với giá trị ban đầu
    document.getElementById("customerId").value = initialValues.id;

    document.getElementById("fullName").value = initialValues.fullName;
    document.getElementById("dateOfBirth").value = initialValues.dateOfBirth;

    let genderInput = document.getElementsByName('gender');
    let genderValue = initialValues.gender == true ? "Male" : "Female";
    genderInput.forEach(function (g){
        if(g.value == genderValue){
            g.checked = true;
        }
    })

    document.getElementById("identityCard").value = initialValues.identityCard;
    document.getElementById("address").value = initialValues.address;
    document.getElementById("username").value = initialValues.userName;

    document.getElementById("password").value = initialValues.password;
    document.getElementById("repassword").value = initialValues.password;

    document.getElementById("email").value = initialValues.email;
    document.getElementById("phone").value = initialValues.phone;

});
