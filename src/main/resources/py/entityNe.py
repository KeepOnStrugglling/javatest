import json

class Ne(object):
    def __init__(self):
        self.__outPutParam={}
        self.__hasAlarm=0
        self.__alarmSummary=""
        self.__alarmMsg=""
        self.__hasError=0
        self.__errorMsg=""
    def getPythonResult(self):
        dictTemp={
            "outPutParam":self.__outPutParam,
            "hasAlarm" : self.__hasAlarm,
            "alarmSummary":self.__alarmSummary,
            "alarmMsg":self.__alarmMsg,
            "hasError":self.__hasError,
            "errorMsg":self.__errorMsg,
        }
        return json.dumps(dictTemp)

    def getHasAlarm(self):
        return self.__hasAlarm
    
    def setHasAlarm(self,hasAlarm):
        self.__hasAlarm = hasAlarm

    def getAlarmSummary(self):
        return self.__alarmSummary
    
    def setAlarmSummary(self,alarmSummary):
        self.__alarmSummary = alarmSummary

    def getAlarmMsg(self):
        return self.__alarmMsg
    
    def setAlarmMsg(self,alarmMsg):
        self.__alarmMsg = alarmMsg

    def getHasError(self):
        return self.__hasError
    
    def setHasError(self,hasError):
        self.__hasError = hasError

    def getErrorMsg(self):
        return self.__errorMsg
    
    def setErrorMsg(self,errorMsg):
        self.__errorMsg = errorMsg

def print(*values: object):
    None