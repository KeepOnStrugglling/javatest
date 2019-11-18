class FbAtom(object):
    #指令执行时间
    procTime=None
    #网元名称
    meName=None
    #报文的命令
    command=None
    #指令执行结果
    procStatus="0"
    #子报文的名称
    fbAtomName=None
    #子报文的类型
    fbAtomType=None #类型有：multi，single
    #标题数组
    headArr=None
    #报文实体的数据
    dataArr=None
    #报文实体的数据的长度
    dataLen=0
    #指令执行失败结果
    procErrorMsg=None