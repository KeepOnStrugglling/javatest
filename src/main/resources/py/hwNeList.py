# coding=utf-8
import sys
import json
import re

columnStandard=[]

class Net:
    def __init__(self, equipmentType,equipmentName,ip):
        self.equipmentType = equipmentType
        self.equipmentName = equipmentName
        self.ip = ip
        
def object2dict(obj):
    d={}
    d.update(obj.__dict__)
    return d

def getColumnStandardByRe(index,dataArr):
    data=dataArr[index]
    global columnStandard
    columnStandard=re.split(r"  +",data)
    return columnStandard

def getObjByValue(index,headfilter,dataArr):
    datas=dataArr[index].split()
    m={}
    for i in range(len(headfilter)):
        m[headfilter[i]]=datas[i]
    return m

def getNeType(neType):
    if neType.find('ATSNE')!=-1:
        return 'ATS'
    if neType.find('CCFNE')!=-1:
        return 'CCF'
    if neType.find('CGPOMUNE')!=-1:
        return 'CGP'
    if neType.find('CSCFNE')!=-1:
        return 'CSCF'
    if neType.find('MRP')!=-1:
        return 'MRFP'
    if neType.find('SE2900')!=-1:
        return 'SBC'
    if neType.find('SPGNE')!=-1:
        return 'SPG'
    if neType.find('UIMNE')!=-1:
        return 'UT'
    if neType.find('USCDBNE')!=-1:
        return 'UT_USCDB'

def main(report):
    headfilter=[]
    neList=[]
    neList2=[]
    
    dataArr=report.splitlines()
    k=0
    for i in range(len(dataArr)):
        if k==len(dataArr):
            break
        if dataArr[k].find('LST NE Info')!=-1:
            k+=3
            headfilter=getColumnStandardByRe(k,dataArr)
            k+=2
        if dataArr[k]=='':
            headfilter=[]
        if headfilter!=[]:
            dataMap=getObjByValue(k,headfilter,dataArr)
            neType=getNeType(dataMap['NE Type'])
            neName=dataMap['NE Name']
            neIp=dataMap['IP address']
            net=Net(neType,neName,neIp)
            if neType=='CGP':
                neList.append(net)
            else:
                neList2.append(net)
        k+=1
    neList.extend(neList2) #保证CGP网元在前
    listJson=json.dumps(neList, default=object2dict, ensure_ascii=False)
    print (listJson)
    return listJson

main(sys.argv[1])

