class InvalidValueException extends Error {
    constructor(message) {
        super(message);
        this.name = "InvalidValueException";
    }
}

function validation(values){
	console.log(values);
	if (values.x===undefined){
		throw new InvalidValueException('Вы не выбрали X');
		//alert('Вы не выбрали X');
	}
	if (values.r===undefined){
		throw new InvalidValueException('Вы не выбрали R');
		//alert('Вы не выбрали R');
	}
	if (values.y===""){
		throw new InvalidValueException('Вы не выбрали Y');
		//alert('Вы не выбрали Y');
	} else {
		var floaty = parseFloat(values.y);
		if (!/^(-?\d+(\.\d+)?)$/.test(floaty)){
			throw new InvalidValueException('Y должен быть числом');
		}
		if (floaty<=-5 || floaty>=3){
			throw new InvalidValueException('Недопустимое значение Y');
			//alert('Недопустимое значение Y');
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
	
	fetch('/fcgi-bin/hello-world.jar?' + new URLSearchParams(formData).toString(), {method: "GET"})
	.then(response => {
		if (!response.ok){
			throw new Error('${response.status}');
		}
		return response.text();
	})
	.then(function (answer){

		var answ = JSON.parse(answer);

		const lastTries = document.getElementById('tries');
	
		if (lastTries.rows.length>=8){
			lastTries.innerHTML = '';
		}
		const newRow = lastTries.insertRow(-1);
		const newCell = newRow.insertCell(0);

		newCell.textContent = answ.result + ' x=' + answ.x + '; y=' + answ.y + '; r=' + answ.r + " "
			+ answ.time + " " + answ.workTime + "\n";

	})
	
	const lastTries = document.getElementById('tries');
	
	if (lastTries.rows.length>=8){
		lastTries.innerHTML = '';
	}
	
	const newRow = lastTries.insertRow(-1);
    const newCell = newRow.insertCell(0);
	
	

    newCell.textContent = 'x=' + values.x + '; y=' + values.y + '; r=' + values.r + "\n";
	//еще надо чтобы писало время и попал/не попал
	
	//console.log(responce); 
}

document.addEventListener("DOMContentLoaded", () => {
  console.log("Hello World!");  
  
  let mainForm = document.getElementById('main');  
  mainForm.addEventListener('submit', saveArticle);
  

});