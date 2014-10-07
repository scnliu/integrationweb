    dojo.require("dijit.form.Button");
	dojo.require("dijit.form.Select");
	dojo.require("dijit.form.NumberSpinner");
	
	dojo.require("dojox.charting.Chart2D");
	dojo.require("dojox.charting.themes.CubanShirts");
	dojo.require("dojox.charting.widget.Legend");
	
	dojo.require("dojox.charting.action2d.Base");
	dojo.require("dojox.charting.action2d.Highlight");
	dojo.require("dojox.charting.action2d.Magnify");
	dojo.require("dojox.charting.action2d.MoveSlice");
	dojo.require("dojox.charting.action2d.Shake");
	dojo.require("dojox.charting.action2d.Tooltip");	
	dojo.require("dojox.lang.functional.sequence");
	
	dojo.require("dijit.form.Select");
	dojo.require("dijit.form.RadioButton");
		
	function addActions(chart){
	    var actions = [];
		dojox.lang.functional.forEach(actions, ".destroy()");
		actions = [
			new dojox.charting.action2d.Highlight(chart),
			new dojox.charting.action2d.Magnify(chart),
			new dojox.charting.action2d.MoveSlice(chart),
			new dojox.charting.action2d.Tooltip(chart)
			
		];
	}
	
	function changePlot(plotName,cName){
		 //alert(plotName);
	     var type={};
	     var opts={};
		 type[cName] = dijit.byId(plotName).get("value"),opts[cName] = {type: type[cName], gap: 2};
		
	    switch(type[cName]){
	        case "Lines":
	        case "StackedAreas":
	            opts[cName].markers = true;
	            break;
	        case "Pie":
	            opts[cName].radius = 150;
	            break;
	    }
	    
		chart[cName].addPlot("default", opts[cName]);
	    addActions(chart[cName]);
		chart[cName].render();
		legend[cName].refresh();
	};
	fireEvent = function(type){
	    chart['$cName'].fireEvent(
	        dijit.byId("series").get("value"),
	        type,
	        dijit.byId("index").get("value")
	    );
	}
