var app = {};

$(document).ready(function() {
	$(window).keydown(function(event) {
		if ((event.keyCode == 13)) {
			if (event.target.className == 'o_table_paginator_field') {
				event.target.onchange();
			}
			event.preventDefault();
			return false;
		}
	});
});

app.sessionExpired = function(data) {
	var xml = data.responseXML;
	if (xml) {
		var partial = xml.firstChild;
		if (partial.nodeName = "partial-response") {
			var ext = partial.firstChild;
			var extType = ext.attributes["type"];
			var extData = ext.attributes["data"];
			if (extType && extData && extType.value == "sessionExpiration"
					&& extData.value == "kmsessionExpired") {
				alert("SESSION EXPIRED WE ARE GOING TO DIE!!!");
				window.location.href = window.location.href;
			}
		}

	}
};

app.initClearButton = function(button){
	var btn = $(button).find('em');
	var filter = btn.parent().parent().find('input');
	btn.bind('click',function(){
		filter.val('');
		filter.trigger('change');
		btn.addClass('km-hidden');
	});
	filter.bind('keyup',function(){
		if(!filter.val()){
			btn.addClass('km-hidden');
		} else {
			btn.removeClass('km-hidden');
		}
	});
};


app.showPopupMenu = function(element,popupId){
	var popup = O$(popupId);
	var x = element.offsetLeft;
	var y = element.offsetTop + element.offsetHeight+3;
	popup.showAtXY(x, y);
};

app.prepareExport = function(table,type,exportAll){
	O$('exportTableId').value = table;
	O$('exportType').value = type;
	O$('exportAll').value = exportAll;
};

app.createLink = function(data){
	var div = $('#actions');
	 var exportLink = document.createElement('a');
	 exportLink.setAttribute('href', 'data:text/csv;base64,' + window.btoa(data));
	 exportLink.appendChild(document.createTextNode('report'));
	 div.append(exportLink);
};

