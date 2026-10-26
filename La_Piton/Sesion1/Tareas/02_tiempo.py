total_segundos = int(input("Introduce segundos: "))

horas = total_segundos // 3600

segundos_restantes = total_segundos % 3600
minutos = segundos_restantes // 60
segundos = segundos_restantes % 60

print("\nResultado:")
print(f"{horas:02d}h {minutos:02d}m {segundos:02d}s")