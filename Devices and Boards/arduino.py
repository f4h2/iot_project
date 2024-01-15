import sys, re, serial, openpyxl, os
from PyQt5.QtWidgets import QApplication, QMainWindow, QLabel, QTextBrowser
from PyQt5.QtCore import Qt, QThread, pyqtSignal
from PyQt5 import uic
from datetime import datetime

# Specify the serial port and baud rate
ser = serial.Serial('COM4', 9600)  # Replace 'COMx' with the appropriate serial port for your Arduino
dic_uid = {}

class SerialReaderThread(QThread):
    data_received = pyqtSignal(str)

    def run(self):
        while True:
            line = ser.readline()
            decoded_line = line.decode('utf-8').rstrip()
            self.data_received.emit(decoded_line)

class UI(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("arduino.ui", self)

        # Connect Ui
        self.uid_in       = self.findChild(QLabel, "uid_in")
        self.time_in_in   = self.findChild(QLabel, "time_in_in")
        self.time_out_in  = self.findChild(QLabel, "time_out_in")
        self.time_out_out = self.findChild(QLabel, "time_out_out")
        self.uid_out      = self.findChild(QLabel, "uid_out")
        self.slot1        = self.findChild(QLabel , "slot_1")
        self.slot2        = self.findChild(QLabel , "slot_2")
        self.output       = self.findChild(QTextBrowser, "log")
        self.his          = self.findChild(QTextBrowser, "his")
        
        self.show()
        self.output.append("------------     OK     --------------")
        
        # SERIAL
        self.serial_reader_thread = SerialReaderThread()
        self.serial_reader_thread.data_received.connect(self.handle_serial_data)
        self.serial_reader_thread.start()

        # EXCEL
        # file_path = os.path.join("D:\coding\Python Project", str(datetime.now().strftime("%Y-%m-%d"))+'_output_data.xlsx')
        # if os.path.exists(file_path):  
        try:
            self.workbook = openpyxl.load_workbook("D:\coding\Python Project"+str(datetime.now().strftime("%Y-%m-%d")) + '_output_data.xlsx')
        except FileNotFoundError:
            # Create a new workbook if the file doesn't exist
            self.workbook = openpyxl.Workbook()
            self.sheet = self.workbook.active
            self.sheet.append(["Timestamp", "UID", "Status"])
        # self.workbook = openpyxl.Workbook()
        self.sheet = self.workbook.active
        self.workbook.save(datetime.now().strftime("%Y-%m-%d") + '_output_data.xlsx')
        self.workbook.close()

    def handle_serial_data(self, decoded_line):
        # Get In
        if re.findall("(?<=The\sUID\sget\sIn:\s\s)[0-9A-Z\s]{11}", decoded_line):
            res = re.findall("(?<=The\sUID\sget\sIn:\s\s)[0-9A-Z\s]{11}", decoded_line)
            current_time = datetime.now().strftime("%H:%M:%S")
            self.uid_in.setText(str(res[0]))
            self.time_in_in.setText(current_time)
            dic_uid[res[0]] = str(current_time)
            self.output.append(str(datetime.now()) + " - Card " + str(res[0]) + ":  In")
            self.his.append(str(datetime.now()) + " - Card " + str(res[0]) + ":  In")
            # Append the data to the Excel sheet
            self.workbook = openpyxl.load_workbook(datetime.now().strftime("%Y-%m-%d") + '_output_data.xlsx')
            self.sheet = self.workbook.active
            self.sheet.append([str(datetime.now()), str(res[0]), "In"])
            self.workbook.save(datetime.now().strftime("%Y-%m-%d") + '_output_data.xlsx')
            self.workbook.close()
            # self.output.append(str(decoded_line))
            
        # Get Out
        elif re.findall("(?<=The\sUID\sget\sOUT:\s\s)[0-9A-Z\s]{11}", decoded_line):
            res = re.findall("(?<=The\sUID\sget\sOUT:\s\s)[0-9A-Z\s]{11}", decoded_line)
            current_time = datetime.now().strftime("%H:%M:%S")
            self.uid_out.setText(str(res[0]))
            self.time_out_in.setText(dic_uid[res[0]])
            self.time_out_out.setText(current_time)
            dic_uid[res[0]] = '-1'
            self.output.append(str(datetime.now()) + " - Card " + str(res[0]) + ":  Out")
            self.his.append(str(datetime.now()) + " - Card " + str(res[0]) + ":  Out")
            # Append the data to the Excel sheet
            self.workbook = openpyxl.load_workbook(datetime.now().strftime("%Y-%m-%d") + '_output_data.xlsx')
            self.sheet = self.workbook.active
            self.sheet.append([str(datetime.now()), str(res[0]), "Out"])
            self.workbook.save(datetime.now().strftime("%Y-%m-%d") + '_output_data.xlsx')
            self.workbook.close()
            # self.output.append(str(decoded_line))
            
        # Park status
        elif re.findall("(?<=1 - 2: )[\S]{3}", decoded_line):
            pos = re.findall("(?<=1 - 2: )[\S]{3}", decoded_line)
            if str(pos[0])[0] == '0':
                self.slot1.setText("Full")
            elif str(pos[0])[0] == '1':
                self.slot1.setText("Empty")
            if str(pos[0])[2] == '0':
                self.slot2.setText("Full")
            elif str(pos[0])[2] == '1':
                self.slot2.setText("Empty")
            self.output.append(str(decoded_line))
            
        else:
            self.output.append(str(decoded_line))
                

    def closeEvent(self, event):
        self.serial_reader_thread.terminate()
        event.accept()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    UIWindow = UI()
    sys.exit(app.exec_())

