package net.linybin7.core.web.component.util;

import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.component.button.Button;
import net.linybin7.core.frame.bo.Func;


/**
 * 按钮工具类
 * 
 * 
 */
public final class ButtonUtil {
	private ButtonUtil() {

	}

	public static List<Button> buildButtons(List funcs) {
		List<Button> buttons = new ArrayList<Button>();
		if (funcs == null) {
			return buttons;
		}
		for (int i = 0; i < funcs.size(); i++) {
			Func func = (Func) funcs.get(i);
			Button button = new Button();
			button.setFunction(func.getLink());
			button.setLabel(func.getFuncName());
			button.setId("btn" + i);
			button.setFuncProp(func.getFuncProp());
			button.setFuncCode(func.getFuncCode());
			buttons.add(button);
		}
		return buttons;
	}
}
