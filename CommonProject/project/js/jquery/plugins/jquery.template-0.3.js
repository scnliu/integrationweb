/*
 * jQuery Template plugin 1.3
 *
 * Copyright (c) 2009 Anton Kolodii
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */

;(function($) {

	function Property() {
		this.Name = "";
		this.pos = -1;
		//this.type = "property";
	};
	function PatternEntities() {
		this.str = "";
		//this.type = "entity";
	};

	jQuery.extend( {
		prepare : function(str) {

			var NameList = new Array();
			var i = 0;
			while (true) {
				var sear = str.search(/{(.)*}/gi);
				if (sear == -1)
					break;
				var name = str.substring(sear + 1, str.indexOf("}"));
				if (name.indexOf("foreach:") == -1) {
					var property = new Property();
					property.pos = sear;
					property.Name = name;
					property.type = 'property';
					str = str.replace("{" + name + "}", "");
					NameList[i] = property;
				} else {
					//name = name.substring(8); //why .substring(8), because "foreach:" has 8 symbols and now name has such form "foreach:name"
					var nestedstr = str.substring(str.indexOf("}") + 1, str.indexOf("{/foreach:" + name.substring(8) + "}")); 
					var strEntity = $.prepare(nestedstr);
					strEntity.pos = sear;
					strEntity.Name = name.substring(8);
					strEntity.type = 'entity'; 
					NameList[i] = strEntity;
					str = str.replace(new RegExp("{foreach:" + strEntity.Name + "}.*{\/foreach:" + strEntity.Name + "}", "i"), "");
				}
				i++;
			}
			var entity = new PatternEntities();
			entity.Name = "";
			entity.str = str;
			entity.type = 'root';
			entity.Parameters = NameList;
			return entity;
		},

		filltemplate : function(Entities, data) {

			function insert(str, ins, pos) {
				return str.substring(0, pos) + ins + str.substring(pos);
			}
			;

			function fillTemplate(template, data, NameList) {
				var fultmpl = template;
				for (i = NameList.length - 1; i >= 0; i--) {
					fultmpl = insert(fultmpl, data[NameList[i].Name],
							NameList[i].pos);
				}
				return fultmpl;
			}
			;

			var NameList = Entities.Parameters;
			var paternStr = Entities.str;
			if (NameList && NameList.length > 0) {
				for ( var i = NameList.length - 1; i >= 0; i--) {
					if (NameList[i].type == "entity") {
						if (NameList[i].Name) {
							var eachstr = "";
							$.each(data[NameList[i].Name], function(i, item) {
								eachstr += $.filltemplate(NameList[i], item);
							});
							paternStr = insert(paternStr, eachstr, NameList[i].pos);
						} else {
							var eachstr = "";
							$.each(data, function(j, item) {
								eachstr += $.filltemplate(NameList[i], item);
							});
							paternStr = insert(paternStr, eachstr, NameList[i].pos);
						}
					} else {
						paternStr = insert(paternStr, data[NameList[i].Name], NameList[i].pos);
					}
				}
			} else {
				return paternStr;
			}
			return paternStr;
		},

		stringformat : function(str, data) {
			var entities = $.prepare(str);
			return $.filltemplate(entities, data);
		}
	});
})(jQuery);