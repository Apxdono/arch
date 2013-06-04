var app = {};

app.sessionExpired = function(data){
	var xml = data.responseXML;
	if(xml){
		var partial = xml.firstChild;
		if(partial.nodeName="partial-response"){
			var ext = partial.firstChild;
			var extType = ext.attributes["type"];
			var extData = ext.attributes["data"];
			if(extType && extData && extType.value=="sessionExpiration" && extData.value=="kmsessionExpired"){
				alert("SESSION EXPIRED WE ARE GOING TO DIE!!!");
				window.location.href = window.location.href ;
			}	
		}

	}
};