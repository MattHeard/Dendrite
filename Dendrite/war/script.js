function clickFormatButton(name) {
	var buttonName = "#" + name + "Button";
	var wasPreviouslySet = $(buttonName).hasClass("pressed");
	$('.formatIcon').removeClass("pressed");
	if (wasPreviouslySet === false) {
		$(buttonName).addClass("pressed");
	}
	
	var idName = "#" + name + "Dropdown";
	var wasOpen = $(idName).hasClass("visible");
	$('.dropdown').removeClass("visible");
	if (wasOpen === false) {
		$(idName).addClass("visible");
	}
}

function removeCurrentModifiableTextStyle(classes) {
	for (i = 0; i < classes.length; i++) {
		if ($('.modifiableText').hasClass(classes[i])) {
			$('.modifiableText').removeClass(classes[i]);
			break;
		}
	}
}

var themes = [ "Light", "Dark", "Sepia", "Lovely" ];

function setDropdownOptionAsSelected(dimension, value) {
	if (dimension === "size") {
		var sizes = [ "Huge", "Large", "Medium", "Small" ];
		for (var i = 0; i < sizes.length; i++) {
			var idName = "#size" + sizes[i] + "DropdownOption";
			$(idName).removeClass("selected");
		}
	} else if (dimension === "type") {
		var types = [ "Serif", "Sans-serif", "Monospace", "Fantasy",
		              "Cursive" ];
		for (var i = 0; i < types.length; i++) {
			var idName = "#type" + types[i] + "DropdownOption";
			$(idName).removeClass("selected");
		}
	} else if (dimension === "colour") {
		var colours = [ "Default", "Charcoal", "Black", "Grey", "Blue", "Green",
		                "Red" ];
		for (var i = 0; i < colours.length; i++) {
			var idName = "#colour" + colours[i] + "DropdownOption";
			$(idName).removeClass("selected");
		}
	} else if (dimension === "align") {
		var alignments = [ "Left", "Right", "Center", "Justify" ];
		for (var i = 0; i < alignments.length; i++) {
			var idName = "#align" + alignments[i] + "DropdownOption";
			$(idName).removeClass("selected");
		}
	} else if (dimension === "spacing") {
		var spacings = [ "Huge", "Large", "Medium", "Small" ];
		for (var i = 0; i < spacings.length; i++) {
			var idName = "#spacing" + spacings[i] + "DropdownOption";
			$(idName).removeClass("selected");
		}
	} else if (dimension === "theme") {
		for (var i = 0; i < themes.length; i++) {
			var idName = "#theme" + themes[i] + "DropdownOption";
			$(idName).removeClass("selected");
		}
	}
	
	var idName = "#" + dimension + value + "DropdownOption";
	$(idName).addClass("selected");
}

function removeCurrentTheme() {
	for (var i = 0; i < themes.length; i++) {
		var className = "theme" + themes[i];
		$("body").removeClass(className);
	}
}

var sizes = [ "sizeHuge", "sizeLarge", "sizeMedium", "sizeSmall" ];
var types = [ "fontTypeSerif", "fontTypeSansSerif", "fontTypeMonospace",
              "fontTypeFantasy", "fontTypeCursive" ];
var colours = [ "fontColourDefault", "fontColourCharcoal", "fontColourBlack",
                "fontColourGrey", "fontColourBlue", "fontColourGreen",
                "fontColourRed" ];
var alignments = [ "alignmentLeft", "alignmentRight", "alignmentCenter",
                "alignmentJustify" ];
var spacings = [ "spacingHuge", "spacingLarge", "spacingMedium",
                 "spacingSmall" ];

function pick(dimension, value) {
	if (dimension === "size") {
		removeCurrentModifiableTextStyle(sizes);
		$('.modifiableText').addClass("size" + value);
	} else if (dimension === "type") {
		removeCurrentModifiableTextStyle(types);
		$('.modifiableText').addClass("fontType" + value);
	} else if (dimension === "colour") {
		removeCurrentModifiableTextStyle(colours);
		$('.modifiableText').addClass("fontColour" + value);
	} else if (dimension === "align") {
		removeCurrentModifiableTextStyle(alignments);
		$('.modifiableText').addClass("alignment" + value);
	} else if (dimension === "spacing") {
		removeCurrentModifiableTextStyle(spacings);
		$('.modifiableText').addClass("spacing" + value);
	} else if (dimension === "theme") {
		removeCurrentTheme();
		$("body").addClass("theme" + value);
	}

	setDropdownOptionAsSelected(dimension, value);
	$('.formatIcon').removeClass("pressed");
	$('.dropdown').removeClass("visible");
}

