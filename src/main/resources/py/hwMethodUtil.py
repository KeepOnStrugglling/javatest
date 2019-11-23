# coding=utf-8

columnStandard=[]

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
    
def getColumnStandardByRe(index,dataArr):   #用正则表达式切割，只切割两个以上的空格
    data=dataArr[index]
    global columnStandard
    columnStandard=re.split(r"  +",data)
    return columnStandard

def object2dict(obj):
    d={}
    d.update(obj.__dict__)
    return d