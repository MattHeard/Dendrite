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

function changeLogoTheme(theme) {
  var url = "/static/img/logo/2014-08-20-0/" + theme + "Theme/logo.png";
  $("#logoImage").attr("src", url);
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
      changeLogoTheme(val);
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
      $("ul#formatMenu > li.select > button").removeClass("pressed");
      $("ul#formatMenu > li.menu > ul").hide();
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

function checkBlackTextOnDarkTheme() {
  if ($(".radio_list").length > 0) {
    var colour = $(".radio_list input[name='fontColour']:radio:checked")
        .val()
        .toLowerCase();
    var theme = $(".radio_list input[name='theme']:radio:checked")
        .val()
        .toLowerCase();
    if (colour === "black" && theme === "dark") {
      $("#darkWarning").show();
    } else {
      $("#darkWarning").hide();
    }
  }
}

function initBlackTextOnDarkThemeWarning() {
  $(".radio_list input:radio").change(function() {
    checkBlackTextOnDarkTheme();
  });
  checkBlackTextOnDarkTheme();
}

var selectedAboutTab = "about";
var selectedAuthorTab = "pages_written";
var selectedTab = "";

function showAccordion() {
  $("div.accordion > ul.tabs").hide();
  $("div.accordion > section").show();
  $("div.accordion > section > h2").show();
  $("div.accordion > section > div").hide();
  if (selectedTab !== "") {
    $("div.accordion > section." + selectedTab + " > div").show();
  }
}

function hideAccordion() {
  $("div.accordion > ul.tabs > li").removeClass("selected");
  $("div.accordion > ul.tabs > li." + selectedTab).addClass("selected");
  console.log(selectedTab);
  $("div.accordion > ul.tabs").show();
  $("div.accordion > section").hide();
  $("div.accordion > section > div").show();
  $("div.accordion > section." + selectedTab + " h2").hide();
  $("div.accordion > section." + selectedTab).show();
}

function showAboutAccordion() {
  selectedTab = selectedAboutTab;
  showAccordion();
}

function hideAboutAccordion() {
  if (selectedAboutTab === "") {
    selectedTab = "about";
  } else {
    selectedTab = selectedAboutTab;
  }
  hideAccordion();
}

function initAboutAccordionTabs() {
  $(window).resize(function() {
    if ($(".accordion").hasClass("about")) {
      var width = $("#main").width();
      if (width < 800) {
        showAboutAccordion();
      } else {
        hideAboutAccordion();
      }
    }
  }).resize();
  
  $("div.about.accordion > section > h2").click(function() {
    var prevSelected = selectedAboutTab;
    selectedAboutTab = $(this).text().toLowerCase();
    var section = "div.about.accordion > section." + selectedAboutTab;
    $(section + " > div").toggle();
    $("div.about.accordion > section:not(." + selectedAboutTab + ") > div")
        .hide();
    $('html, body').animate({scrollTop:$(section).offset().top - 20}, 'slow');
    if (prevSelected === selectedAboutTab) {
      selectedAboutTab = "";
    }
  });
  
  $("div.about.accordion > ul.tabs > li").click(function() {
    selectedAboutTab = $(this).text().toLowerCase();
    $("div.about.accordion > ul.tabs > li.selected").removeClass("selected");
    $("div.about.accordion > ul.tabs > li." + selectedAboutTab)
        .addClass("selected");
    $("div.about.accordion > section").hide();
    $("div.about.accordion > section > h2").hide();
    $("div.about.accordion > section." + selectedAboutTab).show();
  });
}

function showAuthorAccordion() {
  selectedTab = selectedAuthorTab;
  showAccordion();
}

function hideAuthorAccordion() {
  if (selectedAuthorTab === "") {
	selectedTab = "pages_written";
  } else {
	selectedTab = selectedAuthorTab;
  }
  hideAccordion();
}

function initAuthorAccordionTabs() {
  $(window).resize(function() {
    if ($(".accordion").hasClass("author")) {
      var width = $("#main").width();
      if (width < 800) {
        showAuthorAccordion();
      } else {
        hideAuthorAccordion();
      }
    }
  }).resize();
  
  $("div.author.accordion > section > h2").click(function() {
    var prevSelected = selectedAuthorTab;
    selectedAuthorTab = $(this).text().toLowerCase();
    if (selectedAuthorTab === "pages written") {
      selectedAuthorTab = "pages_written";
    }
    var section = "div.author.accordion > section." + selectedAuthorTab;
    $(section + " > div").toggle();
    $("div.author.accordion > section:not(." + selectedAuthorTab + ") > div")
        .hide();
    $('html, body').animate({scrollTop:$(section).offset().top - 20}, 'slow');
    if (prevSelected === selectedAuthorTab) {
      selectedAuthorTab = "";
    }
  });
  
  $("div.author.accordion > ul.tabs > li").click(function() {
    selectedAuthorTab = $(this).text().toLowerCase();
    if (selectedAuthorTab === "pages written") {
      selectedAuthorTab = "pages_written";
    }
    $("div.author.accordion > ul.tabs > li.selected").removeClass("selected");
    $("div.author.accordion > ul.tabs > li." + selectedAuthorTab)
        .addClass("selected");
    $("div.author.accordion > section").hide();
    $("div.author.accordion > section > h2").hide();
    $("div.author.accordion > section." + selectedAuthorTab).show();
  });
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
  initBlackTextOnDarkThemeWarning();
  initAboutAccordionTabs();
  initAuthorAccordionTabs();
}