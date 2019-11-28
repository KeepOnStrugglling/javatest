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

# main("+++     NMS SERVER        2019-09-10 10:22:34\n"+
#         "O&M     #2304\n"+
#         "%%LST NE:;%%\n"+
#         "RETCODE = 0  Success\n"+
#         "\n"+
#         "LST NE Info\n"+
#         "-----------\n"+
#         "\n"+
#         "NE Type    NE Name      IP address\n"+
#         "\n"+
#         "ATSNE    GD_DG_VOLTEAS201_HW_V    132.98.77.52\n"+
#         "ATSNE    GD_DG_VOLTEAS202_HW_V    132.98.77.68\n"+
#         "ATSNE    GD_DG_VOLTEAS221_HW_V    132.98.77.84\n"+
#         "ATSNE    GD_GZ_VOLTEAS101_HW_V    132.96.44.148\n"+
#         "ATSNE    GD_GZ_VOLTEAS102_HW_V    132.96.44.164\n"+
#         "ATSNE    GD_GZ_VOLTEAS121_HW_V    132.96.44.180\n"+
#         "CCFNE    GD_DG_CCF201_HW_V    132.98.77.52\n"+
#         "CCFNE    GD_DG_CCF202_HW_V    132.98.77.68\n"+
#         "CCFNE    GD_DG_CCF221_HW_V    132.98.77.84\n"+
#         "CCFNE    GD_GZ_CCF101_HW_V    132.96.44.148\n"+
#         "CCFNE    GD_GZ_CCF102_HW_V    132.96.44.164\n"+
#         "CCFNE    GD_GZ_CCF121_HW_V    132.96.44.180\n"+
#         "CGPOMUNE    GD_DG_CGP_ISBC291_HW_V    132.98.76.236\n"+
#         "CGPOMUNE    GD_DG_CGP_ISCSCF201_HW_V    132.98.77.36\n"+
#         "CGPOMUNE    GD_DG_CGP_ISCSCF202_HW_V    132.98.77.44\n"+
#         "CGPOMUNE    GD_DG_CGP_PSBC201_HW_V    132.98.76.228\n"+
#         "CGPOMUNE    GD_DG_CGP_PSBC202_HW_V    132.103.196.166\n"+
#         "CGPOMUNE    GD_DG_CGP_UT201_HW_V    132.98.76.244\n"+
#         "CGPOMUNE    GD_DG_CGP_VOLTEAS201_HW_V    132.98.77.52\n"+
#         "CGPOMUNE    GD_DG_CGP_VOLTEAS202_HW_V    132.98.77.68\n"+
#         "CGPOMUNE    GD_DG_CGP_VOLTEAS221_HW_V    132.98.77.84\n"+
#         "CGPOMUNE    GD_FS_CGP_PSBC401_HW_V    132.104.196.196\n"+
#         "CGPOMUNE    GD_FS_CGP_PSBC402_HW_V    132.104.197.196\n"+
#         "CGPOMUNE    GD_GZ_CGP_ISBC191_HW_V    132.97.76.148\n"+
#         "CGPOMUNE    GD_GZ_CGP_ISCSCF101_HW_V    132.96.44.132\n"+
#         "CGPOMUNE    GD_GZ_CGP_ISCSCF102_HW_V    132.96.44.140\n"+
#         "CGPOMUNE    GD_GZ_CGP_PSBC101_HW_V    132.96.44.114\n"+
#         "CGPOMUNE    GD_GZ_CGP_PSBC102_HW_V    132.97.75.20\n"+
#         "CGPOMUNE    GD_GZ_CGP_UT101_HW_V    132.97.76.140\n"+
#         "CGPOMUNE    GD_GZ_CGP_VOLTEAS101_HW_V    132.96.44.148\n"+
#         "CGPOMUNE    GD_GZ_CGP_VOLTEAS102_HW_V    132.96.44.164\n"+
#         "CGPOMUNE    GD_GZ_CGP_VOLTEAS121_HW_V    132.96.44.180\n"+
#         "CGPOMUNE    GD_SZ_CGP_PSBC301_HW_V    132.102.47.52\n"+
#         "CGPOMUNE    GD_SZ_CGP_PSBC302_HW_V    132.102.47.166\n"+
#         "CSCFNE    GD_DG_ISCSCF201_HW_V    132.98.77.36\n"+
#         "CSCFNE    GD_DG_ISCSCF202_HW_V    132.98.77.44\n"+
#         "CSCFNE    GD_DG_MRFC201_HW_V    132.98.77.52\n"+
#         "CSCFNE    GD_DG_MRFC202_HW_V    132.98.77.68\n"+
#         "CSCFNE    GD_DG_MRFC221_HW_V    132.98.77.84\n"+
#         "CSCFNE    GD_GZ_ISCSCF101_HW_V    132.96.44.132\n"+
#         "CSCFNE    GD_GZ_ISCSCF102_HW_V    132.96.44.140\n"+
#         "CSCFNE    GD_GZ_MRFC101_HW_V    132.96.44.148\n"+
#         "CSCFNE    GD_GZ_MRFC102_HW_V    132.96.44.164\n"+
#         "CSCFNE    GD_GZ_MRFC121_HW_V    132.96.44.180\n"+
#         "MRP6600NE    GD_DG_MRFP201_HW_V    132.98.77.52\n"+
#         "MRP6600NE    GD_DG_MRFP202_HW_V    132.98.77.68\n"+
#         "MRP6600NE    GD_DG_MRFP221_HW_V    132.98.77.84\n"+
#         "MRP6600NE    GD_GZ_MRFP101_HW_V    132.96.44.148\n"+
#         "MRP6600NE    GD_GZ_MRFP102_HW_V    132.96.44.164\n"+
#         "MRP6600NE    GD_GZ_MRFP121_HW_V    132.96.44.180\n"+
#         "SE2900NE    GD_DG_ISBC291_HW_V    132.98.76.236\n"+
#         "SE2900NE    GD_DG_PSBC201_HW_V    132.98.76.228\n"+
#         "SE2900NE    GD_DG_PSBC202_HW_V    132.103.196.166\n"+
#         "SE2900NE    GD_FS_PSBC401_HW_V    132.104.196.196\n"+
#         "SE2900NE    GD_FS_PSBC402_HW_V    132.104.197.196\n"+
#         "SE2900NE    GD_GZ_ISBC191_HW_V    132.97.76.148\n"+
#         "SE2900NE    GD_GZ_PSBC101_HW_V    132.96.44.114\n"+
#         "SE2900NE    GD_GZ_PSBC102_HW_V    132.97.75.20\n"+
#         "SE2900NE    GD_SZ_PSBC301_HW_V    132.102.47.52\n"+
#         "SE2900NE    GD_SZ_PSBC302_HW_V    132.102.47.166\n"+
#         "SPGNE    GD_DG_SPG201_HW_V    132.98.77.52\n"+
#         "SPGNE    GD_DG_SPG202_HW_V    132.98.77.68\n"+
#         "SPGNE    GD_DG_SPG221_HW_V    132.98.77.84\n"+
#         "SPGNE    GD_GZ_SPG101_HW_V    132.96.44.148\n"+
#         "SPGNE    GD_GZ_SPG102_HW_V    132.96.44.164\n"+
#         "SPGNE    GD_GZ_SPG121_HW_V    132.96.44.180\n"+
#         "UIMNE    GD_DG_UTAP201_HW_V    132.98.76.244\n"+
#         "UIMNE    GD_DG_UTBSF201_HW_V    132.98.76.244\n"+
#         "UIMNE    GD_GZ_UTAP101_HW_V    132.97.76.140\n"+
#         "UIMNE    GD_GZ_UTBSF101_HW_V    132.97.76.140\n"+
#         "USCDBNE    GD_DG_UTUSCDB201_HW_V    132.98.76.244\n"+
#         "USCDBNE    GD_GZ_UTUSCDB101_HW_V    132.97.76.140\n"+
#         "\n"+
#         "\n"+
#         "---    END\n")