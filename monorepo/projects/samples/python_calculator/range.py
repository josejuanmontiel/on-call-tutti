from datetime import datetime, timedelta


class Rango:
    def __init__(self, fecha_inicio, fecha_fin, persona, nivel):
        self.fecha_inicio = fecha_inicio
        self.fecha_fin = fecha_fin
        self.persona = persona
        self.nivel = nivel

    def __str__(self):
        return f"{self.fecha_inicio},{self.fecha_fin},{self.persona},{self.nivel}"


def dividir_rangos(entrada):
    rangos = []
    for linea in entrada:
        partes = linea.split(",")

        # Modifica el formato de fecha de entrada
        fecha_inicio_str = partes[0]
        fecha_fin_str = partes[1]

        # Elimina la 'Z' del final de las fechas, si existe
        if fecha_inicio_str.endswith("Z"):
            fecha_inicio_str = fecha_inicio_str[:-1]
        if fecha_fin_str.endswith("Z"):
            fecha_fin_str = fecha_fin_str[:-1]

        # Convierte las fechas a objetos datetime
        try:
            fecha_inicio = datetime.strptime(fecha_inicio_str, "%Y-%m-%dT%H.%M.%S")
            fecha_fin = datetime.strptime(fecha_fin_str, "%Y-%m-%dT%H.%M.%S")
        except ValueError:
            print(f"Error al parsear fecha: {linea}")
            continue  # Salta al siguiente elemento de la lista de entrada

        persona = int(partes[2])
        nivel = int(partes[3])

        # Si la fecha de inicio es igual a la fecha de fin, solo se crea un rango
        if fecha_inicio == fecha_fin:
            rangos.append(Rango(fecha_inicio, fecha_fin, persona, nivel))
        else:
            # Se crea un rango para la fecha de inicio
            rangos.append(Rango(fecha_inicio, fecha_inicio - timedelta(seconds=1), persona, nivel))

            # Se crea un rango para cada día entre la fecha de inicio y la fecha de fin
            while fecha_inicio < fecha_fin:
                fecha_inicio += timedelta(days=1)
                rangos.append(Rango(fecha_inicio, fecha_inicio - timedelta(seconds=1), persona, nivel))

            # Se crea un rango para la fecha de fin
            rangos.append(Rango(fecha_fin, fecha_fin, persona, nivel))

    # Filtrar rangos por nivel y por superposición
    rangos_filtrados = []
    for rango in rangos:
        if rango.nivel >= 1:
            superpuesto = False
            for rango_filtrado in rangos_filtrados:
                if (
                    rango.fecha_inicio < rango_filtrado.fecha_fin
                    and rango.fecha_fin > rango_filtrado.fecha_inicio
                ) or (
                    rango.fecha_inicio == rango_filtrado.fecha_inicio
                    or rango.fecha_fin == rango_filtrado.fecha_fin
                ):
                    superpuesto = True
                    break
            if not superpuesto:
                rangos_filtrados.append(rango)

    # Convertir rangos a strings
    salida = [str(rango) for rango in rangos_filtrados]

    return salida


# Ejemplo de uso
entrada = [
    "2024-06-21T00.00.00Z,2025-01-01T00.00.00Z,1,0",
    "2024-06-21T15.00.00Z,2024-06-21T18.30.00Z,2,1",
    "2024-06-22T00.00.00Z,2024-06-22T11.00.00Z,3,1",
    "2024-06-22T16.00.00Z,2025-01-01T00.00.00Z,4,1",
]

salida = dividir_rangos(entrada)

print(salida)