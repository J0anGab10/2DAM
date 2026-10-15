import pyautogui
import time

print("Tienes 15 segundos para abrir el chat de WhatsApp Web")
time.sleep(15)

for i in range(5):
    pyautogui.write("Carlos come POPO ")
    pyautogui.press("enter")
    time.sleep(0.5)