function pickTheme(value) {
	$('.formatIcon').css('-webkit-filter', 'invert(100%)');
	$('.formatIconPressed').css('-webkit-filter', 'invert(100%)');
	
	if (value === "light") {
		$('body').css('color', '#111');
		$('body').css('backgroundColor', '#fefefe');
		$('a').css('color', '#666');
		$('#headerBar').css('backgroundColor', '#f0f0f0');
		$('#headerBar').css('borderColor', '#e0e0e0');
		$('#footerBar').css('backgroundColor', '#f0f0f0');
		$('#footerBar').css('borderColor', '#e0e0e0');
		$('#formatBar').css('backgroundColor', '#999');
		$('#formatBar').css('borderColor', '#888');
		$('#logoImage').css('-webkit-filter', 'sepia(0%)');
	} else if (value === "dark") {
		$('body').css('color', '#eee');
		$('body').css('backgroundColor', '#010101');
		$('a').css('color', '#999');
		$('#headerBar').css('backgroundColor', '#0f0f0f');
		$('#headerBar').css('borderColor', '#1f1f1f');
		$('#footerBar').css('backgroundColor', '#0f0f0f');
		$('#footerBar').css('borderColor', '#1f1f1f');
		$('#formatBar').css('backgroundColor', '#666');
		$('#formatBar').css('borderColor', '#555');
		$('#logoImage').css('-webkit-filter', 'sepia(0%)');
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
		$('#logoImage').css('-webkit-filter', 'sepia(50%)');
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
		$('#logoImage').css('-webkit-filter', 'sepia(0%)');
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

function preventEnterFromSubmittingForms() {
  $("input").not("textarea").bind("keyup keypress", function(e) {
    var code = e.keyCode || e.which;
    if (code == 13) {
      e.preventDefault();
      return false;
    }
  });
}

function initTitleCountNote() {
  $('#title').keyup(function() {
	var numChars = 100 - $('#title').val().length;
    $('#titleCount').text(numChars);
  }).focus(function() {
	$('#titleCountNote').show();
  }).focusout(function() {
	$('#titleCountNote').hide();
  });
  
  $('#title').keyup();
}

function initContentFocus() {
  $('#content').keyup(function() {
	var numChars = 5000 - $('#content').val().length;
    $('#contentCount').text(numChars);
  }).focus(function() {
	$('#contentCountNote').show();
	$('#contentFormattingTips').show();
  }).focusout(function() {
	$('#contentCountNote').hide();
	$('#contentFormattingTips').hide();
  });
  
  $('#content').keyup();
}

function initOptionFocus() {
  var NUM_OPTION_INPUTS = 5;
  for (var i = 0; i < NUM_OPTION_INPUTS; i++) {
    var id = '#option' + i;
    $(id).keyup(function() {
      var numChars = 80 - $(this).val().length;
      $('#optionCount').text(numChars);
    }).focus(function() {
      $('#optionCountNote').show();
      var numChars = 80 - $(this).val().length;
      $('#optionCount').text(numChars);
  	  $('#optionsFormattingTips').show();
    }).focusout(function() {
      $('#optionCountNote').hide();
  	  $('#optionsFormattingTips').hide();
    }).keyup();
  }
}

function initAuthorNameCountNote() {
  $('#authorName').keyup(function() {
	var numChars = 100 - $('#authorName').val().length;
    $('#authorNameCount').text(numChars);
  }).focus(function() {
	$('#authorNameCountNote').show();
  }).focusout(function() {
	$('#authorNameCountNote').hide();
  });
  
  $('#authorName').keyup();
}

function initTextEntryFields() {
  initTitleCountNote();
  initContentFocus();
  initOptionFocus();
  initAuthorNameCountNote();
}

function initLoveButton() {
  $("#love").click(function() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
      if (req.readyState == 4 && req.status == 200) {
    	var count = parseInt(req.responseText);
    	if (count > 0) {
          $("#loveCount").text(count);
    	} else {
    		$("#loveCount").text("");
    	}
        if (IS_NOT_CURRENTLY_LOVED === true) {
          IS_NOT_CURRENTLY_LOVED = false;
        } else {
          IS_NOT_CURRENTLY_LOVED = true;
        }
        love_uri = LOVE_URI_WITHOUT_VAL + IS_NOT_CURRENTLY_LOVED;
      }
    };
    req.open("POST", love_uri, true);
    req.send();
    $("#love").toggleClass("set");
  });
}

