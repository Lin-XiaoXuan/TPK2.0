// 工具包方法：该工具包用于封装一些常用的方法

/** 
 * <p>身份证验证方法</p>
 * @param idCard 身份证
 * @return {boolean}
 * 	-- 如果身份证合法返回 true
 * 	-- 身份证不合法返回 false
 * */
function idcardVerify(idCard){
	if(idCard.length != 18){
		return false;
	}else{
		return true;
	}
}

/**
 * <p>确认密码</p>
 * @param {firstPass,secondPass} 
 * @return {booleam}
 * -- 密码一致返回 true
 * -- 密码不一致返回fasle
 */

function confirmPass(firstPass,secondPass){
	if(firstPass == secondPass){
		return true;
	}else{
		return false;
	}
}

/**
 * <p>手机位数确认</p>
 * @param {phone} 
 * @return {booleam}
 * -- 手机验证正确 true
 * -- 手机验证错误fasle
 */
function confirmPhone(phone){
	if(phone.length != 11){
		return false;
	}
	return true;
}

/**
 * <p>判断备注信息的位数</p>
 * @param {phone} 
 * @return {str || boolean}
 */
function confirmRemark(remark){
	if(remark.length > 225){
		return false;
	}
	return true;
}

/**
 * 拼接时间数组
 * yyyy-MM-dd hh:mm:ss
 * 
 *@param {array} ArrayList 
 *@return {String}
 */
function timeFormat(list){
	//yyyy-MM-dd hh:mm:ss
	let result = "yyyy-MM-dd hh:mm:ss";
	let formatArray = ['yyyy','MM',"dd","hh","mm","ss"];
	
	for(let i = 0;i < formatArray.length;i++){
		result = result.replace(formatArray[i],list[i]);
	}
	
	return result;
}
