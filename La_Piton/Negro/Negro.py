import os
import sys
import time
import ctypes
import tkinter as tk
from tkinter import messagebox
import pygame
from ffpyplayer.player import MediaPlayer
import keyboard

# Teclas que quedarán bloqueadas durante el fundido y el vídeo
TECLAS_BLOQUEADAS = [
    'windows', 'left windows', 'right windows',
    'alt', 'left alt', 'right alt', 'alt gr',
    'tab', 'esc', 'f4', 'ctrl', 'left ctrl', 'right ctrl',
    'delete'
]

def activar_bloqueo():
    """Bloquea los atajos del sistema y congela el ratón"""
    for tecla in TECLAS_BLOQUEADAS:
        try:
            keyboard.block_key(tecla)
        except Exception:
            pass
    try:
        ctypes.windll.user32.BlockInput(True)
    except Exception:
        pass

def desactivar_bloqueo():
    """Devuelve el control total del PC al terminar"""
    try:
        ctypes.windll.user32.BlockInput(False)
    except Exception:
        pass
    try:
        keyboard.unhook_all()
    except Exception:
        pass

def salida_emergencia_creador():
    """Atajo secreto (Shift + Q) para que tú puedas cerrarlo en tus pruebas"""
    if keyboard.is_pressed('shift') and keyboard.is_pressed('q'):
        desactivar_bloqueo()
        pygame.quit()
        os._exit(0)

