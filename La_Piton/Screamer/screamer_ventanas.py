#!/usr/bin/env python3
"""
Screamer de ventanas: espera 5 segundos sin mostrar nada y entonces aparecen
10 ventanas con tus imágenes de la carpeta "img" (1, 2, 3, 4, 5), en
posiciones y tamaños distintos y en orden desordenado.

Estructura esperada:
    screamer_ventanas.py
    img/
        1.png  2.jpg  3.png  4.png  5.gif   (cualquier extensión de imagen)

Recomendado:  pip install pillow   (permite JPG, PNG, GIF, WEBP... y tamaños libres)
Sin Pillow solo se pueden usar PNG y GIF.

Uso:
    python screamer_ventanas.py
    (en Windows, con pythonw screamer_ventanas.py no se ve ni la terminal)

ESC cierra todo. Clic sobre una ventana la cierra. Se cierra solo al final.
Si no encuentra alguna imagen, dibuja una cara de terror en su lugar.
"""

import glob
import math
import os
import random
import sys
import threading
import time
import tkinter as tk

try:
    from PIL import Image, ImageTk
except ImportError:
    Image = ImageTk = None

ESPERA = 5            # segundos de espera silenciosa
NUM_VENTANAS = 25     # cuántas ventanas aparecen
DURACION = 7          # segundos que dura todo tras aparecer la primera
SONIDO = True
EXTENSIONES = (".png", ".jpg", ".jpeg", ".gif", ".webp", ".bmp")

if getattr(sys, "frozen", False):
    BASE = sys._MEIPASS          # carpeta temporal del .exe
else:
    BASE = os.path.dirname(os.path.abspath(__file__))
CARPETA = os.path.join(BASE, "img")



# ---------- carga de imágenes ----------

def buscar_imagenes():
    """Devuelve las rutas de img/1.*, img/2.*, ... img/5.* que existan."""
    rutas = []
    for n in range(1, 6):
        for f in sorted(glob.glob(os.path.join(CARPETA, f"{n}.*"))):
            if f.lower().endswith(EXTENSIONES):
                rutas.append(f)
                break
    return rutas


def preparar_imagen(ruta, ancho_obj, sw, sh):
    """Carga la imagen escalada a un ancho aproximado. Devuelve PhotoImage o None."""
    try:
        if Image:
            img = Image.open(ruta).convert("RGBA")
            ow, oh = img.size
            nw = ancho_obj
            nh = int(nw * oh / ow)
            if nh > sh * 0.85:
                nh = int(sh * 0.85)
                nw = int(nh * ow / oh)
            filtro = getattr(Image, "Resampling", Image).LANCZOS
            return ImageTk.PhotoImage(img.resize((max(nw, 50), max(nh, 50)), filtro))
        foto = tk.PhotoImage(file=ruta)  # solo PNG/GIF sin Pillow
        ow = foto.width()
        if ow > ancho_obj:
            foto = foto.subsample(max(1, round(ow / ancho_obj)))
        else:
            foto = foto.zoom(max(1, round(ancho_obj / ow)))
        return foto
    except Exception:
        return None


# ---------- plan B: imágenes de terror generadas por código ----------

def cara(c, w, h):
    c.configure(bg=random.choice(["black", "#1a0000", "#0b0b1a"]))
    r = min(w, h) * 0.42
    cx, cy = w / 2, h / 2
    piel = random.choice(["#d8d3c4", "#b8c4a8", "#c9b0b0", "#a9b4c4"])
    c.create_oval(cx - r * .8, cy - r, cx + r * .8, cy + r, fill=piel, outline="#6b0000", width=4)
    ojo_color = random.choice(["red", "orange", "#ffee00", "#00ff66"])
    for px, py in [(-.35, -.2), (.35, -.2)]:
        ex, ey, s = cx + px * r, cy + py * r, r * .26
        c.create_oval(ex - s, ey - s * 1.15, ex + s, ey + s * 1.15, fill="black")
        c.create_oval(ex - s * .3, ey - s * .3, ex + s * .3, ey + s * .3, fill=ojo_color, outline="")
    bx1, bx2, by1, by2 = cx - r * .55, cx + r * .55, cy + r * .3, cy + r * .9
    c.create_oval(bx1, by1, bx2, by2, fill="black", outline="#400000", width=3)
    n = random.randint(6, 11)
    a = (bx2 - bx1) / n
    for i in range(n):
        x = bx1 + i * a
        c.create_polygon(x, by1 + (by2 - by1) * .25, x + a, by1 + (by2 - by1) * .25,
                         x + a / 2, by1 + (by2 - by1) * .5, fill="#f2eedd")
        c.create_polygon(x, by2 - (by2 - by1) * .25, x + a, by2 - (by2 - by1) * .25,
                         x + a / 2, by2 - (by2 - by1) * .5, fill="#f2eedd")


