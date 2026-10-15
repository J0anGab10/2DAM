import cv2
import pyautogui
import keyboard
import time

pyautogui.FAILSAFE = True

# 1. Cargar imagen en escala de grises
img = cv2.imread("teddy.jpg", cv2.IMREAD_GRAYSCALE)
if img is None:
    img = cv2.imread("monalisa.jpg", cv2.IMREAD_GRAYSCALE)

if img is None:
    print("Error: No se encontró 'monalisa.jpg'. Asegúrate de que esté en la carpeta.")
    exit()

# 2. Redimensionar para encajar en el lienzo de Paint
ancho_lienzo = 200
alto_lienzo = int(img.shape[0] * (ancho_lienzo / img.shape[1]))
img_resized = cv2.resize(img, (ancho_lienzo, alto_lienzo))

# 3. Extraer bordes y contornos vectoriales
bordes = cv2.Canny(img_resized, 80, 160)
contornos, _ = cv2.findContours(bordes, cv2.RETR_LIST, cv2.CHAIN_APPROX_NONE)

print("Tienes 5 segundos para situar el ratón en la esquina superior izquierda de Paint...")
print("PULSA 'ESC' EN CUALQUIER MOMENTO PARA DETENERLO.")
time.sleep(5)

origen_x, origen_y = pyautogui.position()
cancelado = False

try:
    for contorno in contornos:
        if cancelado or keyboard.is_pressed('esc'):
            cancelado = True
            break

        # Omitir ruidos o motas diminutas
        if len(contorno) < 4:
            continue

        # Ir al inicio del trazo
        x_ini = origen_x + contorno[0][0][0]
        y_ini = origen_y + contorno[0][0][1]
        pyautogui.moveTo(x_ini, y_ini)
        pyautogui.mouseDown()

        # Dibujar cada punto del contorno
        for punto in contorno[1:]:
            if keyboard.is_pressed('esc'):
                cancelado = True
                break

            px = origen_x + punto[0][0]
            py = origen_y + punto[0][1]
            pyautogui.moveTo(px, py)

        pyautogui.mouseUp()

finally:
    pyautogui.mouseUp()
    if cancelado:
        print("\nDetenido por el usuario con la tecla Escape.")
    else:
        print("\n¡Dibujo finalizado!")