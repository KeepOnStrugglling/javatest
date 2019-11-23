# coding=utf-8
import sys

columnStandard = []
def getEqName(data):
    bIndex = data.find('/*')
    eIndex = data.find('*/')
    eqName = data[bIndex + 16:eIndex]
    return eqName

def getColumnStandard(index, dataArr):
    data = dataArr[index]
    global columnStandard
    columnStandard = data.split()
    return columnStandard

def getObjByValue(index, headfilter, dataArr):
    datas = dataArr[index].split()
    m = {}
    for i in range(len(headfilter)):
        m[headfilter[i]] = datas[i]
    return m


def main(report):
    eqName = ""  # 网元名字
    devicePatch = ""
    headfilter = []

    dataArr = report.splitlines();
    k = 0
    for i in range(len(dataArr)):
        if k == len(dataArr):
            break
        if dataArr[k].find('MENAME:') != -1:
            eqName = getEqName(dataArr[i])
        if dataArr[k].find('操作结果如下') != -1:
            k += 2
            headfilter = getColumnStandard(k, dataArr)
            k += 2
        if dataArr[k].find('结果个数 = ') != -1:
            headfilter = []
        if headfilter != []:
            dataMap = getObjByValue(k, headfilter, dataArr)
            if eqName==dataMap['网元名称']:
                devicePatch = dataMap['网元补丁版本']
                break
        k += 1
    return devicePatch

    main(sys.argv[1])