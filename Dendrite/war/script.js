function toggleFormatButton(name) {
	var button = document.getElementById(name + "Button");
	button.className = (button.className === 
			'formatIcon' ? 'formatIconPressed' : 'formatIcon');
}

function unsetFormatButton(name) {
	var button = document.getElementById(name + "Button");
	button.className = 'formatIcon';
}

function unsetFormatBar(name) {
	var bar = document.getElementById(name + "PickerBar");
	bar.style.display = "none";
}

function unsetOtherFormatButtonsAndPickerBars(name) {
	var others = [ "size", "type", "colour", "dayNight", "alignment", "spacing",
	               "theme" ];
	var len = others.length;
	for (var i = 0; i < len; i++) {
		if (others[i] !== name) {
			unsetFormatButton(others[i]);
			unsetFormatBar(others[i]);
		}
	}
}

function toggleFormatPicker(name) {
	var pickerBar = document.getElementById(name + "PickerBar");
	pickerBar.style.display =
			(pickerBar.style.display === "block" ? "none" : "block");
}

function clickFormatButton(name) {
	toggleFormatButton(name);
	toggleFormatPicker(name);
	unsetOtherFormatButtonsAndPickerBars(name);
}

var lineHeight = "1.5em";

function pickSize(value) {
	var elements = document.getElementsByClassName("modifiableText");
	for (var i = 0; i < elements.length; ++i) {
	    var element = elements[i];  
		element.style.fontSize = value + "em";
		element.style.lineHeight = lineHeight;
	}
}

function pickType(value) {
	var elements = document.getElementsByClassName("modifiableText");
	for (var i = 0; i < elements.length; ++i) {
	    var element = elements[i];  
		element.style.fontFamily = value;
	}
}

function pickColour(value) {
	var elements = document.getElementsByClassName("modifiableText");
	for (var i = 0; i < elements.length; ++i) {
	    var element = elements[i];  
		element.style.color = "#" + value;
	}
}

function pickDayNight(value) {
	var theme = $('select#themePicker').val();
	if (value === "day") {
		pickLightTheme(theme);
	} else {
		pickDarkTheme(theme);
	}
}

function pickAlignment(value) {
	var elements = document.getElementsByClassName("modifiableText");
	for (var i = 0; i < elements.length; ++i) {
	    var element = elements[i];  
		element.style.textAlign = value;
	}
}

function pickSpacing(value) {
	lineHeight = value + "em";
	var elements = document.getElementsByClassName("modifiableText");
	for (var i = 0; i < elements.length; ++i) {
	    var element = elements[i];  
		element.style.lineHeight = lineHeight;
	}
}

function pickTheme(value) {
	var dayNight = $('select#dayNightPicker').val();
	if (dayNight === 'day') {
		pickLightTheme(value);
	} else {
		pickDarkTheme(value);
	}
}

function pickLightTheme(value) {
	$('.formatIcon').css('-webkit-filter', 'invert(100%)');
	$('.formatIconPressed').css('-webkit-filter', 'invert(100%)');
	
	if (value === "greyscale") {
		$('body').css('color', '#111');
		$('body').css('backgroundColor', '#fefefe');
		$('a').css('color', '#666');
		$('#headerBar').css('backgroundColor', '#f0f0f0');
		$('#headerBar').css('borderColor', '#e0e0e0');
		$('#footerBar').css('backgroundColor', '#f0f0f0');
		$('#footerBar').css('borderColor', '#e0e0e0');
		$('#formatBar').css('backgroundColor', '#999');
		$('#formatBar').css('borderColor', '#888');
		$('#logoImage').css('-webkit-filter', 'invert(0%) hue-rotate(0deg) saturate(100%) sepia(0%)');
	} else if (value === "sepia") {
		$('body').css('color', '#120f09');
		$('body').css('backgroundColor', '#fcfcf0');
		$('a').css('color', '#6e5c41');
		$('#headerBar').css('backgroundColor', '#f2edd8');
		$('#headerBar').css('borderColor', '#e2ddc8');
		$('#footerBar').css('backgroundColor', '#f2edd8');
		$('#footerBar').css('borderColor', '#e2ddc8');
		$('#formatBar').css('backgroundColor', '#806e4f');
		$('#formatBar').css('borderColor', '#6e5c41');
		$('#logoImage').css('-webkit-filter', 'invert(0%) hue-rotate(0deg) saturate(100%) sepia(50%)');
	} else if (value === "lovely") {
		$('body').css('color', '#910034');
		$('body').css('backgroundColor', '#FFF1F6');
		$('a').css('color', '#C20045');
		$('#headerBar').css('backgroundColor', '#FFD6E4');
		$('#headerBar').css('borderColor', '#FFC8DB');
		$('#footerBar').css('backgroundColor', '#FFD6E4');
		$('#footerBar').css('borderColor', '#FFC8DB');
		$('#formatBar').css('backgroundColor', '#F7669A');
		$('#formatBar').css('borderColor', '#F64D89');
		$('#logoImage').css('-webkit-filter', 'invert(0%) hue-rotate(0deg) saturate(100%) sepia(0%)');
	}
}

function pickDarkTheme(value) {
	$('.formatIcon').css('-webkit-filter', 'invert(10%)');
	$('.formatIconPressed').css('-webkit-filter', 'invert(10%)');
	
	if (value === "greyscale") {
		$('body').css('color', '#eee');
		$('body').css('backgroundColor', '#010101');
		$('a').css('color', '#999');
		$('#headerBar').css('backgroundColor', '#0f0f0f');
		$('#headerBar').css('borderColor', '#1f1f1f');
		$('#footerBar').css('backgroundColor', '#0f0f0f');
		$('#footerBar').css('borderColor', '#1f1f1f');
		$('#formatBar').css('backgroundColor', '#666');
		$('#formatBar').css('borderColor', '#555');
		$('#logoImage').css('-webkit-filter', 'invert(0%) hue-rotate(0deg) saturate(100%) sepia(0%)');
	} else if (value === "sepia") {
		$('body').css('color', '#EBE0CC');
		$('body').css('backgroundColor', '#1F1400');
		$('a').css('color', '#CCB280');
		$('#headerBar').css('backgroundColor', '#2E1F00');
		$('#headerBar').css('borderColor', '#201600');
		$('#footerBar').css('backgroundColor', '#2E1F00');
		$('#footerBar').css('borderColor', '#201600');
		$('#formatBar').css('backgroundColor', '#775E29');
		$('#formatBar').css('borderColor', '#6B5525');
		$('#logoImage').css('-webkit-filter', 'invert(0%) hue-rotate(0deg) saturate(100%) sepia(50%)');
	} else if (value === "lovely") {
		$('body').css('color', '#FFBAD2');
		$('body').css('backgroundColor', '#49001A');
		$('a').css('color', '#F7669A');
		$('#headerBar').css('backgroundColor', '#610022');
		$('#headerBar').css('borderColor', '#79002B');
		$('#footerBar').css('backgroundColor', '#610022');
		$('#footerBar').css('borderColor', '#79002B');

		$('#formatBar').css('backgroundColor', '#A9003C');
		$('#formatBar').css('borderColor', '#C20045');
		
		$('#logoImage').css('-webkit-filter', 'invert(100%) hue-rotate(50deg) saturate(150%) sepia(0%)');
	}
}

function showFormatBarElement() {
	var formatBar = document.getElementById("formatBar");
	formatBar.style.display = "block";
}

function showFormatMenuElement() {
	var formatBar = document.getElementById("formatMenu");
	formatBar.style.display = "block";
}

function showFormatBar() {
	showFormatBarElement();
	showFormatMenuElement();
}