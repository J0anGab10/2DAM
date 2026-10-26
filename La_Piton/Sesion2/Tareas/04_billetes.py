# 04_billetes.py

entrada = input("Introduce los datos del billete: ").split()

match entrada:
    # 1. Caso TURISTA: Captura tipo y edad exacta (sin extras)
    case ["TURISTA", edad] if edad.isdigit() and int(edad) >= 0:
        precio = 30
        edad_num = int(edad)

        if edad_num < 18:
            precio *= 0.75
        elif edad_num >= 65:
            precio *= 0.60

        print(f"Tarifa TURISTA. Precio final: {precio:.2f} €")

    # 2. Caso PREFERENTE o VIP: Captura tipo, edad y una cantidad variable de extras
    case ["PREFERENTE" | "VIP" as tipo, edad, *extras] if edad.isdigit() and int(edad) >= 0:
        precio = 50 if tipo == "PREFERENTE" else 80
        edad_num = int(edad)

        if edad_num < 18:
            precio *= 0.75
        elif edad_num >= 65:
            precio *= 0.60

        # Sumamos 10€ por cada elemento extra en la lista capturada
        precio += len(extras) * 10

        print(f"Tarifa {tipo}. Extras añadidos: {len(extras)}. Precio final: {precio:.2f} €")

    # 3. Validación de edad: Si hay al menos dos parámetros pero la edad es incorrecta
    case [tipo, edad, *resto] if not edad.isdigit() or int(edad) < 0:
        print("Error: La edad introducida no es un número válido o es negativa.")

    # 4. Validación de tarifa: Si la clase no coincide con las tres permitidas
    case [tipo, edad, *resto] if tipo not in ["TURISTA", "PREFERENTE", "VIP"]:
        print(f"Error: La tarifa '{tipo}' no existe en el sistema.")

    # 5. Comodín por defecto: Para entradas vacías o formatos totalmente rotos
    case _:
        print("Error general. Formato esperado: TIPO_BILLETE EDAD [EXTRAS...]")