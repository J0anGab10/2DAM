# 07_ranking_notas.py

# 1. Lista inicial
notas = [5.2, 8.5, 4.0, 9.8, 6.7, 3.5, 9.1, 7.3, 10.0, 5.9]

# 2. Métricas globales
total_examenes = len(notas)
nota_media = sum(notas) / total_examenes
nota_alta = max(notas)
nota_baja = min(notas)

# 3. Top 3 mediante Slicing
# Primero ordenamos de mayor a menor sin tocar la lista original
notas_ordenadas = sorted(notas, reverse=True)
# Extraemos los 3 primeros
top_3 = notas_ordenadas[:3]

# 4. Inversión (usando paso -1)
lista_invertida = notas[::-1]

# SALIDA ESPERADA
print("=== AUDITORÍA DE CALIFICACIONES ===")
print(f"Total exámenes: {total_examenes}")
print(f"Nota media: {nota_media:.2f}")
print(f"Nota más alta: {nota_alta} | Más baja: {nota_baja}")
print()
print(f"Top 3 notas: {top_3}")
print(f"Lista invertida: {lista_invertida}")