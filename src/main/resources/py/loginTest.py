# coding=utf-8
import sys
import json

def main(report):
    status = '0'
    succParamMsg = ""
    textMsg = ""
    errorMsg = ""
    returnMsg = {"status": status, "succParamMsg": succParamMsg, "textMsg": textMsg, "errorMsg": errorMsg}
    if (report.find("RETCODE = 0") == -1):
        returnMsg["status"] = '1'
        returnMsg["succParamMsg"] = "失败"
    else:
        returnMsg["succParamMsg"] = "成功"
    returnMsg = json.dumps(returnMsg, ensure_ascii=False)
    return returnMsg

main(sys.argv[1])


main(
                "+++     NMS SERVER        2019-11-07 17:58:47\n" +
                "O&M     #2304\n" +
                "%%LGI:OP=\"gdzdxj\", PWD=\"*****\";%%\n" +
                "RETCODE = 1  该用户已经登录\n" +
                "\n" +
                "该用户已经登录\n" +
                "\n" +
                "---    END")
