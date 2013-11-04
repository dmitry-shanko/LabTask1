function deleteGroupOfNews() {
	var chks = document.getElementsByName('selectedItems');
	for ( var i = 0; i < chks.length; i++) {
		if (chks[i].checked) {
			return confirmDialog();
		}
	}
	alert(notChecked);
	return false;
}

function confirmDialog() {
	var answer = confirm(deleteDialog);
	if (answer) {
		return true;
	} else {
		return false;
	}
}

function validateAddEditNewsForm(form) {
	var element, elementName, elementValue;
	var errorList = [];
	for ( var i = 0; i < form.elements.length; i++) {
		element = form.elements[i];
		elementName = element.nodeName.toLowerCase();
		elementValue = element.value;
		if (elementName == "input")
		{			
			if (element.name == "dateString") 
			{
				expr = new RegExp(datePattern);
				if (!(elementValue.match(expr))) 
				{
					errorList.push(5);
				} 
				else 
				{
					if (elementValue == "") 
					{
						errorList.push(2);
					}
				}
			}
		} 
		else 
		{
			if (elementName == "textarea") 
			{
				if (element.name == "news.title") 
				{
					if (elementValue == "") 
					{
						errorList.push(1);
					} 
					else
					{
						if (elementValue.length > 100) 
						{
							errorList.push(6);
						}
					}
				}
				if (element.name == "news.brief") 
				{
					if (elementValue == "") 
					{
						errorList.push(3);
					} 
					else 
					{
						if (elementValue.length > 200) 
						{
							errorList.push(7);
						}
					}
				}
				if (element.name == "news.content") 
				{
					if (elementValue == "") 
					{
						errorList.push(4);
					} 
					else 
					{
						if (elementValue.length > 2000) 
						{
							errorList.push(8);
						}
					}
				}
			}
		}
	}
	if (errorList.length == 0) {
		return true;
	} else {
		var errorMessage = "";
		for (i = 0; i < errorList.length; i++) {
			errorMessage += addEditErrorMessages[errorList[i]] + "\n";
		}
		alert(errorMessage);
	}
	return false;
}