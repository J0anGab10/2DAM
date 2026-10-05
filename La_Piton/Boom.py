import pyautogui
import time

time.sleep(15)

for i in range(5):
    pyautogui.write("tu mensaje")
    pyautogui.press("enter")
    time.sleep(0.3)