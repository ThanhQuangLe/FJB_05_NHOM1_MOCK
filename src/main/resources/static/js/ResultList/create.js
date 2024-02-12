var saveButton = document.getElementById("formSubmit");
saveButton.addEventListener("submit",function(e){
	e.preventDefault();
	 var modalBody = document.querySelector('.modal-body');
	  modalBody.innerHTML = '';
	let check = true;
	let notiInjection = '';
	let notiDateInjection = '';
	let notiNextDateInjection = '';
	
	let checkInjectionNumber = document.getElementById("numberOfInjection").value;
	let checkDateInjection = document.getElementById("injectionDate").value;
	let checkNextDateInjection = document.getElementById("nextInjectionDate").value;


	var regexNumber = /^\d+$/;
	if(!regexNumber.test(checkInjectionNumber)){
		check = false;
		notiInjection = 'Injection only contain number';
	}
	
  var dateFormat = "DD/MM/YYYY";
  
  var parsedDateInjection = moment(checkDateInjection, dateFormat);
   var parsedNextDateInjection = moment(checkNextDateInjection, dateFormat);
  
	if(!/^\d{2}\/\d{2}\/\d{4}$/.test(checkDateInjection)||!parsedDateInjection.isValid()){
		check = false;
		notiDateInjection = 'Date in the wrong format, must be DD/MM/YYYY';
	}else if(parsedDateInjection.isBefore(moment(), 'day')){
		 check = false;
        notiDateInjection = 'Please input Date of vaccination with value greater or equal the current date';
	}
	
	if(!/^\d{2}\/\d{2}\/\d{4}$/.test(checkNextDateInjection)||!parsedNextDateInjection.isValid()){
		check = false;
		notiNextDateInjection = 'Next Date in the wrong format, must be DD/MM/YYYY';
	}else if(parsedNextDateInjection.isSameOrBefore(parsedDateInjection, 'day')){
		 check = false;
        notiNextDateInjection = 'Please input Next date of vaccination with value greater than Date of vaccination';
	}
	

    if (check) {
        saveButton.submit();
    } else {
        showModal(notiInjection, notiDateInjection, notiNextDateInjection);
    }
});

function showModal(notiInjection, notiDateInjection, notiNextDateInjection) {
    var modalTitle = document.querySelector('.modal-title');
    var modalBody = document.querySelector('.modal-body');

    modalTitle.textContent = 'Notification';

    var modalContent = '';
    if (notiInjection) {
        modalContent += '<p>' + notiInjection + '</p>';
    }
    if (notiDateInjection) {
        modalContent += '<p>' + notiDateInjection + '</p>';
    }
    if (notiNextDateInjection) {
        modalContent += '<p>' + notiNextDateInjection + '</p>';
    }

    modalBody.innerHTML = modalContent;

    var myModal = new bootstrap.Modal(document.getElementById('myModal'));
    myModal.show();
}
