window.onload = function (){
    let code = document.getElementById("code");
    code.value = generateCaptcha();
}
// Generate a random alphanumeric string for the CAPTCHA

let code = document.getElementById("code");

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
        document.getElementById("passwordError").innerText = "";
    }
}

password.onblur = function (){
    if(password.value != ""){
       repassword.value = '';
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

document.getElementById("savecustomer").addEventListener("click", function (event){
         event.preventDefault();
         let checkErr = false;
      let checkEmail = false;

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

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if(emailRegex.test(email.value)){
            document.getElementById("emailError").innerText = ""
        }else {
            checkEmail = true;
            document.getElementById("emailError").innerText = "Email is not correct"
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

        let requestData = {address : address.value, dateOfBirth : dateOfBirth.value , email : email.value , fullName : fullName.value ,
            gender : gender , identityCard : identityCard.value , password : password.value , phone : phone.value , userName : username.value }

        if(checkErr == false && checkEmail == false) {

            $.ajax({
                url: "http://localhost:8080/save-customer",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(requestData),
                success: function (response) {

                    if (response == "username"){
                        alert("Username is already exist")
                    }else if(response == "email"){
                        alert("Email is already exist")
                    }else if(response == "phone"){
                        alert("Phone is already exist")
                    } else {
                        alert("Register successfull");
                        window.location.href = "http://localhost:8080/customer-list";
                    }

                },
                error: function (error) {
                    alert("Database connect error");
                }
            })
        }else {
            if(checkErr == true){
                alert("You must input information into field (*)")
            }

        }
})