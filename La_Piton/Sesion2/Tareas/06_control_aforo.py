# BY Juan Gabriel Galarza Claros

aforo_maximo = 5
aforo_actual = 0
historial = []

while True:
     print("\n=== CONTROL DE AFORO (Máx: 5) ===\nOPCIONES = Entrar | Salir | Estado | Fin ")
     feedback= input("Elige una opción: ").upper()

     match feedback:
         case "ENTRAR":
             if aforo_actual >= aforo_maximo:
                 print("ERROR: Aforo completo")
             else:
                 aforo_actual += 1
                 historial.append(aforo_actual)
                 print(f"¡Persona ingresada con exito!\nAforo actual = {aforo_actual}/5")

         case "SALIR":
             if aforo_actual <= 0:
                 print("ERROR: No hay personas dentro")
             else:
                 aforo_actual -= 1
                 historial.append(aforo_actual)
                 print(f"¡Persona sacada con exito!\nAforo actual = {aforo_actual}/5")

         case "ESTADO":
             aforo_disponible = aforo_maximo - aforo_actual
             print("ESTADO ACTUAL: ")
             print(f"Aforo actual = {aforo_actual}\nAforo disponible = {aforo_disponible}")

         case "FIN":
             break

         case _:
             print("ERROR: Porfavor elija una de las 4 opciones disponibles")

#Bucle for else opcional
print("\n\nCOMPROBACIÓN DEL AFORO")
for cantidad in historial:
     if cantidad == aforo_maximo:
         print("AVISO: En algún momento se llego al aforo máximo")
         break
else:
     print("En ningún momento se llego al aforo máximo")