def forzar_foco_y_raton(hwnd=None):
    """Mantiene el ratón encerrado en el centro y fuerza la ventana al frente"""
    try:
        ancho_pantalla = ctypes.windll.user32.GetSystemMetrics(0)
        alto_pantalla = ctypes.windll.user32.GetSystemMetrics(1)
        ctypes.windll.user32.SetCursorPos(ancho_pantalla // 2, alto_pantalla // 2)
        if hwnd:
            ctypes.windll.user32.SetForegroundWindow(hwnd)
    except Exception:
        pass

def obtener_ruta(ruta_relativa):
    """Obtiene la ruta correcta tanto en desarrollo como compilado en .exe"""
    try:
        ruta_base = sys._MEIPASS
    except Exception:
        ruta_base = os.path.abspath(".")
    return os.path.join(ruta_base, ruta_relativa)

def iniciar_secuencia(minutos_str, ventana_aviso):
    try:
        minutos = float(minutos_str)
        if minutos < 0:
            raise ValueError
    except ValueError:
        messagebox.showerror("Error", "Introduce un número válido de minutos (ej. 1 o 0.1).")
        return

    # 1. Cerrar la pantalla de aviso y esperar el temporizador en segundo plano
    ventana_aviso.destroy()
    segundos_espera = int(minutos * 60)
    time.sleep(segundos_espera)

    # 2. ¡EMPIEZA EL BLOQUEO TOTAL!
    activar_bloqueo()

    try:
        # 3. Fundido a negro (5s) + Mantener en negro (7s) con bloqueo activo
        ventana_negra = hacer_fade_out_y_mantener()

        # 4. Reproducir el único vídeo (jumpscare.mp4) con audio y sin cortes
        ruta_video = obtener_ruta(os.path.join("vd", "jumpscare.mp4"))
        ejecutar_video(ruta_video, ventana_negra)
    finally:
        # 5. Pase lo que pase, devolver el control al acabar el vídeo
        desactivar_bloqueo()
        sys.exit()

def hacer_fade_out_y_mantener():
    """Oscurece la pantalla en 5s y la mantiene en negro absoluto durante 7s sin dejar salir"""
    fade = tk.Tk()
    fade.attributes("-fullscreen", True)
    fade.attributes("-topmost", True)
    fade.attributes("-alpha", 0.0)
    fade.configure(bg="black", cursor="none")
    fade.protocol("WM_DELETE_WINDOW", lambda: None)

    hwnd = fade.winfo_id()

    # 5 segundos de fundido progresivo
    pasos = 100
    intervalo = 5.0 / pasos
    for i in range(1, pasos + 1):
        salida_emergencia_creador()
        fade.attributes("-alpha", i / pasos)
        fade.lift()
        fade.focus_force()
        forzar_foco_y_raton(hwnd)
        fade.update()
        time.sleep(intervalo)

    # Mantener la pantalla completamente en negro durante 7 segundos
    inicio_negro = time.time()
    while time.time() - inicio_negro < 7.0:
        salida_emergencia_creador()
        fade.lift()
        fade.focus_force()
        forzar_foco_y_raton(hwnd)
        fade.update()
        time.sleep(0.05)

    return fade

def ejecutar_video(ruta_video, ventana_negra):
    """Inicializa Pygame secuestrando el foco de eventos y reproduce jumpscare.mp4 con sonido"""
    if not os.path.exists(ruta_video):
        print(f"No se encontró el archivo: {ruta_video}")
        if ventana_negra is not None:
            try:
                ventana_negra.destroy()
            except Exception:
                pass
        return

    pygame.init()
    info = pygame.display.Info()
    ancho, alto = info.current_w, info.current_h

    pantalla = pygame.display.set_mode((ancho, alto), pygame.FULLSCREEN | pygame.NOFRAME)
    pantalla.fill((0, 0, 0))
    pygame.display.flip()

    pygame.mouse.set_visible(False)
    pygame.event.set_grab(True)
    hwnd_pygame = pygame.display.get_wm_info()['window']
    reloj = pygame.time.Clock()

    # Audio activado ('an': False) y sincronizado con el sonido ('sync': 'audio')
    opciones_ff = {'an': False, 'sync': 'audio'}
    reproductor = MediaPlayer(ruta_video, ff_opts=opciones_ff)

    reproduciendo = True
    while reproduciendo:
        salida_emergencia_creador()
        forzar_foco_y_raton(hwnd_pygame)

        for evento in pygame.event.get():
            if evento.type == pygame.QUIT:
                pass

        frame, val = reproductor.get_frame()
        if val == 'eof':
            reproduciendo = False
            break
        elif frame is None:
            reloj.tick(60)
            continue

        img, t = frame
        w, h = img.get_size()
        superficie = pygame.image.frombuffer(img.to_bytearray()[0], (w, h), 'RGB')

        if (w, h) != (ancho, alto):
            superficie = pygame.transform.smoothscale(superficie, (ancho, alto))

        pantalla.blit(superficie, (0, 0))
        pygame.display.flip()

        # Quitar la cortina de tkinter en cuanto el primer frame ya está en pantalla
        if ventana_negra is not None:
            ventana_negra.destroy()
            ventana_negra = None

        if val > 0:
            time.sleep(val)

    reproductor.close_player()

    if ventana_negra is not None:
        try:
            ventana_negra.destroy()
        except Exception:
            pass

    pygame.event.set_grab(False)
    pygame.quit()

def mostrar_interfaz_inicial():
    root = tk.Tk()
    root.title("ADVERTENCIA")
    root.attributes("-fullscreen", True)
    root.attributes("-topmost", True)
    root.configure(bg="black")

    marco = tk.Frame(root, bg="black")
    marco.place(relx=0.5, rely=0.5, anchor="center")

    fuente_titulo = ("Chiller", 65, "bold")
    fuente_texto = ("Courier", 16, "bold")

    lbl_titulo = tk.Label(
        marco,
        text="⚠️ AVISO ⚠️",
        fg="#cc0000",
        bg="black",
        font=fuente_titulo
    )
    lbl_titulo.pack(pady=(0, 20))

    texto_aviso = (
        "NO NOS HACEMOS CARGO DE NADA DE LO QUE OCURRA A CONTINUACIÓN.\n\n"
        "Al iniciar la cuenta atrás, el sistema quedará bajo tu propia responsabilidad.\n"
        "Introduce en cuántos minutos quieres que se ejecute la secuencia:"
    )
    lbl_desc = tk.Label(
        marco,
        text=texto_aviso,
        fg="#ff1a1a",
        bg="black",
        font=fuente_texto,
        justify="center"
    )
    lbl_desc.pack(pady=20)

    entrada_tiempo = tk.Entry(
        marco,
        font=("Courier", 28, "bold"),
        fg="#ff0000",
        bg="#110000",
        insertbackground="red",
        justify="center",
        width=8,
        bd=3,
        relief="ridge"
    )
    entrada_tiempo.insert(0, "0.1")
    entrada_tiempo.pack(pady=20)
    entrada_tiempo.focus()

    btn_aceptar = tk.Button(
        marco,
        text="ACEPTAR Y COMENZAR",
        font=("Courier", 18, "bold"),
        fg="black",
        bg="#cc0000",
        activebackground="#800000",
        activeforeground="white",
        cursor="hand2",
        padx=20,
        pady=10,
        command=lambda: iniciar_secuencia(entrada_tiempo.get(), root)
    )
    btn_aceptar.pack(pady=30)

    root.bind('<Return>', lambda event: iniciar_secuencia(entrada_tiempo.get(), root))
    root.bind('<Escape>', lambda event: root.destroy())

    root.mainloop()

if __name__ == "__main__":
    mostrar_interfaz_inicial()