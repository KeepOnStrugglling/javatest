# coding=utf-8
import sys
import json

columnStandard=[]

class Result:
    def __init__(self, deviceVersion,devicePatch,neVPList):
        self.deviceVersion = deviceVersion
        self.devicePatch = devicePatch
        self.neVPList = neVPList

def object2dict(obj):
    d={}
    d.update(obj.__dict__)
    return d

def getEqName(data):
    bIndex=data.find('/*')
    eIndex=data.find('*/')
    eqName=data[bIndex+16:eIndex]
    return eqName

def getColumnStandard(index,dataArr):
    data=dataArr[index]
    global columnStandard
    columnStandard=data.split()
    return columnStandard

def getObjByValue(index,headfilter,dataArr):
    datas=dataArr[index].split()
    m={}
    for i in range(len(headfilter)):
        m[headfilter[i]]=datas[i]
    return m

def main(report):
    eqName="" #网元名字
    deviceVersion=""
    devicePatch=""
    headfilter=[]
    d={}
    neVPList=[]
    
    dataArr=report.splitlines();
    k=0
    for i in range(len(dataArr)):
        if k==len(dataArr):
            break
        if dataArr[k].find('MENAME:')!=-1:
            eqName=getEqName(dataArr[i])
        if dataArr[k].find('操作结果如下')!=-1:
            k+=2
            headfilter=getColumnStandard(k,dataArr)
            k+=2
        if dataArr[k].find('结果个数 = ')!=-1:
            headfilter=[]
        if headfilter!=[]:
            dataMap=getObjByValue(k,headfilter,dataArr)
            if eqName==dataMap['网元名称']:
                deviceVersion=dataMap['网元版本']
                devicePatch=dataMap['网元补丁版本']
            else:
                d['deviceName']=dataMap['网元名称']
                d['deviceVersion']=dataMap['网元版本']
                d['devicePatch']=dataMap['网元补丁版本']
                neVPList.append(d)
        k+=1
    result=Result(deviceVersion,devicePatch,neVPList)
    print(json.dumps(result, default=object2dict, ensure_ascii=False))
    return json.dumps(result, default=object2dict, ensure_ascii=False)

main(sys.argv[1])

main("+++    CGP/*MEID:0 MENAME:GD_GZ_CGP_ISCSCF101_HW_V*/        2019-11-19 10:51:42+08:00\n" +
"O&M    #179523\n"+
"%%/*1843884 MEID=000*/LST ME:;%%\n" +
"RETCODE = 0  操作成功\n" +
"\n" +
"操作结果如下\n" +
"------------\n" +
" 域标识  子网标识  网元ID  网元名称                  网元类型  网元版本           网元制造商名称  网元自定义状态  网元本地名称  网元锁定状态  网元国家码  网元接口ID                  网元语言  网元补丁版本            网元模式\n" +
"\n" +
" 1       0         0       GD_GZ_CGP_ISCSCF101_HW_V  CGP网元   V200R019C10SPC500  HUAWEI          正常            NULL          解锁          NULL        CGPV200R019C10SPC505_S3_M3  中文      CGP_V200R019C10SPC505   云虚拟站点管理\n" +
" 1       0         5       GD_GZ_ISCSCF101_HW_V      CSCF网元  V500R019C10SPC200  HUAWEI          正常            NULL          解锁          NULL        CSCFV500R019C10SPC200LICI   中文      CSCF_V500R019C10SPH201  NULL\n" +
"(结果个数 = 2)\n" +
"\n" +
"---    END")