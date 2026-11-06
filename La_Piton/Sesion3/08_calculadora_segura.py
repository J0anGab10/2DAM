while True:
    try:
        # Pedimos el primer número
        entrada1 = input("Número 1: ")
        if entrada1.upper() == "SALIR":
            break
        n1 = float(entrada1)

        op = input("Operador (+, -, *, /): ")
        if op.upper() == "SALIR":
            break

        # Pedimos el segundo número
        entrada2 = input("Número 2: ")
        if entrada2.upper() == "SALIR":
            break
        n2 = float(entrada2)

        if op not in ["+", "-", "*", "/"]:
            raise ValueError("Operador no soportado")

        # Operaciones
        if op == "+":
            resultado = n1 + n2
        elif op == "-":
            resultado = n1 - n2
        elif op == "*":
            resultado = n1 * n2
        elif op == "/":
            resultado = n1 / n2

    # Captura de errores
    except ValueError as e:
        # Si el error es el que hemos lanzado nosotros con raise:
        if str(e) == "Operador no soportado":
            print(f"[ERROR]: {e}\n")
        # Si es el error por introducir letras en el float():
        else:
            print("[ERROR]: Has introducido letras en lugar de números.\n")

    except ZeroDivisionError:
        print("[ERROR]: No se puede dividir entre cero.\n")

    else:
        print(f"[ÉXITO]: {n1} {op} {n2} = {resultado:.2f}\n")