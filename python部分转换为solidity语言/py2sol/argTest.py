import argparse

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("-fp", "--filePath", type=str, help="give me a file to convert", dest="filePath", required=True)
    parser.add_argument("-sp", "--savePath", type=str, help="save file", dest="savePath", default="ret.sol")
    args = parser.parse_args()
    print(args.filePath)
    print(args.savePath)
