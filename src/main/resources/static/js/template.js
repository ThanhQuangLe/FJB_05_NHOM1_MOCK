var currentPath = window.location.pathname;
var targetPaths = [
	'VaccineResult-Report',
	'report-customer',
	'report-vaccine'
];
var isMatched = false;
targetPaths.forEach(function(targetPath) {
	if (currentPath.includes(targetPath)) {
		isMatched = true;
	}
});
switch (true) {
	
	case currentPath.includes("employee-"):
        var title = document.getElementById("employeeSubmenuTitle");
        title.style.color = "rgb(4, 201, 155)";
        title.style.backgroundColor = "#e9ecef";
        title.style.outline = "none";
        if (currentPath.includes("list")) {
          var list = document.getElementById("employeeSubmenuList");
          var text = list.innerHTML
          list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
          list.classList.add("activeSidebar");
          document.getElementById("employeeSubmenu").classList.add("show");
        }
        if (currentPath.includes("create")) {
          var create = document.getElementById("employeeSubmenuCreate");
          var text = create.innerHTML
          create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
          create.classList.add("activeSidebar");
          document.getElementById("employeeSubmenu").classList.add("show");
        }
        break;
	

	case currentPath.includes("customer-"):
		var title = document.getElementById("customerSubmenuTitle");
		title.style.color = "rgb(4, 201, 155)";
		title.style.backgroundColor = "#e9ecef";
		title.style.outline = "none";
		if (currentPath.includes("list")) {
			var list = document.getElementById("customerSubmenuList");
			var text = list.innerHTML
			list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			list.classList.add("activeSidebar");
			document.getElementById("customerSubmenu").classList.add("show");
		}
		if (currentPath.includes("create")) {
			var create = document.getElementById("customerSubmenuCreate");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("customerSubmenu").classList.add("show");
		}
		break;

	case currentPath.includes("vaccine-type"):
		var title = document.getElementById("vaccineSubmenuTitle");
		title.style.color = "rgb(4, 201, 155)";
		title.style.backgroundColor = "#e9ecef";
		title.style.outline = "none";
		if (currentPath.includes("list")) {
			var list = document.getElementById("vaccineSubmenuList");
			var text = list.innerHTML
			list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			list.classList.add("activeSidebar");
			document.getElementById("vaccineSubmenu").classList.add("show");
		}
		if (currentPath.includes("create")) {
			var create = document.getElementById("vaccineSubmenuCreate");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("vaccineSubmenu").classList.add("show");
		}
		break;

	case currentPath.includes("vaccine-"):
		var title = document.getElementById("vaccineManagementSubmenuTitle");
		title.style.color = "rgb(4, 201, 155)";
		title.style.backgroundColor = "#e9ecef";
		title.style.outline = "none";
		if (currentPath.includes("list")) {
			var list = document.getElementById("vaccineManagementSubmenuList");
			var text = list.innerHTML
			list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			list.classList.add("activeSidebar");
			document.getElementById("vaccineManagementSubmenu").classList.add("show");
		}
		if (currentPath.includes("create")) {
			var create = document.getElementById("vaccineManagementSubmenuCreate");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("vaccineManagementSubmenu").classList.add("show");
		}
		break;

	case currentPath.includes("vaccineSchedule"):
		var title = document.getElementById("injectionSubmenuTitle");
		title.style.color = "rgb(4, 201, 155)";
		title.style.backgroundColor = "#e9ecef";
		title.style.outline = "none";
		if (currentPath.includes("list")) {
			var list = document.getElementById("injectionSubmenuList");
			var text = list.innerHTML
			list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			list.classList.add("activeSidebar");
			document.getElementById("injectionSubmenu").classList.add("show");
		}
		if (currentPath.includes("create")) {
			var create = document.getElementById("injectionSubmenuCreate");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("injectionSubmenu").classList.add("show");
		}
		break;

	case currentPath.includes("vaccineResult"):
		var title = document.getElementById("injectionResultSubmenuTitle");
		title.style.color = "rgb(4, 201, 155)";
		title.style.backgroundColor = "#e9ecef";
		title.style.outline = "none";
		if (currentPath.includes("list")) {
			var list = document.getElementById("injectionResultSubmenuList");
			var text = list.innerHTML
			list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			list.classList.add("activeSidebar");
			document.getElementById("injectionResultSubmenu").classList.add("show");
		}
		if (currentPath.includes("create")) {
			var create = document.getElementById("injectionResultSubmenuCreate");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("injectionResultSubmenu").classList.add("show");
		}
		break;

	case currentPath.includes("news"):
		var title = document.getElementById("newsSubmenuTitle");
		title.style.color = "rgb(4, 201, 155)";
		title.style.backgroundColor = "#e9ecef";
		title.style.outline = "none";
		if (currentPath.includes("list")) {
			var list = document.getElementById("newsSubmenuList");
			var text = list.innerHTML
			list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			list.classList.add("activeSidebar");
			document.getElementById("newsSubmenu").classList.add("show");
		}
		if (currentPath.includes("create")) {
			var create = document.getElementById("newsSubmenuCreate");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("newsSubmenu").classList.add("show");
		}
		break;

	case isMatched:
		var title = document.getElementById("reportSubmenuTitle");
		title.style.color = "rgb(4, 201, 155)";
		title.style.backgroundColor = "#e9ecef";
		title.style.outline = "none";
		if (currentPath.includes("/VaccineResult-Report")) {
			var list = document.getElementById("reportSubmenuInjection");
			var text = list.innerHTML
			list.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			list.classList.add("activeSidebar");
			document.getElementById("reportSubmenu").classList.add("show");
		}
		if (currentPath.includes("report-customer")) {
			var create = document.getElementById("reportSubmenuCustomer");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("reportSubmenu").classList.add("show");
		}
		if (currentPath.includes("report-vaccine")) {
			var create = document.getElementById("reportSubmenuVaccine");
			var text = create.innerHTML
			create.innerHTML = `<i class="fa-solid fa-arrow-right"></i>` + "  " + text
			create.classList.add("activeSidebar");
			document.getElementById("reportSubmenu").classList.add("show");
		}
		break;

	default:
		// Xử lý mặc định khi không có case nào khớp
		break;
}


var listItems = document.querySelectorAll('.sidebar-menu li');

listItems.forEach(function(li) {
	
	
	var link = li.querySelector('a');
	
	if (link) { 
       link.addEventListener('click', function(event) {

		listItems.forEach(function(item) {
			var itemLink = item.querySelector('a');
			if (itemLink !== link) {
				itemLink.style.color = 'black';
				itemLink.style.backgroundColor = 'white';
			}
		});

		link.style.color = 'black'; 
		link.style.backgroundColor = 'white'; 
	});
	}
});

    var image_users = document.getElementById('image_users').innerText;
    var image_users_show =  document.getElementById('image_users_show');
    image_users_show.setAttribute('src', image_users);
