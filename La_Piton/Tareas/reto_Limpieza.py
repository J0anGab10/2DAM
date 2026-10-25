entrada = input("Introduce una cantidad en euros: ")

entrada_normalizada = entrada.replace(",", ".")

euros = float(entrada_normalizada)

print(f"\nValor guardado en memoria: {euros}")
print(f"Comprobación (doble de la cantidad): {euros * 2:.2f} €")