def ojo_gigante(c, w, h):
    c.configure(bg=random.choice(["#2a0000", "#100010", "black"]))
    cx, cy, rx, ry = w / 2, h / 2, w * .46, h * .3
    for _ in range(25):
        ang = random.uniform(0, 2 * math.pi)
        c.create_line(cx + math.cos(ang) * rx, cy + math.sin(ang) * ry,
                      cx + math.cos(ang) * rx * .45, cy + math.sin(ang) * ry * .45, fill="#c01010", width=2)
    c.create_oval(cx - rx, cy - ry, cx + rx, cy + ry, outline="#c01010", width=3)
    ir = min(rx, ry) * .8
    c.create_oval(cx - ir, cy - ir, cx + ir, cy + ir, fill=random.choice(["#ffcc00", "#ff3300", "#22cc55"]), outline="black", width=3)
    c.create_oval(cx - ir * .15, cy - ir * .85, cx + ir * .15, cy + ir * .85, fill="black")


GENERADORES = [cara, ojo_gigante]


# ---------- sonido y ventanas ----------

def pitido():
    if not SONIDO:
        return
    try:
        import winsound
        winsound.Beep(random.randint(400, 2200), 120)
    except Exception:
        print("\a", end="", flush=True)


def crear_ventana(root, ruta, sw, sh):
    foto = preparar_imagen(ruta, random.randint(220, 560), sw, sh) if ruta else None

    if foto:
        w, h = foto.width(), foto.height()
    else:
        w, h = random.randint(220, 560), random.randint(220, 560)
    w, h = min(w, sw), min(h, sh)
    x, y = random.randint(0, max(0, sw - w)), random.randint(0, max(0, sh - h))

    win = tk.Toplevel(root)
    win.overrideredirect(True)
    win.attributes("-topmost", True)
    win.geometry(f"{w}x{h}+{x}+{y}")
    canvas = tk.Canvas(win, width=w, height=h, highlightthickness=0, bg="black")
    canvas.pack()
    if foto:
        canvas.create_image(0, 0, image=foto, anchor="nw")
        canvas.image = foto  # evita que Python borre la imagen de memoria
    else:
        random.choice(GENERADORES)(canvas, w, h)

    win.bind("<Button-1>", lambda e: win.destroy())
    win.focus_force()
    threading.Thread(target=pitido, daemon=True).start()

    def temblar():
        if not win.winfo_exists():
            return
        win.geometry(f"{w}x{h}+{x + random.randint(-7, 7)}+{y + random.randint(-7, 7)}")
        win.after(80, temblar)

    temblar()


def main():
    time.sleep(ESPERA)  # espera silenciosa, sin imprimir nada

    root = tk.Tk()
    root.withdraw()
    root.bind_all("<Escape>", lambda e: root.destroy())
    sw, sh = root.winfo_screenwidth(), root.winfo_screenheight()

    imagenes = buscar_imagenes()
    if imagenes:
        # reparte las imágenes por igual (5 imágenes x 2 = 10 ventanas) y las mezcla
        lista = [imagenes[i % len(imagenes)] for i in range(NUM_VENTANAS)]
    else:
        lista = [None] * NUM_VENTANAS  # sin imágenes: usa las dibujadas por código
    random.shuffle(lista)

    for ruta in lista:
        root.after(random.randint(0, 2500), crear_ventana, root, ruta, sw, sh)

    root.after(int((DURACION + 2.5) * 1000), root.destroy)
    root.mainloop()


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        pass