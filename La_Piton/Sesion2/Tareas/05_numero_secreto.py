import random

print("ADIVINA EL NÚMERO SECRETO (1 al 50)")

numero = random.randint(1, 50)

for intento in range(1, 6):
    respuesta = int(input(f"Intento {intento}/5: "))

    if respuesta == numero:
        print(f"¡Felicidades! Adivinaste el número en el intento {intento}.")
        break
    elif respuesta < numero:
        print("-> El número secreto es MAYOR.")
    else:
        print("-> El número secreto es MENOR.")

else:
    print(f"Has agotado todos los intentos. El número secreto era el {numero}.")