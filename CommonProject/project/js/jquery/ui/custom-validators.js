function phoneValidator(value){
	 var regExp=/((^[0-9]{3,4}[-][0-9]{6,8})|(^[0-9]{3,8})|(^([0-9]{3,4})[0-9]{3,8}))/;
	 if(regExp.test(value))return true;
	return false;
}