function initNotificationDeletion() {
  var CLASS_NAME = "notificationDelete";
  var NOTIFICATION_DELETE_URI_WITHOUT_VAL = "/deleteNotification?id=";
  $("." + CLASS_NAME).click(function() {
    var buttonIndex = $(this).attr("id");
    var notificationIndex = buttonIndex.substring(CLASS_NAME.length);
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
      if (req.readyState == 4 && req.status == 200) {
        var notificationId = "#notification" + notificationIndex;
        var notificationDeleteId = "#notificationDelete" + notificationIndex;
        $(notificationId).fadeOut();
        $(notificationDeleteId).fadeOut();
      }
    };
    var deleteUri = NOTIFICATION_DELETE_URI_WITHOUT_VAL + notificationIndex;
    req.open("POST", deleteUri, true);
    req.send();
  });
}

function changeFollowButtonToUnfollow() {
  $("#follow").unbind("click").attr("id", "unfollow").text("Unfollow");
  initFollowButtons();
}

function changeUnfollowButtonToFollow() {
  $("#unfollow").unbind("click").attr("id", "follow").text("Follow");
  initFollowButtons();
}

function initFollowButtons() {
  $("#follow").click(function() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
      if (req.readyState == 4 && req.status == 200) {
        changeFollowButtonToUnfollow();
      }
    };
    var uri = "/follow?id=" + AUTHOR_ID;
    req.open("POST", uri, true);
    req.send();
  });

  $("#unfollow").click(function() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
      if (req.readyState == 4 && req.status == 200) {
    	  changeUnfollowButtonToFollow();
      }
    };
    var uri = "/unfollow?id=" + AUTHOR_ID;
    req.open("POST", uri, true);
    req.send();
  });
}

function handleRemoveTagClick() {
}

function initTagButtons() {
  $("ul.tagControls > li.add").click(function() {
    $("ul.tagControls > li.ok")
        .add("ul.tagControls > li.select")
        .add("ul.tagControls > li.cancel")
        .show();
    $("ul.tagControls > li.add").hide();
  });
  
  $("ul.tagControls > li.ok").add("ul.tagControls > li.cancel").click(function() {
    $("ul.tagControls > li.add").show();
    $("ul.tagControls > li.ok")
        .add("ul.tagControls > li.select")
        .add("ul.tagControls > li.cancel")
        .hide();
  });
  
  $("ul.tags > li")
      .after()
      .click(function() {
    var tagElement = $(this);
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
      if (req.readyState == 4 && req.status == 200) {
        tagElement.before().fadeOut();
      }
    };
    var tag = $(this).before().text();
    var uri = REMOVE_TAG_URL_WITHOUT_TAG + tag;
    req.open("POST", uri, true);
    req.send();
  });
  
  $("ul.tagControls > li.ok").click(function() {
    var tag = $("ul.tagControls > li > select").val();
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
      if (req.readyState == 4 && req.status == 200) {
    	var tagClass = tag.toLowerCase();
    	var tagName = tag.toUpperCase();
    	var tagElement = $('<li class="' + tagClass + '">' + tagName + '</li>');
    	tagElement.hide();
    	
    	tagElement.after().click(function() {
    	  var req = new XMLHttpRequest();
    	  req.onreadystatechange = function() {
    	    if (req.readyState == 4 && req.status == 200) {
	          tagElement.fadeOut();
		    }
		  };
		  var tag = $(this).before().text();
		  var uri = REMOVE_TAG_URL_WITHOUT_TAG + tag;
		  req.open("POST", uri, true);
		  req.send();
    	});
    	
        $("ul.tags")
            .append(tagElement);
        tagElement.fadeIn();
      }
    };
    var uri = ADD_TAG_URL_WITHOUT_TAG + tag;
    req.open("POST", uri, true);
    req.send();
  });
}

function fixInvisibleWebFont() {
  $(function() {
    $('body').css('zoom',1);
  }); // HACK
}

function initFonts() {
  $(function() {
    var fonts = "<link href='http://fonts.googleapis.com/css?family=Source+Code+Pro:300,400,700|Open+Sans:300italic,700italic,400,700|Merriweather:400,300,300italic,700,700italic|Kalam:400,300,700|Almendra:400,700,400italic,700italic' rel='stylesheet' type='text/css'>";
    $('head').append(fonts);
  });
}

function resizeContactForm() {
  var mainWidth = $("#main").width();
  $("iframe").width(mainWidth);
}

function initResizeContactForm() {
  $(window).resize(resizeContactForm);
  
  resizeContactForm();
}

function init() {
  fixInvisibleWebFont();
  preventEnterFromSubmittingForms();
  initTextEntryFields();
  initLoveButton();
  initNotificationDeletion();
  initFollowButtons();
  initTagButtons();
  initFonts();
  initResizeContactForm();
}