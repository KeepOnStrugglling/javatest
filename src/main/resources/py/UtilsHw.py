from entityFbAtom import FbAtom
import re
import numpy as np
import pandas as pd



def reportMsgSplit(str):
    strArr = str.splitlines()
    return strArr

__p1=re.compile(r'[/][*](.*?)[*][/]', re.S) #挑出报文中的 /*MEID:0 MENAME:GD_GZ_CGP_UT101_HW_V*/   #最小匹配
__p2=re.compile(r'\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}', re.S) #挑出报文中的 2019-11-13 09:05:51
__p3=re.compile(r'[*][/](.*?)[%][%]', re.S)  #挑出命令 %%/*1839079 MEID=005*/DSP SYSRES: RSCL=RES_ALL;%%
__p4=re.compile(r'RETCODE = (.*?)[ ]', re.S)  #挑出命令 RETCODE = 1  非法命令，不能执行。
__p5=re.compile(r'\d (.*)', re.S)  #挑出命令后半部分 RETCODE = 1  非法命令，不能执行。
__p6=re.compile(r'-{5,}', re.S)  #---------------------
__p7=re.compile(r'\S+\s{0,1}\S{0,}\s{0,}', re.S)#解析multi型的标题

#处理思路是，先找到每一条命令，记录开始的位置
def transferToEnFb(str):
    strArr = str.splitlines()
    returnMsg={}
    enFbAtomArr=[]#定义一个报文解析数组
    headEndNum=[] #记录每一段子报文的开始行号

    #找到每一块的头部和尾部
    for i in range(0,len(strArr)):
        if "+++"==strArr[i][:3]: #如果判断道是子报文的开头
            headEndNum.append(i)

        if "---"==strArr[i][:3] and "END"==strArr[i][-3:]: 
            if len(headEndNum)>0:    # 为了避免报文头部不完整
                headEndNum.append(i)


    #为了避免报文尾部不完整，那么头尾列表将会不能被2整除，此时，删去最后一个元素
    if len(headEndNum) % 2 != 0:
        returnMsg.update(error="报文不完整。")
        headEndNum=headEndNum[0:-1]

    #利用numpy转为n*2的数组
    headEndNdarr = np.array(headEndNum)
    headEndNdarr=headEndNdarr.reshape(len(headEndNum)//2,2)

    for rowid in headEndNdarr:
        enfb=FbAtom() #初始化一个报文原子
        index = rowid[0]
        while index<=rowid[1]:
            row=strArr[index]
            
            if index==0:  #解析第一行代码+++    CGP/*MEID:0 MENAME:GD_GZ_CGP_UT101_HW_V*/        2019-11-13 09:05:51+08:00
                enfb.meName=re.findall(__p1, row)[0].split(":")[-1]
                enfb.procTime = re.findall(__p2, row)[0]

            if "%%"==row[:2] and "%%"==row[-2:]:  #解析命令名称 %%/*1839079 MEID=005*/DSP SYSRES: RSCL=RES_ALL;%%
                enfb.command=re.findall(__p3, row)[0]
            
            if "RETCODE" == row[:7]:  #解析命令名称命令是否执行成功  RETCODE = 1  非法命令，不能执行。
                enfb.procStatus=re.findall(__p4, row)[0]
                if enfb.procStatus=="1":
                    enfb.procErrorMsg=re.findall(__p5, row)[0]

            if "-----" == row[:5]:  #找到： ----------------
                #找到子报文的标题
                if strArr[index-1][-2:] == "如下": 
                    enfb.fbAtomName = strArr[index-1][:-2]
                else:
                    enfb.fbAtomName = strArr[index-1]
                
                #判断子报文的类型，判断规则：如果虚线接下来的行有=号，且接下来的第2行非空，则判断为single，否则multi
                if len(strArr[index+2])>0 and  "=" in strArr[index+1]: 
                    enfb.fbAtomType = "single"

                else:
                    enfb.fbAtomType = "multi"
                    


                print(enfb.fbAtomType)



            
            index+=1

        enFbAtomArr.append(enfb)

    print(enFbAtomArr)

def readTitle(titleStr):
    headStr = titleStr.lstrip()+ ' '
    headArr = re.findall(__p7, headStr)
    for i in headArr:
        print(i,len(i),len(i.encode('gbk')))
    return headArr

readTitle(" 资源名称                  资源使用百分比  使用的资源数  资源最大数  模块状态  模块号  资源地址          资源大小  分配数量公式           ")

