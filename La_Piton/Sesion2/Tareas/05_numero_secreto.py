# 05_numero_secreto.py
import random

print("ADIVINA EL NÚMERO SECRETO (1 al 50)")

# 1. Generar número aleatorio entre 1 y 50
numero = random.randint(1, 50)

# 2. Bucle for con range() para 5 intentos
for intento in range(1, 6):
    respuesta = int(input(f"Intento {intento}/5: "))

    # 4. Salto con break en caso de acierto
    if respuesta == numero:
        print(f"¡Felicidades! Adivinaste el número en el intento {intento}.")
        break
        # 3. Pistas condicionales
    elif respuesta < numero:
        print("-> El número secreto es MAYOR.")
    else:
        print("-> El número secreto es MENOR.")

# 5. Cláusula else del bucle (Obligatorio)
else:
    print(f"Has agotado todos los intentos. El número secreto era el {numero}.")