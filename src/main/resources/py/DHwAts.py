import UtilsHw as hwUtil
import numpy as np

daily_prices = np.array(
    [
        (4,3,3,1),
        (5,4,3,6),
        (6,3,2,7),
        (3,9,7,4),
        (8,4,6,3),
        (8,3,3,9)],
    dtype=[('MSFT','float'),('CSCO','float'),('GOOG','float'),('F','float') ]
    )

def proc(ne,fb):
    hwUtil.transferToEnFb(fb)

#     var returnMsgObj = {};
# //请对报文reportMsg进行操作
#   var dataArr = reportMsg.split('\\r\\n');
#   var msg = '';
# 	var equipName = '';
#   var gqsj = '';
#   var yxms = '';
#   var alarmList=[];
# 	for(var i = 0;i < dataArr.length;i++) {
# 		if(dataArr[i].indexOf('License过期时间')!=-1) {
# 			var pArr = dataArr[i].trim().split('  =  ');
#       gqsj = pArr[1];
#       if(gqsj == '永久有效' || gqsj.indexOf('9999')!=-1) {
				
#     	} else {
#         returnMsgObj.status = 1;
#         alarmList.push('License过期时间：非永久生效');
#       }
# 		}
#     break;
#   }
# 	for(var i = 0;i < dataArr.length;i++) {
#     if(dataArr[i].indexOf('运行模式')!=-1) {
# 			var yArr = dataArr[i].trim().split('  =  ');
#       yxms = yArr[1];
#       if(yxms == '正常') {
				
#     	} else {
#     		returnMsgObj.status = 1;
#         alarmList.push('运行模式：不正常;');
#       }
# 		}
#     break;
# 	}
# 	if(alarmList.length > 0){
# 	  returnMsgObj.textMsg += '<pre>';
#    	returnMsgObj.textMsg += "【License状态检查】：<span style='color:red'>不通过</span>。License检查问题如下：<br>";
#     for(var z=0;z<alarmList.length;z++){
#     	returnMsgObj.textMsg+="      ("+(z+1)+")"+alarmList[z]+"<br>";
#     }
#     returnMsgObj.textMsg += '</pre>';
# 	}else{
# 	   returnMsgObj.textMsg += "<pre>【License状态检查】：<span style='color:green'>通过。</span></pre>";
# 	}
