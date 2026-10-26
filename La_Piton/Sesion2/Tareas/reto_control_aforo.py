# 06_control_aforo.py

maximo = 5
dentro = 0
historial = []

print("=== CONTROL DE AFORO (Máx: 5) ===")
print("Comandos: ENTRAR | SALIR | ESTADO | FIN")

while True:
    cmd = input("> Comando: ").strip().upper()

    match cmd:
        case "ENTRAR":
            if dentro < maximo:
                dentro += 1
                historial.append(dentro)
                print(f"[OK] Persona ingresada. Aforo actual: {dentro}/{maximo}")
            else:
                print("[AVISO] Aforo máximo alcanzado.")

        case "SALIR":
            if dentro > 0:
                dentro -= 1
                historial.append(dentro)
                print("[OK] Persona ha salido.")
            else:
                print("[AVISO] El recinto ya está vacío.")

        case "ESTADO":
            libres = maximo - dentro
            print(f"[INFO] Personas dentro: {dentro} | Plazas libres: {libres}")

        case "FIN":
            print("Saliendo del programa...")
            break

        case _:
            print("Comando no reconocido.")

# Comprobación con for-else
for registro in historial:
    if registro == maximo:
        print("Nota: Se alcanzó el aforo completo durante la sesión.")
        break
else:
    print("Nota: El local no se llenó en toda la sesión.")