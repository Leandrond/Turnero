PROYECTO: SISTEMA DE GESTIÓN DE TURNOS

Titulo:
Sistema de Gestión de Turnos

Propuesta:
El proyecto tiene como objetivo desarrollar un sistema orientado a objetos
para gestionar la asignación y administración de turnos en instancias de evaluación
académica (defensas de examen, entregas de trabajos prácticos y mesas de examen).

Cada turno registra información relacionada con:
- El alumno o grupo que se presenta.
- El docente que gestiona la evaluación.
- La materia y la instancia evaluativa.
- La fecha, hora y estado del turno.
- La prioridad y motivo de justificación (si corresponde).

El sistema permitirá realizar diferentes operaciones relacionadas con la creación,
registro, ordenamiento y control de turnos dentro de un turnero administrado por el docente.

El proyecto corresponde a la materia Programación II - Año 2026 y tiene como finalidad
aplicar los conceptos de Programación Orientada a Objetos trabajados hasta el momento,
incorporando nuevos principios de diseño y buenas prácticas.

VERSION :
Versión 1.0 - 2026

REQUERIMIENTOS FUNCIONALES

RF01 - Crear Turnero
El sistema deberá permitir al docente crear un turnero indicando fecha, materia e instancia evaluativa.

RF02 - Registrar Turno
El sistema deberá permitir registrar un turno individual o grupal dentro del turnero.

RF03 - Validar Prioridad
El sistema deberá permitir ingresar una justificación de prioridad y reordenar la cola aplicando
el algoritmo de prioridad + FIFO.

RF04 - Llamar al siguiente turno
El sistema deberá permitir al docente llamar al siguiente turno disponible y cambiar su estado.

RF05 - Consultar estado de turnos
El sistema deberá permitir visualizar los turnos registrados y sus estados (EN_ESPERA, EN_EVALUACION, FINALIZADO, AUSENTE).

RF06 - Persistencia de datos
El sistema deberá guardar la información en archivos para mantener continuidad entre ejecuciones.

RF07 - Gestión de grupos
El sistema deberá permitir registrar grupos de alumnos, validando cupo mínimo y máximo.

REQUERIMIENTOS NO FUNCIONALES

RNF01 - Programación Orientada a Objetos
La solución deberá desarrollarse aplicando los principios de POO.

RNF02 - Encapsulamiento
Los atributos de las clases deberán respetar los criterios de visibilidad y encapsulamiento.

RNF03 - Responsabilidad de las clases
Cada clase deberá representar una responsabilidad claramente definida.

RNF04 - Reutilización
La solución deberá favorecer la reutilización de código mediante métodos y clases apropiadas.

RNF05 - Legibilidad
El código deberá ser claro, ordenado y fácil de comprender. Los nombres deberán ser significativos.

RNF06 - Validación
El sistema deberá controlar situaciones que generen información inconsistente,
especialmente en la gestión de turnos y grupos.

RNF07 - Entorno de desarrollo
El proyecto deberá poder abrirse y ejecutarse utilizando BlueJ.

RNF08 - Documentación
Las clases y métodos principales deberán contar con documentación adecuada.

MODELO PRELIMINAR DE CLASES (UML)

class Alumno {
  - nombre: String
  - apellido: String
  - legajo: String
  - dni: String

  + getNombre(): String
  + getApellido(): String
  + getLegajo(): String
  + getDni(): String
  + getNombreCompleto(): String
}

class Docente {
  - nombre: String
  - apellido: String
  - legajo: String

  + getNombre(): String
  + getApellido(): String
  + getLegajo(): String
  + getNombreCompleto(): String
}

class Grupo {
  - numero: int
  - listaAlumnos: List<Alumno>

  + getNumero(): int
  + getListaAlumnos(): List<Alumno>
  + agregarAlumno(alumno: Alumno): void
}

class Materia {
  - nombre: String
  - representanteCatedra: Docente

  + getNombre(): String
  + getRepresentanteCatedra(): Docente
  + setRepresentanteCatedra(docente: Docente): void
}

class Comision {
  - nombre: String
  - materia: Materia
  - docente: Docente
  - listaAlumnos: List<Alumno>
  - listaGrupos: List<Grupo>

  + getNombre(): String
  + getMateria(): Materia
  + getDocente(): Docente
  + getListaAlumnos(): List<Alumno>
  + getListaGrupos(): List<Grupo>
  + agregarAlumno(alumno: Alumno): void
  + agregarGrupo(grupo: Grupo): void
}

class InstanciaEvaluativa {
  - tipo: String
  - fecha: String
  - horaInicio: String
  - comision: Comision
  - duracionEstimada: String
  - turno: Turno

  + getTipo(): String
  + getFecha(): String
  + getHoraInicio(): String
  + getComision(): Comision
  + getDuracionEstimada(): String
  + getTurno(): Turno
}

class Turno {
  - evaluacion: InstanciaEvaluativa
  - alumno: Alumno
  - grupo: Grupo
  - fechaHoraRegistro: String
  - horaEstimada: String
  - tienePrioridad: boolean
  - motivoPrioridad: String
  - estado: String

  + getEvaluacion(): InstanciaEvaluativa
  + getAlumno(): Alumno
  + getGrupo(): Grupo
  + getFechaHoraRegistro(): String
  + getHoraEstimada(): String
  + isTienePrioridad(): boolean
  + getMotivoPrioridad(): String
  + getEstado(): String
  + setEstado(estado: String): void
}

class Turnero {
  - listaTurnos: List<Turno>

  + getListaTurnos(): List<Turno>
  + agregarTurno(turno: Turno): void
  + obtenerColachaOrdenada(): List<Turno>
  + obtenerSiguienteTurno(): Turno
  + cambiarEstadoTurno(numero: int, nuevoEstado: String): void
}

Estudiantes de Programación II - UTN FRVM (Año 2026)
- TOMÁS, DIEGO – Jr.
- DOMÍNGUEZ, LEO MARIANO – Q&A Tester
- DEMARCHI, LEANDRO – Líder técnico
- BERRINO, JOAQUÍN JOSÉ – Diseñador
- CHOQUE, LEANDRO – Programador