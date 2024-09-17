class InvalidValueException extends Error {
    constructor(message) {
        super(message);
        this.name = "InvalidValueException";
    }
}

function validation(values){
	console.log(values);
	if (values.x===undefined){
		throw new InvalidValueException('You didn\'t choose X');
	}
	if (values.r===undefined){
		throw new InvalidValueException('You didn\'t choose R');
	}
	if (values.y===""){
		throw new InvalidValueException('You didn\'t choose Y');
	} else {
		var floaty = parseFloat(values.y);
		if (!/^(-?\d+(\.\d+)?)$/.test(floaty)){
			throw new InvalidValueException('Y must be the number');
		}
		if (floaty<=-5 || floaty>=3){
			throw new InvalidValueException('Invalid Y');
		}
	}
}


function saveArticle(event){
	event.preventDefault();
	
	let mainForm = document.getElementById('main');  
	let formData = new FormData(mainForm);
	const values = Object.fromEntries(formData);
	
	try {
		validation(values);
	} catch (e){
		alert(e.message);
		return;
	}
	
	fetch('/fcgi-bin/Server.jar?' + new URLSearchParams(formData).toString(), {method: "GET"})
	.then(response => {
		if (!response.ok){
			throw new Error('${response.status}');
		}
		return response.text();
	})
	.then(function (answer){


		var answ = JSON.parse(answer);

		if (answ.code===200) {

			const lastTries = document.getElementById('tries');

			if (lastTries.rows.length >= 8) {
				lastTries.innerHTML = '';
			}
			const newRow = lastTries.insertRow(-1);
			const newCell = newRow.insertCell(0);

			let textResult;
			if (answ.result === "true") {
				textResult = "YES ";
			} else {
				textResult = "NO ";
			}
			newCell.textContent = textResult + 'x=' + answ.x + '; y=' + answ.y + '; r=' + answ.r + " "
				+ answ.time + "\n";
		} else if (answ.code===400){
			alert(answ.result)
		}

	})
}

document.addEventListener("DOMContentLoaded", () => {
	console.log("Hello World!");
  
  let mainForm = document.getElementById('main');  
  mainForm.addEventListener('submit', saveArticle);

});