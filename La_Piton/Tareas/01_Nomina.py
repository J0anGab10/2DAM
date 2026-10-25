nombre_trabajador = "Laura García"
horas_trabajadas = 30
tarifa_hora = 15.0
IRPF = 0.15

salario_bruto = horas_trabajadas * tarifa_hora
retencion = salario_bruto * IRPF
salario_neto = salario_bruto - retencion

print(f"--- NÓMINA: {nombre_trabajador} ---")
print(f"Bruto: {salario_bruto:.2f} €")
print(f"IRPF (15%): {retencion:.2f} €")
print(f"Neto: {salario_neto:.2f} €")