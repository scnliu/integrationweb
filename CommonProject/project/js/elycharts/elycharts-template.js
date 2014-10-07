var _ijk = 0;

$.elycharts.templates['tmpl_line'] = {
	type : "line",
	margins : [ 30, 40, 20, 40 ],
	style : {
		//background : "url(http://elycharts.com/sites/elycharts.com/files/sky.jpg)"
		//opacity : 0.8,			
		"background-color" : "white"
	},
	features : {
		grid : {
			draw : true,
			forceBorder : true,
			props : {
			    "stroke-dasharray" : "-"
			}
		},
		legend : {
			horizontal : true,
			width : 300,
			height : 20,
			x : 40,
			y : 5,
			dotType : "rect",
			dotProps : {
				stroke : "white",
				"stroke-width" : 0
			},
			borderProps : {
				//opacity : 0.3,
				fill : "#f5f5f5",
				"stroke-width" : 0,
				"stroke-opacity" : 0
			}
		}
	},
	defaultSeries : {
		plotProps : {
			"stroke-width" : 1
		},
		dot : true,
		dotProps : {
			stroke : "white",
			"stroke-width" : 1,
			size: 2
		},
		tooltip : {	        
	        width: 110, height: 55, 
	        roundedCorners: 0 
		},
		startAnimation: { // use an animation to start plotting the chart
            active: true,
            type: "avg", // start from the average line.
            speed: 1000, // animate in 1 second.
            easing: ">"
        },
        stepAnimation: { // defines an animation for data updates
            speed: 1000,
            delay: 0,
            easing: '<>'
        },
        highlight: {
            scaleSpeed: 1000, // do not animate the dot scaling. instant grow.
            scaleEasing: '',
            scale: 1.2, // enlarge the dot on hover
            newProps: {
                opacity: 1 // show dots on hover
            }
        }
	},
	defaultAxis : {
		labels : true
	},
	axis : {		
		x : {
			//labelsRotate : 45,
			labelsProps : {
				font : "9px Verdana"
			}
		},
		r : {
			//max : 0.1,
			//suffix : "%"
		}
	}
}

$.elycharts.templates['tmpl_bar'] = {
	type : "line",
	margins : [ 30, 40, 5, 40 ],
	style : {
		//background : "url(http://elycharts.com/sites/elycharts.com/files/sky.jpg)"
		//opacity : 0.8,			
		"background-color" : "white"
	}, 
	defaultAxis : {
		labels : true,
		suffix : "%"
	},
	features : {
		grid : {
			draw : [ true, true ],
			forceBorder : true,
			 props : {
			    "stroke-dasharray" : "-"
			 }
			/*
			evenHProps : {
				// fill : "yellow",
				opacity : 0.2
			},
			oddHProps : {
				// fill : "green",
				opacity : 0.2
			}*/
		},
		legend : {
			horizontal : true,
			width : 100,
			height : 20,
			x : 40,
			y : 5,
			dotType : "rect",
			dotProps : {
				stroke : "white",
				"stroke-width" : 0
			},
			borderProps : {
				//opacity : 0.3,
				fill : "#f5f5f5",
				"stroke-width" : 0,
				"stroke-opacity" : 0
			}
		}
	},
	defaultSeries : {
		type : "bar",
		plotProps : {
			opacity : 0.6
		},
		//highlight : {
		//	overlayProps : {
		//		fill : "white",
		//		opacity : 0
		//	}
		//},
		//startAnimation : {
		//	active : true,
		//	type : "grow"
		//},
		tooltip : {
			//frameProps : true,
			width: 110, height: 55, 
			//offset : [ 10, 0 ],
			//contentStyle : "font-weight: bold",
			roundedCorners: 0
		}
	},
	barMargins : 10
}
