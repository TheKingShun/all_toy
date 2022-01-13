import argparse
import json
import logging
import subprocess

from transform import ASTParse

# logging configure
LOG_FORMAT = "%(filename)s - %(lineno)s - %(levelname)s ===%(message)s==="
logging.basicConfig(level=logging.DEBUG, format=LOG_FORMAT)

if __name__ == "__main__":

    parser = argparse.ArgumentParser()
    parser.add_argument("-fp", "--filePath", type=str, help="give me a file to convert", dest="filePath", required=True)
    parser.add_argument("-sp", "--savePath", type=str, help="save file", dest="savePath", default="ret.sol")
    args = parser.parse_args()

    filePath = str(args.filePath)
    savePath = str(args.savePath)

    cmd_command = "astexport < " + filePath + " > ret.json"
    ret = subprocess.run(cmd_command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE,
                         encoding="utf-8")
    if ret.returncode == 0:
        logging.info("cmd load success: ")
    else:
        logging.info("cmd load error")
        logging.error(ret)
        exit()

    filePath = "./ret.json"
    json_str = json.load(open(filePath, 'r'))

    # start to transform py 2 sol

    parse = ASTParse()
    parse.start(json_str, savePath)
