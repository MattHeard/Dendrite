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

function changeFormat(select, val) {
  if (val != "") {
    if (select === "size") {
      $("body")
          .removeClass("hugeSize")
          .removeClass("largeSize")
          .removeClass("mediumSize")
          .removeClass("smallSize")
          .addClass(val.toLowerCase() + "Size");
    } else if (select === "type") {
      if (val.toLowerCase() === "sans") {
        val = "sansSerif";
      }
      $("body")
          .removeClass("serifTypeface")
          .removeClass("sansSerifTypeface")
          .removeClass("monospaceTypeface")
		  .removeClass("cursiveTypeface")
		  .removeClass("fantasyTypeface")
		  .addClass(val.toLowerCase() + "Typeface");
    } else if (select === "colour") {
	  $("body")
	      .removeClass("defaultColour")
	      .removeClass("charcoalColour")
          .removeClass("blackColour")
          .removeClass("greyColour")
          .removeClass("blueColour")
          .removeClass("greenColour")
          .removeClass("redColour")
          .addClass(val.toLowerCase() + "Colour");
    } else if (select === "spacing") {
      $("body")
          .removeClass("hugeSpacing")
          .removeClass("largeSpacing")
          .removeClass("mediumSpacing")
          .removeClass("smallSpacing")
          .addClass(val.toLowerCase() + "Spacing");
    } else if (select === "align") {
      $("body")
          .removeClass("leftAlignment")
          .removeClass("rightAlignment")
          .removeClass("centreAlignment")
          .removeClass("justifyAlignment")
          .addClass(val.toLowerCase() + "Alignment");
    } else if (select === "theme") {
      $("body")
          .removeClass("lightTheme")
          .removeClass("darkTheme")
          .removeClass("sepiaTheme")
          .removeClass("lovelyTheme")
          .addClass(val.toLowerCase() + "Theme");
    }
  }
}

var fmtBtnSelector = "#formatMenu > .select > button";

function initFormatMenu() {
  $("#formatBar").show();
  $(fmtBtnSelector).click(function() {
    var dimension = $(this).text().toLowerCase();
    var menu = "#" + dimension + "Menu";
    
	if ($(this).hasClass("pressed")) {
      $(this).removeClass("pressed");
      $(menu).hide();
	} else {
	  $(this).addClass("pressed");
      $(menu).show();
	}
  });
  $("#formatMenu > .menu > ul > li").click(function() {
    var menu = $(this).parent().attr("id");
    var menuStrLen = menu.length;
    var dimension = menu.substring(0, menuStrLen - "menu".length);
    var text = $(this).text().toLowerCase();
    changeFormat(dimension, text);
    $(fmtBtnSelector).removeClass("pressed");
    $("#formatMenu > .menu > ul").hide();
  })
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
  initFormatMenu();
}