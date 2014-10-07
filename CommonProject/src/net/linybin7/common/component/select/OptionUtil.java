package net.linybin7.common.component.select;

import java.util.ArrayList;
import java.util.List;



/**
 * 

 *
 */
public class OptionUtil {
	private OptionUtil(){
		
	}
	
	public static List<Option> toOptions(String[][] optionArray){
		List<Option> options = new ArrayList<Option>();
		for(String[] item : optionArray){
			Option option = new Option();
			option.setValue(item[0]);
			option.setLabel(item[1]);
			options.add(option);
		}
		return options;
	}
	
	public static List<Option> toOptions(List<String[]> optionList){
		List<Option> options = new ArrayList<Option>();
		for(String[] item : optionList){
			Option option = new Option();
			option.setValue(item[0]);
			option.setLabel(item[1]);
			options.add(option);
		}
		return options;
	}
	
	public static List<Option> toOptions1(List<String> optionList){
		List<Option> options = new ArrayList<Option>();
		for(String item : optionList){
			Option option = new Option();
			option.setValue(item);
			option.setLabel(item);
			options.add(option);
		}
		return options;
	}
	
	public static Option getOptionByValue(List<Option> optionList, String value) {
		for (Option option : optionList) {
			if (value.equals(option.getValue()))
				return option;
		}
		return new Option();
	}
}
