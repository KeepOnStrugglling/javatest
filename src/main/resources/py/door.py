#coding=utf-8
from entityNe import Ne
from sys import argv 




##私有的输出

if __name__=="__main__":
    model = __import__(argv[1])
    if(len(argv)==0):
        print({"erorr":"参数个数不合法。"})
    else:
        ne=Ne()
        model.proc(ne,argv[2])
        print(ne.getPythonResult())

